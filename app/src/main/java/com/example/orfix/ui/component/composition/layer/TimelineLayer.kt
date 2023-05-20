package com.example.orfix.ui.component.composition.layer

import android.graphics.Canvas
import android.graphics.Rect
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import androidx.core.content.ContextCompat
import androidx.core.view.GestureDetectorCompat
import com.example.orfix.R
import com.example.orfix.ui.component.composition.BaseCompositionLayer
import com.example.orfix.ui.component.composition.DrawInfoManager
import com.example.orfix.ui.component.composition.layer.data.CompositionState

class TimelineLayer : BaseCompositionLayer(), GestureDetector.OnGestureListener {

    private lateinit var mDetector: GestureDetectorCompat

    override fun initLayer() {
        mDetector = GestureDetectorCompat(drawInfo.context, this)
        mDetector.setIsLongpressEnabled(false)
    }

    override fun onDraw(canvas: Canvas) {
        painter.color = ContextCompat.getColor(drawInfo.context, R.color.gray_background_main)
        canvas.drawRect(drawInfo.timelineRect, painter)

        painter.color = ContextCompat.getColor(drawInfo.context, R.color.timeline_underline)
        painter.strokeWidth = 3f
        canvas.drawLine(
            drawInfo.timelineRect.left.toFloat(),
            (drawInfo.timelineRect.bottom - 5).toFloat(),
            drawInfo.timelineRect.right.toFloat(),
            (drawInfo.timelineRect.bottom - 5).toFloat(),
            painter
        )
    }

    override fun onTouch(event: MotionEvent?): Boolean {

        var isMDetector = false
        if (mDetector.onTouchEvent(event)) {
            isMDetector = true
        }

        if (event != null) {
            if (event.action == MotionEvent.ACTION_UP) {
                eventState.removeState(eventState.IS_TIMELINE_ON_DOWN)
            }
        }

        return when {
            isMDetector -> {
                true
            }
            else -> {
                false
            }
        }

    }

    override fun onDown(p0: MotionEvent?): Boolean {
        if (p0 != null) {
            if (isRectPressed(drawInfo.timelineSerifsRect, p0.x, p0.y)) {

                eventState.addState(eventState.IS_TIMELINE_ON_DOWN)
                return true
            }
        }
        return false
    }

    override fun onShowPress(p0: MotionEvent?) {
    }

    override fun onSingleTapUp(p0: MotionEvent?): Boolean {
        if (eventState.isStateActive(eventState.IS_TIMELINE_ON_DOWN) && p0 != null) {
            DrawInfoManager.cursorShift(p0.x, drawInfo)
            return true
        }

        return false
    }

    override fun onScroll(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {
        if (eventState.isStateActive(eventState.IS_TIMELINE_ON_DOWN) && p0 != null && p1 != null) {
            if (isRectPressed(drawInfo.timelineSerifsRect, p0.x, p0.y)) {

                DrawInfoManager.cursorShift(p1.x, drawInfo)
                return true
            }
        }

        return false
    }

    override fun onLongPress(p0: MotionEvent?) {

    }

    override fun onFling(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {

        return false
    }

}
