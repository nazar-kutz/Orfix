package com.example.orfix.ui.component.composition

import android.graphics.Canvas
import android.util.Log
import android.view.MotionEvent
import com.example.orfix.ui.component.composition.layer.*
import com.example.orfix.ui.component.composition.layer.data.CompositionState
import com.example.orfix.ui.component.composition.layer.data.DrawInfo

class CompositionLayers {
    private lateinit var drawInfo: DrawInfo
    private lateinit var eventState: CompositionState
    private var layers: MutableList<BaseCompositionLayer> = mutableListOf()
    private var onDrawLayers: MutableList<BaseCompositionLayer?> = mutableListOf()
    private var onTouchLayers: MutableList<BaseCompositionLayer?> = mutableListOf()

    fun initLayers(drawInfo: DrawInfo, eventState: CompositionState) {
        this.drawInfo = drawInfo
        this.eventState = eventState

        addLayer(BackgroundLayer())
        addLayer(TrackHeadersLayer())
        addLayer(VerticalLinesLayer())
        addLayer(HorizontalLinesLayer())
        addLayer(SegmentsLayer())
        addLayer(TimelineLayer())
        addLayer(TimelineSerifsLayer())
        addLayer(TimelineCursorLayer())
        addLayer(ButtonsLayer())
        initLayers()

        onDrawLayers.add(getLayer(BackgroundLayer::class.java))
        onDrawLayers.add(getLayer(VerticalLinesLayer::class.java))
        onDrawLayers.add(getLayer(SegmentsLayer::class.java))
        onDrawLayers.add(getLayer(ButtonsLayer::class.java))

        onDrawLayers.add(getLayer(HorizontalLinesLayer::class.java))

        onDrawLayers.add(getLayer(TrackHeadersLayer::class.java))
        onDrawLayers.add(getLayer(TimelineLayer::class.java))
        onDrawLayers.add(getLayer(TimelineSerifsLayer::class.java))
        onDrawLayers.add(getLayer(TimelineCursorLayer::class.java))


        onTouchLayers.add(getLayer(ButtonsLayer::class.java))
        onTouchLayers.add(getLayer(SegmentsLayer::class.java))
        onTouchLayers.add(getLayer(BackgroundLayer::class.java))
        onTouchLayers.add(getLayer(TrackHeadersLayer::class.java))
        onTouchLayers.add(getLayer(TimelineLayer::class.java))

    }

    fun onDraw(canvas: Canvas) {
        for (layer in onDrawLayers) {
            layer?.onDraw(canvas)
        }
    }

    private fun addLayer(layer: BaseCompositionLayer) {
        layers.add(layer)
    }

    private fun initLayers() {
        for (layer in layers) {
            layer.drawInfo = this.drawInfo
            layer.eventState = this.eventState
            layer.initLayer()
        }
    }

    fun <T> getLayer(layerClass: Class<T>): BaseCompositionLayer? {
        for (layer in layers) {
            if (layer::class.java == layerClass) {
                return layer
            }
        }
        return null
    }

    fun onTouch(event: MotionEvent?): Boolean {
        for (layer in onTouchLayers) {
            if (layer?.onTouch(event) == true) {
                return true
            }
        }
        return false
    }





}