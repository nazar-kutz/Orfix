package com.example.orfix.ui.component.composition.layer

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import androidx.core.content.ContextCompat
import androidx.core.view.GestureDetectorCompat
import com.example.orfix.R
import com.example.orfix.ui.component.composition.BaseCompositionLayer
import com.example.orfix.ui.component.composition.DrawInfoManager

class SegmentsLayer : BaseCompositionLayer(), GestureDetector.OnGestureListener {

    private lateinit var mDetector: GestureDetectorCompat

    var isLongPress = false

    var IS_EDITED_SEGMENT_PRESSED = false
    var IS_SEGMENT_RESIZE_LEFT_PRESSED = false
    var IS_SEGMENT_RESIZE_RIGHT_PRESSED = false


    var segmentShiftPoint: Float = 0f
    override fun initLayer() {
        mDetector = GestureDetectorCompat(drawInfo.context, this)
        mDetector.setIsLongpressEnabled(true)
    }

    override fun onDraw(canvas: Canvas) {
        for (track in drawInfo.UITracksOnDraw) {
            for (segment in track.segments) {
                if (!segment.isSelected) {
                    painter.strokeWidth = 0.5f
                    painter.style = Paint.Style.FILL
                    painter.color = ContextCompat.getColor(drawInfo.context, R.color.gray_main)
                    canvas.drawRect(segment.segmentRect, painter)

                    painter.color = track.color
                    painter.alpha = 120
                    canvas.drawRect(segment.segmentRect, painter)
                    painter.alpha = 0

                    painter.style = Paint.Style.STROKE
                    painter.color = track.color
                    painter.strokeWidth = 6f
                    canvas.drawRect(segment.segmentRect, painter)
                }
            }
        }

        if (drawInfo.selectedSegment != null && drawInfo.selectedTrack != null) {
            if (drawInfo.isSegmentShifting) {

                if (drawInfo.isSegmentInWrongPosition) {

                    painter.style = Paint.Style.FILL
                    painter.color = ContextCompat.getColor(drawInfo.context, R.color.white_main)
                    painter.alpha = 60
                    canvas.drawRect(drawInfo.savedSelectedSegment!!.segmentRect, painter)
                    painter.alpha = 0

                    painter.strokeWidth = 6f
                    painter.style = Paint.Style.STROKE
                    painter.color = drawInfo.selectedTrack!!.color
                    canvas.drawRect(drawInfo.savedSelectedSegment!!.segmentRect, painter)


                    painter.style = Paint.Style.FILL
                    painter.color = ContextCompat.getColor(drawInfo.context, R.color.white_main)
                    painter.alpha = 60
                    canvas.drawRect(drawInfo.selectedSegment!!.segmentRect, painter)
                    painter.alpha = 0

                    painter.strokeWidth = 12f
                    painter.style = Paint.Style.STROKE
                    painter.color = Color.RED
                    canvas.drawRect(drawInfo.selectedSegment!!.segmentRect, painter)


                    painter.style = Paint.Style.STROKE
                    canvas.drawLine(
                        drawInfo.selectedSegment!!.segmentRect.left.toFloat(),
                        drawInfo.selectedSegment!!.segmentRect.top.toFloat(),
                        drawInfo.selectedSegment!!.segmentRect.right.toFloat(),
                        drawInfo.selectedSegment!!.segmentRect.bottom.toFloat(),
                        painter
                    )

                    canvas.drawLine(
                        drawInfo.selectedSegment!!.segmentRect.right.toFloat(),
                        drawInfo.selectedSegment!!.segmentRect.top.toFloat(),
                        drawInfo.selectedSegment!!.segmentRect.left.toFloat(),
                        drawInfo.selectedSegment!!.segmentRect.bottom.toFloat(),
                        painter
                    )

                } else {

                    painter.style = Paint.Style.FILL
                    painter.color = ContextCompat.getColor(drawInfo.context, R.color.white_main)
                    painter.alpha = 60
                    canvas.drawRect(drawInfo.selectedSegment!!.segmentRect, painter)
                    painter.alpha = 0

                    painter.strokeWidth = 12f
                    painter.style = Paint.Style.STROKE
                    painter.color = ContextCompat.getColor(drawInfo.context, R.color.white_main)
                    canvas.drawRect(drawInfo.selectedSegment!!.segmentRect, painter)
                }

            } else {
                if (drawInfo.isSegmentEditing) {

                    painter.strokeWidth = 6f
                    painter.style = Paint.Style.FILL_AND_STROKE
                    painter.color = drawInfo.selectedTrack!!.color
                    painter.alpha = 230
                    canvas.drawRect(drawInfo.selectedSegment!!.segmentRect, painter)
                    painter.alpha = 0

                    painter.style = Paint.Style.STROKE
                    painter.color = ContextCompat.getColor(drawInfo.context, R.color.white_main)
                    painter.strokeWidth = 12f
                    canvas.drawRect(drawInfo.selectedSegment!!.segmentRect, painter)


                    for (segmentButton in drawInfo.segmentButtons.buttons) {

                        painter.style = Paint.Style.FILL
                        painter.color = ContextCompat.getColor(drawInfo.context, R.color.white_main)
                        canvas.drawCircle(
                            segmentButton.rect.centerX().toFloat(),
                            segmentButton.rect.centerY().toFloat(),
                            (segmentButton.rect.width() / 2).toFloat() + 8, painter
                        )

                        segmentButton.icon?.bounds = segmentButton.rect
                        segmentButton.icon?.setTint(
                            ContextCompat.getColor(
                                drawInfo.context,
                                R.color.gray_main
                            )
                        )
                        segmentButton.icon?.draw(canvas)

                    }

                } else {

                    painter.strokeWidth = 6f
                    painter.style = Paint.Style.FILL_AND_STROKE
                    painter.color = drawInfo.selectedTrack!!.color
                    canvas.drawRect(drawInfo.selectedSegment!!.segmentRect, painter)
                }
            }
        }


//        painter.color = ContextCompat.getColor(drawInfo.context, R.color.white_main)
        if (drawInfo.isSegmentEditing && drawInfo.segmentLeftResizeRect != null && drawInfo.segmentRightResizeRect != null && drawInfo.selectedSegment != null && drawInfo.selectedTrack != null) {

            painter.style = Paint.Style.FILL
            painter.color = ContextCompat.getColor(drawInfo.context, R.color.white_main)
            canvas.drawCircle(
                drawInfo.segmentLeftResizeRect!!.centerX().toFloat(),
                drawInfo.segmentLeftResizeRect!!.centerY().toFloat(),
                ((drawInfo.segmentLeftResizeRect!!.width() / 2) - 2).toFloat(), painter
            )

            canvas.drawCircle(
                drawInfo.segmentRightResizeRect!!.centerX().toFloat(),
                drawInfo.segmentRightResizeRect!!.centerY().toFloat(),
                ((drawInfo.segmentRightResizeRect!!.width() / 2) - 2).toFloat(), painter
            )
        }

    }

