package com.example.orfix.ui.utils

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager

object AndroidUtils {

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