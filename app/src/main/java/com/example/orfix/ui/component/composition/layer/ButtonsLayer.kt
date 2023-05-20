package com.example.orfix.ui.component.composition.layer

import android.graphics.Canvas
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.GestureDetectorCompat
import com.example.orfix.R
import com.example.orfix.ui.component.composition.BaseCompositionLayer
import com.example.orfix.ui.component.composition.DrawInfoManager

class ButtonsLayer : BaseCompositionLayer(), GestureDetector.OnGestureListener {

    private lateinit var mDetector: GestureDetectorCompat

    override fun initLayer() {
        mDetector = GestureDetectorCompat(drawInfo.context, this)
        mDetector.setIsLongpressEnabled(false)

        painter.color = ContextCompat.getColor(drawInfo.context, R.color.gray_main)

    }

    override fun onDraw(canvas: Canvas) {

        if (drawInfo.newSegmentButton.isVisible) {
            drawInfo.newSegmentButton.icon?.bounds = drawInfo.newSegmentButton.rect
            drawInfo.newSegmentButton.icon?.draw(canvas)
        }

        painter.color = ContextCompat.getColor(drawInfo.context, R.color.small_line)
        canvas.drawRect(
            drawInfo.compositionViewRect.left.toFloat(),
            drawInfo.newTrackButton.rect.top.toFloat(),
            drawInfo.compositionViewRect.right.toFloat(),
            drawInfo.newTrackButton.rect.bottom.toFloat(),
            painter
        )

        drawInfo.newTrackButton.icon?.bounds = drawInfo.newTrackButton.iconRect
        drawInfo.newTrackButton.icon?.setTint(
            ContextCompat.getColor(
                drawInfo.context,
                R.color.white_main
            )
        )
        drawInfo.newTrackButton.icon?.draw(canvas)



/*        painter.style = Paint.Style.FILL
        painter.color = ContextCompat.getColor(drawInfo.context, R.color.big_line)
        painter.strokeWidth = 1f
        canvas.drawLine(
            drawInfo.newTrackButton.rect.right.toFloat(),
            drawInfo.newTrackButton.rect.top.toFloat(),
            drawInfo.newTrackButton.rect.right.toFloat(),
            drawInfo.newTrackButton.rect.bottom.toFloat(), painter)

        canvas.drawLine(
            drawInfo.newTrackButton.rect.left.toFloat(),
            drawInfo.newTrackButton.rect.bottom.toFloat(),
            drawInfo.newTrackButton.rect.right.toFloat(),
            drawInfo.newTrackButton.rect.bottom.toFloat(), painter)*/
    }

    override fun onTouch(event: MotionEvent?): Boolean {

        var isMDetector = false
        if (mDetector.onTouchEvent(event)) {
            isMDetector = true
        }

        if (event != null) {
            if (event.action == MotionEvent.ACTION_UP) {
                eventState.removeState(eventState.IS_BUTTONS_ON_DOWN)

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
            if (drawInfo.newSegmentButton.isVisible) {
                if (isRectPressed(drawInfo.newSegmentButton.rect, p0.x, p0.y)) {
                    eventState.addState(eventState.IS_BUTTONS_ON_DOWN)
                    return true
                }
            }else if(isRectPressed(drawInfo.newTrackButton.rect, p0.x, p0.y)){
                eventState.addState(eventState.IS_BUTTONS_ON_DOWN)
                return true
            }

            for (button in drawInfo.segmentButtons.buttons){
                if(isRectPressed(button.rect, p0.x, p0.y)){
                    eventState.addState(eventState.IS_BUTTONS_ON_DOWN)
                    return true
                }
            }
        }
        return false
    }

    override fun onShowPress(p0: MotionEvent?) {

    }

    override fun onSingleTapUp(p0: MotionEvent?): Boolean {

        if (eventState.isStateActive(eventState.IS_BUTTONS_ON_DOWN) && p0 != null) {
            if (drawInfo.newSegmentButton.isVisible) {
                if (isRectPressed(drawInfo.newSegmentButton.rect, p0.x, p0.y)) {
                    DrawInfoManager.createSegment(drawInfo)
                    return true
                }
            }

            if (isRectPressed(drawInfo.newTrackButton.rect, p0.x, p0.y)){
                DrawInfoManager.createUITrack(null, drawInfo)
                return true
            }

            if(drawInfo.isSegmentEditing && drawInfo.selectedSegment != null){
                for(button in drawInfo.segmentButtons.buttons){
                    if(isRectPressed(button.rect, p0.x, p0.y)){
                        if (button.name == "delete"){
                            DrawInfoManager.deleteSelectedSegment(drawInfo)
                            return true
                        }else if(button.name == "edit"){
                            Toast.makeText(drawInfo.context,"Edit segment ${drawInfo.selectedSegment!!.id}", Toast.LENGTH_SHORT).show()
                            return true
                        }
                    }
                }
            }
        }

        return false
    }

    override fun onScroll(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {
        if (eventState.isStateActive(eventState.IS_BUTTONS_ON_DOWN) && p0 != null) {

        }

        return false
    }

    override fun onLongPress(p0: MotionEvent?) {
        if (eventState.isStateActive(eventState.IS_BUTTONS_ON_DOWN) && p0 != null) {

        }

    }

    override fun onFling(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {
        if (eventState.isStateActive(eventState.IS_BUTTONS_ON_DOWN) && p0 != null) {

        }

        return false
    }
}