    override fun onTouch(event: MotionEvent?): Boolean {

        var isMDetector = false
        if (mDetector.onTouchEvent(event)) {
            isMDetector = true
        }

        if (event != null) {
            if (event.action == MotionEvent.ACTION_UP) {
                eventState.removeState(eventState.IS_SEGMENT_ON_DOWN)
                eventState.removeState(eventState.IS_BACKGROUND_SEGMENT_DOWN)

                isLongPress = false
                IS_EDITED_SEGMENT_PRESSED = false
                IS_SEGMENT_RESIZE_LEFT_PRESSED = false
                IS_SEGMENT_RESIZE_RIGHT_PRESSED = false

                if (drawInfo.isSegmentShifting) {
                    drawInfo.isSegmentShifting = false

                    if (drawInfo.isSegmentInWrongPosition) {
                        DrawInfoManager.restoreSelectedSegment(drawInfo)
                    }

                    return true
                }
            }
        }

        return when {
            eventState.isStateActive(eventState.IS_BACKGROUND_SEGMENT_DOWN) ->{
                false
            }
            isMDetector -> {
                true
            }
            isLongPress -> {
                true
            }
            else -> {
                false
            }
        }
    }


    override fun onDown(p0: MotionEvent?): Boolean {
        if (p0 != null) {

            if (drawInfo.segmentLeftResizeRect != null && drawInfo.segmentRightResizeRect != null) {
                if (isRectPressed(drawInfo.segmentLeftResizeRect!!, p0.x, p0.y) && isRectPressed(drawInfo.compositionFieldRect, p0.x, p0.y)) {
                    eventState.addState(eventState.IS_SEGMENT_ON_DOWN)

                    IS_SEGMENT_RESIZE_LEFT_PRESSED = true
                    return true
                } else if (isRectPressed(drawInfo.segmentRightResizeRect!!, p0.x, p0.y) && isRectPressed(drawInfo.compositionFieldRect, p0.x, p0.y)) {
                    eventState.addState(eventState.IS_SEGMENT_ON_DOWN)
                    IS_SEGMENT_RESIZE_RIGHT_PRESSED = true

                    return true
                }
            }

            for (track in drawInfo.UITracksOnDraw) {
                for (segment in track.segments) {
                    if (isRectPressed(segment.segmentRect, p0.x, p0.y) && isRectPressed(drawInfo.compositionFieldRect, p0.x, p0.y)) {
                        if (segment == drawInfo.selectedSegment && drawInfo.isSegmentEditing) {
                            IS_EDITED_SEGMENT_PRESSED = true
                            eventState.addState(eventState.IS_SEGMENT_ON_DOWN)
                            segmentShiftPoint = p0.x
                            DrawInfoManager.saveSelectedSegment(drawInfo)

                        } else {

                            eventState.addState(eventState.IS_SEGMENT_ON_DOWN)
                            eventState.addState(eventState.IS_BACKGROUND_SEGMENT_DOWN)
                        }

                        return true
                    }
                }
            }
        }


        return false
    }

