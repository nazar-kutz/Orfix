package com.example.orfix.ui.component.composition.layer.data

import android.graphics.Rect

class UISegment(_startPPQN: Int, duration: Int, _id: Int, selected: Boolean = false) {
    var startPPQN:Int = _startPPQN
    var endPPQN:Int = 0
    var isSelected = selected
    var id = _id

    lateinit var segmentRect: Rect

    init {
        endPPQN = startPPQN + duration
    }
}