package com.example.orfix.ui.fragment

import android.os.Bundle
import com.example.orfix.AppActivity
import com.example.orfix.R
import com.example.orfix.ui.BaseFragment

class MainFragment: BaseFragment(R.layout.fragment_main) {

	override fun createViewContent(savedInstanceState: Bundle?) {
	}

	override fun onBackPressed() {
		activity.moveTaskToBack(true)
	}

	companion object {

		private val TAG = MainFragment::class.simpleName;

		@JvmStatic
		fun showInstance(activity: AppActivity) {
			activity.showFragment(MainFragment(), TAG)
		}
	}
}