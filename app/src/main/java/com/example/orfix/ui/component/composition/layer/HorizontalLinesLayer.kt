package com.example.orfix.ui.component.composition.layer

import android.graphics.Canvas
import android.graphics.Paint
import android.view.MotionEvent
import androidx.core.content.ContextCompat
import com.example.orfix.R
import com.example.orfix.ui.component.composition.BaseCompositionLayer

class HorizontalLinesLayer : BaseCompositionLayer() {
    override fun initLayer() {

    }

    override fun onDraw(canvas: Canvas) {
        for (track in drawInfo.UITracksOnDraw) {
            painter.color = ContextCompat.getColor(drawInfo.context, R.color.horizontal_line)
            painter.style = Paint.Style.FILL
            painter.strokeWidth = 1f
            painter.isAntiAlias = false
            canvas.drawLine(
                drawInfo.compositionFieldRect.left.toFloat(),
                track.trackHeaderRect.bottom.toFloat(),
                drawInfo.compositionFieldRect.right.toFloat(),
                track.trackHeaderRect.bottom.toFloat(),
                painter
            )
        }
    }

    override fun onTouch(event: MotionEvent?): Boolean {
        return false

    }
}