package com.example.orfix

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.example.orfix.ui.BaseFragment
import com.example.orfix.ui.fragment.MainFragment

class AppActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		MainFragment.showInstance(this)
	}

	fun showFragment(fragment: BaseFragment, tag: String?) {
		val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
		ft.replace(R.id.fragment_container, fragment, tag)
		ft.addToBackStack(null)
		ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
		ft.commitAllowingStateLoss()
	}

	fun closeFragment(fragment: BaseFragment) {
		// TODO close target fragment
		if (supportFragmentManager.backStackEntryCount > 0) {
			supportFragmentManager.popBackStack()
		} else {
			moveTaskToBack(true)
		}
	}

}