package com.example.orfix.ui.component.composition.layer

import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.view.MotionEvent
import androidx.core.content.ContextCompat
import com.example.orfix.R
import com.example.orfix.ui.component.composition.BaseCompositionLayer

class TimelineCursorLayer : BaseCompositionLayer() {
    var iconPlaySize: Int = 0
    var horizontalCursorPosition = 0f
    var playIcon: Drawable? = null
    var isCursorPressed = false
    var isMoved = false
    var previousX: Int = 0


    override fun initLayer() {
        iconPlaySize =
            drawInfo.context.resources.getDimension(R.dimen.timeline_height).toInt()
        playIcon = ContextCompat.getDrawable(drawInfo.context, R.drawable.ic_cursor_play)
    }

    override fun onDraw(canvas: Canvas) {

        if ((drawInfo.timelineCursorRect.right - drawInfo.timelineCursorRect.width() / 2) >= drawInfo.trackHeadersRect.width() - 1 &&
            drawInfo.cursorPPQN <= drawInfo.verticalLines.last().PPQN) {
            playIcon?.setBounds(
                drawInfo.timelineCursorRect.left,
                drawInfo.timelineCursorRect.top,
                drawInfo.timelineCursorRect.right,
                drawInfo.timelineCursorRect.bottom
            )
            playIcon?.draw(canvas)

            painter.color = ContextCompat.getColor(drawInfo.context, R.color.white_main)
            painter.strokeWidth = 4f
            canvas.drawLine(
                (drawInfo.timelineCursorRect.left + drawInfo.timelineCursorRect.width() / 2).toFloat(),
                drawInfo.timelineCursorRect.bottom.toFloat(),
                (drawInfo.timelineCursorRect.left + drawInfo.timelineCursorRect.width() / 2).toFloat(),
                drawInfo.UITracks.last().trackHeaderRect.bottom.toFloat(),
                painter
            )
        }
    }

    override fun onTouch(event: MotionEvent?): Boolean {
        return false
    }

/*    private class TimelineCursorGestureListener : GestureDetector.SimpleOnGestureListener() {

        override fun onDown(e: MotionEvent?): Boolean {
            //return super.onDown(e)
            if (e != null) {
                if (e.x >= horizontalCursorPosition - iconPlaySize / 2 &&
                    e.x <= horizontalCursorPosition + iconPlaySize / 2 &&
                    y >= 0 && y <= iconPlaySize
                ) {
                    isCursorPressed = true
                    previousX = x.toInt()
                    return true
                }
            }
        }

        override fun onScroll(
            e1: MotionEvent?,
            e2: MotionEvent?,
            distanceX: Float,
            distanceY: Float
        ): Boolean {
            return true
        }
    }*/

    /*override fun onTouch(event: MotionEvent?, eventState: CompositionEventState): Boolean {
        var x = event?.x
        var y = event?.y
        if (x != null && y != null) {
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (x >= horizontalCursorPosition - iconPlaySize / 2 &&
                        x <= horizontalCursorPosition + iconPlaySize / 2 &&
                        y >= 0 && y <= iconPlaySize
                    ) {
                        isCursorPressed = true
                        previousX = x.toInt()
                        return true
                    }
                }
                MotionEvent.ACTION_MOVE -> {
                    if (isCursorPressed) {
                        isMoved = true
                        if(x - previousX < 0){
                            if (event.x <= drawInfo.cursorPx - (drawInfo.horizontalStepPx.toFloat()  * (40f / 100f))) {
                                Log.e("action_Move:", "Left")
                                DrawInfoManager.cursorShift(x, drawInfo)
                            }
                        }else{
                            if (event.x >= drawInfo.cursorPx + (drawInfo.horizontalStepPx.toFloat()  * (40f / 100f))) {
                                Log.e("action_Move:", "Right")
                                DrawInfoManager.cursorShift(x, drawInfo)
                            }
                        }
                        previousX = x.toInt()
                        return true
     *//*                   var previousLine: VerticalLine? = null
                        var nextLine: VerticalLine? = null
                        for (lineIndex in drawInfo.verticalLines.indices) {
                            if (drawInfo.verticalLines[lineIndex].horizontalPosition > horizontalCursorPosition) {
                                nextLine = drawInfo.verticalLines[lineIndex]
                                if (drawInfo.cursorPPQN == drawInfo.verticalLines[lineIndex - 1].PPQN) {
                                    if (lineIndex >= 2) {
                                        previousLine = drawInfo.verticalLines[lineIndex - 2]
                                    }
                                } else {
                                    if (lineIndex >= 1) {
                                        previousLine = drawInfo.verticalLines[lineIndex - 1]
                                    }
                                }

                                break
                            }
                        }*//*
*//*                        if (previousLine != null && nextLine != null) {
                            if (event.x <= previousLine.horizontalPosition + (drawInfo.horizontalStepPx.toFloat()  * (20f / 100f))) {
                                DrawInfoManager.cursorShift(x, 0, drawInfo)

                                drawInfo.cursorPPQN = previousLine.PPQN
                            } else if (event.x >= nextLine.horizontalPosition - (drawInfo.horizontalStepPx.toFloat()  * (20f / 100f))) {
                                DrawInfoManager.cursorShift(x, 1, drawInfo)

                                drawInfo.cursorPPQN = nextLine.PPQN
                            }
                        }*//*
                    }

                }
                MotionEvent.ACTION_UP -> {
                    if (isCursorPressed) {
                        if (!isMoved) {
                            if (drawInfo.isPlayed) {
                                playIcon = ContextCompat.getDrawable(
                                    drawInfo.context,
                                    R.drawable.ic_cursor_play
                                )
                                drawInfo.isPlayed = false

                            } else {
                                playIcon = ContextCompat.getDrawable(
                                    drawInfo.context,
                                    R.drawable.ic_cursor_stop
                                )
                                drawInfo.isPlayed = true
                            }
                        }
                        isCursorPressed = false
                        isMoved = false
                        return true
                    }

                }
            }
        }
        return false
    }*/
}