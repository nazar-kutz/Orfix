package com.example.orfix.ui.utils

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat.JPEG
import android.graphics.Paint
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.orfix.R
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.lang.reflect.Field
import java.text.SimpleDateFormat
import java.util.*

object AndroidUtils {

	fun convertDateFormat(date: String, oldFormat: String, newFormat: String): String {
		val simpleDateFormat = SimpleDateFormat(oldFormat, Locale.US)
		val d: Date = simpleDateFormat.parse(date) ?: return date
		simpleDateFormat.applyPattern(newFormat)
		return simpleDateFormat.format(d)
	}

	fun getBase64WithPrefix(bitmap: Bitmap): String {
		val base64 = getBase64(bitmap)
		return "data:image/jpeg;base64,${base64}"
	}

	fun getBase64(bitmap: Bitmap): String =
		android.util.Base64.encodeToString(bitmapAsBytesArray(bitmap), android.util.Base64.DEFAULT)

	fun saveBitmapToFile(bitmap: Bitmap, file: File) {
		try {
			val fos = FileOutputStream(file)
			bitmap.compress(JPEG, 100, fos)
			fos.close()
		} catch (e: IOException) {
			// ignored
			e.printStackTrace()
		}
	}

	fun bitmapAsBytesArray(bitmap: Bitmap): ByteArray? {
		return try {
			val stream = ByteArrayOutputStream()
			bitmap.compress(JPEG, 100, stream)
			stream.toByteArray()
		} catch (e: IOException) {
			// ignored
			e.printStackTrace()
			null
		}
	}

	fun updateViewsVisibility(visible: Boolean,
	                          goneInvisible: Boolean,
	                          vararg views: View) {
		for (view in views) {
			updateVisibility(view, visible, goneInvisible)
		}
	}

	fun updateVisibility(view: View?, visible: Boolean, goneInvisible: Boolean = true) {
		if (view != null) {
			view.visibility = when {
				visible -> {
					View.VISIBLE
				}
				goneInvisible -> {
					View.GONE
				}
				else -> {
					View.INVISIBLE
				}
			}
		}
	}

	fun getStringByProperty(ctx: Context, property: String): String? {
		return try {
			val field: Field =  R.string::class.java.getField(property)
			getStringForField(ctx, field)
		} catch (e: Exception) {
			null
		}
	}

	@Throws(IllegalAccessException::class)
	private fun getStringForField(ctx: Context, field: Field?): String? {
		if (field != null) {
			val `in` = field[null] as Int
			return ctx.getString(`in`)
		}
		return null
	}

	fun getTextWidth(paint: Paint, text: String?): Int {
		return paint.measureText(text).toInt()
	}

	fun addStatusBarPadding(ctx: Context, view: View) {
		view.apply {
			setPadding(paddingLeft, paddingTop + getStatusBarHeight(ctx), paddingRight, paddingBottom)
		}
	}

	fun removeStatusBarPadding(ctx: Context, view: View) {
		view.apply {
			setPadding(paddingLeft, paddingTop - getStatusBarHeight(ctx), paddingRight, paddingBottom)
		}
	}

	private fun getStatusBarHeight(ctx: Context): Int {
		var result = 0
		val resourceId = ctx.resources.getIdentifier("status_bar_height", "dimen", "android")
		if (resourceId > 0) {
			result = ctx.resources.getDimensionPixelSize(resourceId)
		}
		return result
	}

	fun hideSoftKeyboard(activity: Activity, input: View?) {
		val inputMethodManager =
			activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
		if (input != null) {
			val windowToken = input.windowToken
			if (windowToken != null) {
				inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
			}
		}
	}

}