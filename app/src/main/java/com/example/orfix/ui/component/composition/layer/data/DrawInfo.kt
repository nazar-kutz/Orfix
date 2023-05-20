package com.example.orfix.ui.component.composition.layer.data

import android.content.Context
import android.graphics.Color
import android.graphics.Rect

class DrawInfo {
    lateinit var context: Context

    var colors = ArrayList<Int>()

    var color = Color()
    lateinit var segmentButtons: UISegmentButtons
    lateinit var newSegmentButton: UIButton
    lateinit var newTrackButton :UIButton


    var newSegmentStartPPQN: Int = 0
    var newSegmentEndPPQN: Int = 0

    lateinit var compositionViewRect: Rect
    lateinit var timelineRect: Rect
    lateinit var timelineSerifsRect: Rect
    lateinit var timelineCursorRect: Rect
    lateinit var compositionFieldRect: Rect
    lateinit var trackHeadersRect: Rect

    var segmentLeftResizeRect: Rect? = null
    var segmentRightResizeRect: Rect? = null

    var selectedSegment: UISegment? = null
    var selectedTrack: UITrack? = null


    var savedSelectedSegment :UISegment? = null

    var isSegmentEditing: Boolean = false
    var isSegmentShifting: Boolean = false
    var isSegmentInWrongPosition: Boolean = false
    var isTrackHeaderExpanded = false

    var trackHeaderHeight = 0

    var maxVerticalShiftPx: Int = 0

    var stepPPQN: Int = 0
    var horizontalStepPx: Float = 0f
    var shiftPPQN: Int = 0
    var horizontalShiftPx: Float = 0f

    var maxStepPPQN: Int = 0
    var minStepPPQN: Int = 0
    var maxHorizontalStepPx: Int = 0
    var minHorizontalStepPX: Int = 0

    var cursorPPQN: Int = 0

    var verticalLines = ArrayList<VerticalLine>()
    var UITracks = ArrayList<UITrack>()
    var UITracksOnDraw = ArrayList<UITrack>()
    var tracks = ArrayList<UITrack>() //temporary holder until backand's tracks
}