package com.example.orfix.ui.component.composition

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.MotionEvent
import com.example.orfix.ui.component.composition.layer.data.CompositionState
import com.example.orfix.ui.component.composition.layer.data.DrawInfo

abstract class BaseCompositionLayer {
    var drawInfo = DrawInfo()
    var eventState = CompositionState()

    protected val painter = Paint().apply {
        color = 0
        style = Paint.Style.FILL
        isAntiAlias = true
        strokeWidth = 0.5f
    }

    abstract fun initLayer()
    abstract fun onDraw(canvas: Canvas)
    abstract fun onTouch(event: MotionEvent?): Boolean

    fun isRectPressed(rect: Rect, x: Float, y: Float): Boolean {
        return  x > rect.left &&
                x < rect.right &&
                y > rect.top &&
                y < rect.bottom
    }
}