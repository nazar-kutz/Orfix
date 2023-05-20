package com.example.orfix.ui.component.composition.layer

import android.graphics.Canvas
import android.view.MotionEvent
import androidx.core.content.ContextCompat
import com.example.orfix.R
import com.example.orfix.ui.component.composition.BaseCompositionLayer
import com.example.orfix.ui.component.composition.layer.data.LineType

class VerticalLinesLayer : BaseCompositionLayer() {
    var isBackgroundPressed: Boolean = false
    var previousX: Int = 0
    var previousY: Int = 0

    override fun initLayer() {

    }

    override fun onDraw(canvas: Canvas) {
        for (line in drawInfo.verticalLines) {
            when (line.type) {
                LineType.BIG -> {
                    painter.color = ContextCompat.getColor(drawInfo.context, R.color.big_line)
                    painter.strokeWidth = 3f
                }
                LineType.MEDIUM -> {
                    painter.color = ContextCompat.getColor(drawInfo.context, R.color.medium_line)
                    painter.strokeWidth = 2f
                }
                LineType.SMALL -> {
                    painter.color = ContextCompat.getColor(drawInfo.context, R.color.small_line)
                    painter.strokeWidth = 1f
                }

            }
            canvas.drawLine(
                line.horizontalPosition,
                drawInfo.UITracksOnDraw.first().trackHeaderRect.top.toFloat(),
                line.horizontalPosition,
                drawInfo.UITracksOnDraw.last().trackHeaderRect.bottom.toFloat(),
                painter
            )
        }
    }

    override fun onTouch(event: MotionEvent?): Boolean {
        return false
    }

    /*override fun onTouch(event: MotionEvent?, eventState: CompositionEventState): Boolean {
        val x = event?.x
        val y = event?.y
        if (x != null && y != null ) {

            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (x >= drawInfo.trackHeaderWidth &&
                        x <= drawInfo.maxViewWidth &&
                        y >= drawInfo.timelineHeight &&
                        y <= drawInfo.maxViewHeight
                    ) {
                        Log.d("VerticalLine", "ACTION_DOWN")
                        isBackgroundPressed = true
                        previousX = x.toInt()
                        previousY = y.toInt()
                        return true
                    }
                }
                MotionEvent.ACTION_MOVE -> {

                    if (isBackgroundPressed && eventState.isStateNotActive(CompositionEventState.EventState.IS_RESCALING)) {
                        eventState.addState(CompositionEventState.EventState.IS_RESHIFTING)
   *//*                     Log.d("VerticalLine", "ACTION_MOVE")
                        Log.d("previousX", previousX.toString())
                        Log.d("x", x.toString())*//*
                        var horizontalReShift = x - previousX
                        //Log.d("1111horizontalReShift", horizontalReShift.toString())
                        previousX = x.toInt()
                        horizontalReShift += (horizontalReShift)
                        //Log.d("2222horizontalReShift", horizontalReShift.toString())
                        DrawInfoManager.horizontalShift(horizontalReShift.toInt(), drawInfo)

                        var verticalReShift = previousY - y
                        previousY = y.toInt()
                        verticalReShift += (verticalReShift)

                        DrawInfoManager.verticalShift(verticalReShift.toInt(), drawInfo)
                        return true
                    }
                }
                MotionEvent.ACTION_UP -> {
                    if(isBackgroundPressed){
                        Log.d("VerticalLine", "ACTION_UP")
                        previousX = 0
                        isBackgroundPressed = false
                        return true
                    }
                    return false
                }
            }

        }
        return false

    }*/
}