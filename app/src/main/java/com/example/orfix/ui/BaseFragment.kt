package com.example.orfix.ui

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.example.orfix.AppActivity
import com.example.orfix.ui.utils.AndroidUtils

abstract class BaseFragment(@LayoutRes val layoutResId: Int) : Fragment() {

	protected lateinit var activity: AppActivity
	protected lateinit var mainView: View

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		activity = requireActivity() as AppActivity
		activity.onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
			override fun handleOnBackPressed() {
				onBackPressed()
			}
		})
	}

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		mainView = inflater.inflate(layoutResId, container, false)
		createViewContent(savedInstanceState)
		return mainView
	}

	protected fun <T : View> findView(viewId: Int): T = mainView.findViewById(viewId)

	protected open fun onBackPressed() = dismiss()

	protected fun dismiss() = activity.closeFragment(this)

	protected fun hideKeyboard() = AndroidUtils.hideSoftKeyboard(activity, activity.currentFocus)

	protected fun getFont(fontId: Int): Typeface = ResourcesCompat.getFont(activity, fontId)!!

	protected fun getColor(id: Int): Int = ContextCompat.getColor(activity, id)

	protected fun inflate(layoutId: Int): View = layoutInflater.inflate(layoutId, null)

	protected abstract fun createViewContent(savedInstanceState: Bundle?)

}