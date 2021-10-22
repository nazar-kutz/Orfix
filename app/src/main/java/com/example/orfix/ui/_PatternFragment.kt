package com.example.orfix.ui

import android.os.Bundle
import com.example.orfix.AppActivity
import com.example.orfix.R

class _PatternFragment: BaseFragment(R.layout.fragment_main) {

	override fun createViewContent(savedInstanceState: Bundle?) {
		TODO("Not yet implemented")
	}

	companion object {

		private val TAG = _PatternFragment::class.simpleName;

		@JvmStatic
		fun showInstance(activity: AppActivity) {
			activity.showFragment(_PatternFragment(), TAG)
		}
	}
}