    override fun onShowPress(p0: MotionEvent?) {

    }

    override fun onSingleTapUp(p0: MotionEvent?): Boolean {
        if (eventState.isStateActive(eventState.IS_SEGMENT_ON_DOWN) && p0 != null) {

            for (track in drawInfo.UITracksOnDraw) {
                for (segment in track.segments) {
                    if (isRectPressed(segment.segmentRect, p0.x, p0.y)) {

                        drawInfo.newSegmentButton.isVisible = false
                        drawInfo.isSegmentEditing = false

                        DrawInfoManager.selectSegment(segment, track, drawInfo)
                        return true
                    }
                }
            }

        }

        return false
    }

    override fun onScroll(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {
        if (eventState.isStateActive(eventState.IS_SEGMENT_ON_DOWN) && p0 != null && p1 != null) {
            if (drawInfo.segmentLeftResizeRect != null && drawInfo.segmentRightResizeRect != null && drawInfo.selectedSegment != null) {
                if (IS_SEGMENT_RESIZE_LEFT_PRESSED) {
                    DrawInfoManager.reSizeSegment(true, p1.x, drawInfo)
                    return true

                } else if (IS_SEGMENT_RESIZE_RIGHT_PRESSED) {
                    DrawInfoManager.reSizeSegment(false, p1.x, drawInfo)
                    return true

                } else if (IS_EDITED_SEGMENT_PRESSED && drawInfo.isSegmentEditing) {

                    var scrollDifference = p1.x - segmentShiftPoint
                    if (scrollDifference > 0) {
                        while (scrollDifference > drawInfo.horizontalStepPx) {
                            segmentShiftPoint += drawInfo.horizontalStepPx
                            DrawInfoManager.segmentShift(false, drawInfo)
                            scrollDifference -= drawInfo.horizontalStepPx
                        }
                    } else {
                        while (scrollDifference.unaryMinus() > drawInfo.horizontalStepPx) {
                            segmentShiftPoint -= drawInfo.horizontalStepPx
                            DrawInfoManager.segmentShift(true, drawInfo)
                            scrollDifference += drawInfo.horizontalStepPx
                        }
                    }
                    return true
                }
            }
        }
        return false
    }

    override fun onLongPress(p0: MotionEvent?) {
        if (eventState.isStateActive(eventState.IS_SEGMENT_ON_DOWN) && p0 != null) {

            for (track in drawInfo.UITracksOnDraw) {
                for (segment in track.segments) {
                    if (isRectPressed(segment.segmentRect, p0.x, p0.y)) {
                        DrawInfoManager.editSegment(segment, track, drawInfo)
                        isLongPress = true
                        drawInfo.newSegmentButton.isVisible = false
                    }
                }
            }

        }

    }

    override fun onFling(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {
        if (eventState.isStateActive(eventState.IS_SEGMENT_ON_DOWN) && p0 != null) {


        }

        return false
    }
}