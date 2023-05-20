package com.example.orfix.ui.component.composition.layer.data

import android.graphics.Rect
import android.view.WindowInsetsAnimation
import androidx.core.view.WindowInsetsAnimationCompat

class UITrack(
    _isSelected: Boolean = false,
    _trackName: String? = null,
    _extendsCount: Int = 0,
    _childrenCount: Int = 0,
    _isExpanded: Boolean = false,
    _isMuted: Boolean = false,
    _color: Int = 0
) {
    var trackHeaderRect: Rect = Rect()

    lateinit var trackField: Rect
    lateinit var trackNameRect: Rect
    lateinit var expandTrackRect:Rect
    lateinit var expandTrackHeaderRect:Rect
    lateinit var settingsRect:Rect
    lateinit var muteRect:Rect
    lateinit var childCountRect:Rect
    lateinit var extendsCountRect:Rect
    lateinit var extendsIconRect: Rect


    var segments = ArrayList<UISegment>()
    var childUITracks = ArrayList<UITrack>()
    var parentTrack: UITrack? = null

    var trackName: String? = _trackName
    var color: Int = _color
    var extendsCount: Int = _extendsCount
    var childCount: Int = _childrenCount

    var isSelected = _isSelected
    var isExpanded: Boolean = _isExpanded
    var isMuted: Boolean = _isMuted

    fun addSegment(_segment: UISegment) {
        segments.add(_segment)
    }
}