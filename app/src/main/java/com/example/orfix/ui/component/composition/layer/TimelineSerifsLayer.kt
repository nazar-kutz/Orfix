package com.example.orfix.ui.component.composition.layer

import android.graphics.Canvas
import android.graphics.Paint
import android.view.MotionEvent
import androidx.core.content.ContextCompat
import com.example.orfix.R
import com.example.orfix.ui.component.composition.BaseCompositionLayer
import com.example.orfix.ui.component.composition.layer.data.LineType

class TimelineSerifsLayer : BaseCompositionLayer() {
    override fun initLayer() {

    }

    override fun onDraw(canvas: Canvas) {
        for (line in drawInfo.verticalLines) {
            if (line.PPQN != drawInfo.cursorPPQN) {
                when (line.type) {
                    LineType.BIG -> {
                        if (line.timeLineNumber != 0) {

                            var timelineNumberShift = 0
                            var timelineNumber = line.timeLineNumber

                            while (timelineNumber != 0) {
                                timelineNumberShift += 8
                                timelineNumber /= 10
                            }
                            if (line.timeLineNumber >= 100) {

                                painter.color =
                                    ContextCompat.getColor(drawInfo.context, R.color.white_main)
                                painter.strokeWidth = 1.5f
                                painter.textSize = 28f
                                painter.style = Paint.Style.FILL

                                canvas.drawText(
                                    line.timeLineNumber.toString(),
                                    line.horizontalPosition - timelineNumberShift,
                                    drawInfo.timelineRect.height() - 34f,
                                    painter
                                )

                                painter.style = Paint.Style.STROKE
                                painter.strokeWidth = 4f

                                canvas.drawLine(
                                    line.horizontalPosition - ((timelineNumberShift / 8) * 28) / 3,
                                    drawInfo.timelineRect.height() - 26f,
                                    line.horizontalPosition + ((timelineNumberShift / 8) * 28) / 3,
                                    drawInfo.timelineRect.height() - 26f,
                                    painter
                                )

                            } else {

                                painter.color =
                                    ContextCompat.getColor(drawInfo.context, R.color.white_main)
                                painter.strokeWidth = 1.5f
                                painter.textSize = 28f
                                painter.style = Paint.Style.FILL

                                canvas.drawText(
                                    line.timeLineNumber.toString(),
                                    line.horizontalPosition - timelineNumberShift,
                                    drawInfo.timelineRect.height() - 30f,
                                    painter
                                )


                                painter.strokeWidth = 4f
                                painter.style = Paint.Style.STROKE
                                canvas.drawCircle(
                                    line.horizontalPosition - 8f + 8f,
                                    drawInfo.timelineRect.height() - 30f - 10f, (((drawInfo.timelineRect.height() - 12) / 2).toFloat()), painter
                                )
                            }

                        } else {
                            painter.color =
                                ContextCompat.getColor(drawInfo.context, R.color.timeline_underline)
                            painter.strokeWidth = 6f
                            canvas.drawLine(
                                line.horizontalPosition,
                                drawInfo.timelineRect.height() - 38f,
                                line.horizontalPosition,
                                drawInfo.timelineRect.height() - 10f,
                                painter
                            )
                        }
                    }
                    LineType.MEDIUM -> {
                        painter.color =
                            ContextCompat.getColor(drawInfo.context, R.color.timeline_underline)
                        painter.strokeWidth = 6f
                        canvas.drawLine(
                            line.horizontalPosition,
                            drawInfo.timelineRect.height() - 30f,
                            line.horizontalPosition,
                            drawInfo.timelineRect.height() - 10f,
                            painter
                        )
                    }
                    LineType.SMALL -> {
                        painter.color =
                            ContextCompat.getColor(drawInfo.context, R.color.timeline_underline)
                        painter.strokeWidth = 3f
                        canvas.drawLine(
                            line.horizontalPosition,
                            drawInfo.timelineRect.height() - 24f,
                            line.horizontalPosition,
                            drawInfo.timelineRect.height() - 10f,
                            painter
                        )
                    }
                }
            }
        }
    }

    override fun onTouch(event: MotionEvent?): Boolean {
        return false

    }
}