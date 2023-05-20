package com.example.orfix.ui.component.composition.layer

import android.graphics.Canvas
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import androidx.core.content.ContextCompat
import androidx.core.view.GestureDetectorCompat
import com.example.orfix.R
import com.example.orfix.ui.component.composition.BaseCompositionLayer
import com.example.orfix.ui.component.composition.DrawInfoManager

class BackgroundLayer : BaseCompositionLayer(), GestureDetector.OnGestureListener,
    ScaleGestureDetector.OnScaleGestureListener {

    private lateinit var mDetector: GestureDetectorCompat
    private lateinit var mScaleDetector: ScaleGestureDetector
    private var scaleFactor = 1f
    private var SCALE_MULTIPLIER = 30
    private var event: MotionEvent? = null
    private var IS_SCALING = false

    var focusX: Float = 0f
    var focusY: Float = 0f

    override fun initLayer() {
        mDetector = GestureDetectorCompat(drawInfo.context, this)
        mScaleDetector = ScaleGestureDetector(drawInfo.context, this)
        mScaleDetector.isQuickScaleEnabled = false
        mDetector.setIsLongpressEnabled(false)

    }

    override fun onDraw(canvas: Canvas) {
        painter.color = ContextCompat.getColor(drawInfo.context, R.color.gray_main)
        painter.strokeWidth = 0.5f
        canvas.drawColor(painter.color)

    }

    override fun onTouch(event: MotionEvent?): Boolean {
        this.event = event
        mScaleDetector.onTouchEvent(event)

        var isMDetector = false
        if (mDetector.onTouchEvent(event)) {
            isMDetector = true
        }

        if (event != null) {
            if (event.action == MotionEvent.ACTION_UP) {
                eventState.removeState(eventState.IS_BACKGROUND_ON_DOWN)
            }
        }

        return when {
            eventState.isStateActive(eventState.IS_BACKGROUND_SEGMENT_DOWN) ->{
                true
            }
            isMDetector -> {
                true
            }
            else -> {
                false
            }
        }
    }

    override fun onDown(p0: MotionEvent?): Boolean {

        if (eventState.isStateActive(eventState.IS_BACKGROUND_SEGMENT_DOWN)) {
            return true

        } else {
            if (p0 != null) {
                if (isRectPressed(drawInfo.compositionFieldRect, p0.x, p0.y)) {

                    eventState.addState(eventState.IS_BACKGROUND_ON_DOWN)
                    return true
                }
            }
        }

        return false
    }

    override fun onShowPress(p0: MotionEvent?) {
    }

    override fun onSingleTapUp(p0: MotionEvent?): Boolean {

        if (eventState.isStateActive(eventState.IS_BACKGROUND_ON_DOWN) && p0 != null) {
            drawInfo.isSegmentEditing = false

            for (track in drawInfo.UITracksOnDraw) {
                if (isRectPressed(track.trackField, p0.x, p0.y)) {
                    if (track == drawInfo.selectedTrack) {

                        DrawInfoManager.selectSegment(null, null, drawInfo)
                        drawInfo.selectedTrack = track
                        DrawInfoManager.setNewSegmentPPQN(p0.x, drawInfo)
                        return true
                    } else {

                        DrawInfoManager.selectSegment(null, null, drawInfo)
                        drawInfo.selectedTrack = track
                        drawInfo.newSegmentButton.isVisible = false
                        drawInfo.isSegmentEditing = false
                        return true
                    }

                }
            }

            DrawInfoManager.selectSegment(null, null, drawInfo)
        }


        return false
    }

    override fun onScroll(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {
        if ((eventState.isStateActive(eventState.IS_BACKGROUND_ON_DOWN) || eventState.isStateActive(
                eventState.IS_BACKGROUND_SEGMENT_DOWN
            )) && p0 != null
        ) {


            DrawInfoManager.horizontalShift(p2, drawInfo)
            DrawInfoManager.verticalShift(p3, drawInfo)
            return true
        }

        return false
    }

    override fun onLongPress(p0: MotionEvent?) {

    }

    override fun onFling(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {

        return false
    }

    override fun onScale(p0: ScaleGestureDetector?): Boolean {
        if (p0 != null) {
            scaleFactor = p0.scaleFactor
            scaleFactor -= 1
            scaleFactor *= SCALE_MULTIPLIER
            DrawInfoManager.scale(scaleFactor, p0.focusX, drawInfo)
            focusX = p0.focusX
            focusY = p0.focusY
            return true

        }
        return false
    }

    override fun onScaleBegin(p0: ScaleGestureDetector?): Boolean {
        if ((eventState.isStateActive(eventState.IS_BACKGROUND_ON_DOWN) ||
                    eventState.isStateActive(eventState.IS_BACKGROUND_SEGMENT_DOWN)) && p0 != null
        ) {
            IS_SCALING = true
            return true
        }
        return false
    }

    override fun onScaleEnd(p0: ScaleGestureDetector?) {

    }


}