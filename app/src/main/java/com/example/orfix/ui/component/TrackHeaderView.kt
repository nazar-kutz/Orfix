package com.example.orfix.ui.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.example.orfix.R
import com.example.orfix.ui.utils.AndroidUtils

class TrackHeaderView(
	context: Context,
	attrs: AttributeSet
) : FrameLayout(context, attrs) {

	private val parentIndicator: View
	private val tvExtendsCount: TextView
	private val tvTrackName: TextView
	private val collapseBtn: ImageView
	private val settingsBtn: ImageView
	private val volumeBtn: ImageView

	var trackName: String?
	var extendsCount: Int
	var childrenCount: Int
	var isCollapsed: Boolean
	var isMuted: Boolean

	init {
		LayoutInflater.from(context).inflate(R.layout.component_track_header, this, true)
		context.theme.obtainStyledAttributes(
			attrs,
			R.styleable.TrackHeaderView,
			0, 0
		).apply {
			try {
				trackName = getString(R.styleable.TrackHeaderView_trackName)
				extendsCount = getInt(R.styleable.TrackHeaderView_extendsCount, 0)
				childrenCount = getInt(R.styleable.TrackHeaderView_childrenCount, 0)
				isMuted = getBoolean(R.styleable.TrackHeaderView_mutedByDefault, false)
				isCollapsed = getBoolean(R.styleable.TrackHeaderView_collapsedByDefault, false)

				parentIndicator = findViewById(R.id.parent_indicator)
				tvExtendsCount = findViewById<TextView>(R.id.extends_count)
				tvTrackName = findViewById<TextView>(R.id.track_name)
				collapseBtn = findViewById<ImageView>(R.id.collapse_children)
				settingsBtn = findViewById<ImageView>(R.id.settings)
				volumeBtn = findViewById<ImageView>(R.id.volume)

				updateView()
			} finally {
				recycle()
			}
		}
	}

	private fun updateView() {
		AndroidUtils.updateVisibility(parentIndicator, extendsCount > 0)
		AndroidUtils.updateVisibility(collapseBtn, childrenCount > 0)
		tvExtendsCount.text = extendsCount.toString()
		tvTrackName.text = trackName
		val collapseIconId = if (isCollapsed) R.drawable.ic_arrow_down else R.drawable.ic_arrow_up
		val volumeIconId = if (isMuted) R.drawable.ic_mute else R.drawable.ic_volume
		collapseBtn.setImageResource(collapseIconId)
		volumeBtn.setImageResource(volumeIconId)
	}

}