package com.example.orfix.ui.component.composition

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.View.MeasureSpec
import androidx.core.view.doOnLayout
import androidx.core.view.doOnPreDraw
import com.example.orfix.ui.component.composition.layer.data.CompositionState
import com.example.orfix.ui.component.composition.layer.data.DrawInfo


class CompositionView(
    context: Context,
    attrs: AttributeSet
) : View(context, attrs), View.OnTouchListener {

    var drawInfo = DrawInfo()
    var eventState: CompositionState = CompositionState()
    var compositionLayers = CompositionLayers()

    init {
        this.doOnLayout {
            //DrawInfoManager.setViewSizes(it.measuredWidth, it.measuredHeight, drawInfo)
            /*DrawInfoManager.initDrawInfo(drawInfo, context)
            compositionLayers.initLayers(drawInfo, eventState)*/

            DrawInfoManager.setViewSizes(it.measuredWidth, it.measuredHeight, drawInfo, context)
            DrawInfoManager.initDrawInfo(drawInfo, context)

            compositionLayers.initLayers(drawInfo, eventState)
            this.setOnTouchListener(this)
        }
        this.doOnPreDraw {

        }

    }

    override fun onDraw(canvas: Canvas) {
        DrawInfoManager.setViewSizes(width, height, drawInfo, context)

        canvas.apply {

            compositionLayers.onDraw(canvas)
        }
    }

    override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {

        if(compositionLayers.onTouch(p1)) {
            invalidate()
            return true
        } else {
            return false
        }
    }

}