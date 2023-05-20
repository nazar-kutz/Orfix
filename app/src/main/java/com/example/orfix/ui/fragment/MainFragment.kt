package com.example.orfix.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.widget.Button
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.orfix.AppActivity
import com.example.orfix.R
import com.example.orfix.controller.CompositionController
import com.example.orfix.ui.BaseFragment
import com.example.orfix.ui.component.ControlBarView
//import com.example.orfix.ui.component.TrackSequencerFieldView
import com.example.orfix.ui.component.composition.CompositionView

class MainFragment: BaseFragment(R.layout.fragment_main), View.OnClickListener {

	lateinit var compositionView: CompositionView

	lateinit var compositionController: CompositionController

	override fun createViewContent(savedInstanceState: Bundle?) {
		compositionView = findView(R.id.composition_view)
		compositionController = CompositionController(compositionView)


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

	override fun onClick(v: View?) {
	}


}