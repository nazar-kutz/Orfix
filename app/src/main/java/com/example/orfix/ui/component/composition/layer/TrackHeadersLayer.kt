package com.example.orfix.ui.component.composition.layer

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import androidx.core.content.ContextCompat
import com.example.orfix.R
import com.example.orfix.ui.component.composition.BaseCompositionLayer


import android.graphics.drawable.Drawable
import android.os.Build
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.core.view.GestureDetectorCompat
import com.example.orfix.ui.component.composition.DrawInfoManager
import com.example.orfix.ui.component.composition.layer.data.UITrack


class TrackHeadersLayer : BaseCompositionLayer(), GestureDetector.OnGestureListener {
    private lateinit var mDetector: GestureDetectorCompat

    private var doubleArrow: Drawable? = null

    var leftSide: Int = 0
    var centerHorizontalSide: Int = 0
    var centerVerticalSide: Int = 0
    var rightSide: Int = 0
    var topSide: Int = 0
    var bottomSide: Int = 0
    var iconSize: Int = 0
    var iconParentSize: Int = 0

    override fun initLayer() {
        mDetector = GestureDetectorCompat(drawInfo.context, this)
        mDetector.setIsLongpressEnabled(false)

        doubleArrow = ContextCompat.getDrawable(drawInfo.context, R.drawable.ic_round_double_arrow)

        iconSize = drawInfo.context.resources.getDimension(R.dimen.track_header_icon_size).toInt()
        iconParentSize =
            drawInfo.context.resources.getDimension(R.dimen.track_header_parent_indicator_icon_size)
                .toInt()
    }

    override fun onDraw(canvas: Canvas) {
        // #todo delete all sides
        leftSide = 10
        rightSide = drawInfo.trackHeadersRect.width() - 10
        topSide = 10
        bottomSide = drawInfo.trackHeaderHeight - 10
        centerHorizontalSide = drawInfo.trackHeaderHeight / 2
        centerVerticalSide = drawInfo.trackHeadersRect.width() / 2

        painter.style = Paint.Style.STROKE
        painter.color = ContextCompat.getColor(drawInfo.context, R.color.big_line)
        painter.strokeWidth = 2f
        canvas.drawLine(
            drawInfo.trackHeadersRect.right.toFloat(),
            drawInfo.trackHeadersRect.top.toFloat(),
            drawInfo.trackHeadersRect.right.toFloat(),
            drawInfo.UITracksOnDraw.last().trackHeaderRect.bottom.toFloat(),
            painter
        )

        painter.style = Paint.Style.FILL
        painter.strokeWidth = 0.5f

        for(track in drawInfo.UITracksOnDraw){
            drawBackground(canvas, track)
        }

        for (track in drawInfo.UITracksOnDraw) {
            if (drawInfo.isTrackHeaderExpanded) {
                drawTopLine(canvas, track)
                drawMediumLine(canvas, track)
                drawBottomLine(canvas, track)
                drawBorder(canvas, track)

            } else {
                drawCollapsedTrack(canvas, track)
                drawBorder(canvas, track)

            }
        }

        if (drawInfo.selectedTrack != null) {
            drawSelectedBorder(canvas, drawInfo.selectedTrack!!)
        }

    }

    private fun drawBorder(canvas: Canvas, track: UITrack) {
        painter.color = ContextCompat.getColor(drawInfo.context, R.color.horizontal_line)
        painter.style = Paint.Style.FILL
        painter.strokeWidth = 1f
        painter.isAntiAlias = false

        canvas.drawLine(
            track.trackHeaderRect.left.toFloat(),
            track.trackHeaderRect.bottom.toFloat(),
            track.trackHeaderRect.right.toFloat(),
            track.trackHeaderRect.bottom.toFloat(),
            painter
        )
    }

    private fun drawSelectedBorder(canvas: Canvas, track: UITrack) {
        painter.color = track.color
        painter.style = Paint.Style.STROKE
        painter.strokeWidth = 4f

        canvas.drawRect(track.trackHeaderRect, painter)
    }


    override fun onTouch(event: MotionEvent?): Boolean {

        var isMDetector = false
        if (mDetector.onTouchEvent(event)) {
            isMDetector = true
        }

        if (event != null) {
            if (event.action == MotionEvent.ACTION_UP) {
                eventState.removeState(eventState.IS_TRACKHEADER_ON_DOWN)

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

    private fun drawBackground(canvas: Canvas, track: UITrack) {
        painter.color = ContextCompat.getColor(drawInfo.context, R.color.gray_main)
        painter.strokeWidth = 0.5f
        //canvas.drawColor(painter.color)

        canvas.drawRect(
            Rect( // #todo add trackHeaderRect
                0,
                track.trackHeaderRect.top,
                drawInfo.trackHeadersRect.width(),
                track.trackHeaderRect.top + drawInfo.trackHeaderHeight
            ), painter
        )
    }

    private fun drawBottomLine(canvas: Canvas, track: UITrack) {
        val settingIcon: Drawable? =
            ContextCompat.getDrawable(drawInfo.context, R.drawable.ic_settings)

        val muteIcon: Drawable? = if (track.isMuted) {
            ContextCompat.getDrawable(drawInfo.context, R.drawable.ic_mute)
        } else {
            ContextCompat.getDrawable(drawInfo.context, R.drawable.ic_volume)
        }

        val collapseIcon: Drawable? = if (track.isExpanded) {
            ContextCompat.getDrawable(drawInfo.context, R.drawable.ic_arrow_down)
        } else {
            ContextCompat.getDrawable(drawInfo.context, R.drawable.ic_arrow_up)
        }

        if (track == drawInfo.selectedTrack) {
            settingIcon?.setTint(track.color)
            muteIcon?.setTint(track.color)
            collapseIcon?.setTint(track.color)
        } else {

            settingIcon?.setTint(ContextCompat.getColor(drawInfo.context, R.color.white_main))
            muteIcon?.setTint(ContextCompat.getColor(drawInfo.context, R.color.white_main))
            collapseIcon?.setTint(ContextCompat.getColor(drawInfo.context, R.color.white_main))
        }



        settingIcon?.bounds = track.settingsRect
        settingIcon?.draw(canvas)

        muteIcon?.bounds = track.muteRect
        muteIcon?.draw(canvas)

        if (track.childUITracks.isNotEmpty()) {
            collapseIcon?.setTint(track.color)
            collapseIcon?.bounds = track.expandTrackRect
            collapseIcon?.draw(canvas)

        }
    }

    private fun drawMediumLine(canvas: Canvas, track: UITrack) {
        if (track == drawInfo.selectedTrack) {
            painter.color = track.color


        } else {
            painter.color = ContextCompat.getColor(drawInfo.context, R.color.white_main)

        }

        painter.style = Paint.Style.FILL
        painter.strokeWidth = 2f
        painter.textSize =
            drawInfo.context.resources.getDimension(R.dimen.track_header_name_font_size_expanded)
        //painter.typeface = ResourcesCompat.getFont(drawInfo.context, R.font.font_sans_serif_medium)
        /*canvas.drawText(
            track.trackName.toString(),
            leftSide + 20f,
            (centerHorizontalSide + track.trackHeaderRect.top).toFloat() + 9,
            painter
        )*/
        canvas.drawText(
            track.trackName.toString(),
            track.trackNameRect.left.toFloat(),
            (track.trackNameRect.centerY() + drawInfo.context.resources.getDimension(R.dimen.track_header_name_font_size_expanded) / 4),
            painter
        )
    }

    private fun drawTopLine(canvas: Canvas, track: UITrack) {
        if (track.extendsCount > 0) {
            var extendsIcon: Drawable? =
                ContextCompat.getDrawable(drawInfo.context, R.drawable.ic_arrow_parent)



            extendsIcon?.bounds = track.extendsIconRect
            if (track.parentTrack != null) {
                extendsIcon?.setTint(track.parentTrack!!.color)
                painter.color = track.parentTrack!!.color

            }
            extendsIcon?.draw(canvas)

            painter.strokeWidth = 2f
            painter.textSize =
                drawInfo.context.resources.getDimension(R.dimen.track_header_extends_count_font_size)
            canvas.drawText(
                track.extendsCount.toString(),
                track.extendsCountRect.left.toFloat(),
                track.extendsCountRect.centerY() + drawInfo.context.resources.getDimension(R.dimen.track_header_extends_count_font_size) / 3,
                painter
            )
        }

        doubleArrow?.bounds = track.expandTrackHeaderRect

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            doubleArrow?.layoutDirection = View.LAYOUT_DIRECTION_RTL
        }

        if (track == drawInfo.selectedTrack) {
            doubleArrow?.setTint(track.color)

        } else {
            doubleArrow?.setTint(ContextCompat.getColor(drawInfo.context, R.color.white_main))

        }
        doubleArrow?.draw(canvas)
    }

    private fun drawCollapsedTrack(canvas: Canvas, track: UITrack) {

        if (track == drawInfo.selectedTrack) {

            painter.color = track.color
            doubleArrow?.setTint(track.color)
        } else {

            painter.color = ContextCompat.getColor(drawInfo.context, R.color.white_main)
            doubleArrow?.setTint(ContextCompat.getColor(drawInfo.context, R.color.white_main))
        }

        painter.style = Paint.Style.FILL
        painter.strokeWidth = 2f
        painter.textSize =
            drawInfo.context.resources.getDimension(R.dimen.track_header_name_font_size_collapsed)

        var trackHeaderText = track.trackName
        var breakTextNumber =
            painter.breakText(trackHeaderText, true, track.trackNameRect.right.toFloat(), null)

        if (breakTextNumber != track.trackName?.length) {

            trackHeaderText = trackHeaderText?.substring(0, breakTextNumber - 1)
            trackHeaderText += ".."
        }



        if (trackHeaderText != null) {
            canvas.drawText(
                trackHeaderText,
                track.trackNameRect.left.toFloat(),
                track.trackNameRect.bottom.toFloat(),
                painter
            )
        }

        doubleArrow?.bounds = track.expandTrackHeaderRect
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            doubleArrow?.layoutDirection = View.LAYOUT_DIRECTION_LTR
        }
        doubleArrow?.draw(canvas)
    }


    override fun onDown(p0: MotionEvent?): Boolean {
        if (p0 != null) {
            if (isRectPressed(drawInfo.trackHeadersRect, p0.x, p0.y)) {
                eventState.addState(eventState.IS_TRACKHEADER_ON_DOWN)
                return true
            }
        }
        return false

    }

    override fun onShowPress(p0: MotionEvent?) {


    }

    override fun onSingleTapUp(p0: MotionEvent?): Boolean {
        if (eventState.isStateActive(eventState.IS_TRACKHEADER_ON_DOWN) && p0 != null) {
            for (track in drawInfo.UITracksOnDraw) {
                if (!drawInfo.isTrackHeaderExpanded) {
                    if (isRectPressed(track.expandTrackHeaderRect, p0.x, p0.y)) {
                        DrawInfoManager.expandTrackHeader(drawInfo)
                        return true

                    }else if(isRectPressed(track.trackHeaderRect, p0.x, p0.y)){
                        DrawInfoManager.selectSegment(null, null, drawInfo)
                        drawInfo.selectedTrack = track
                        return true
                    }
                } else {
                    when {
                        isRectPressed(track.expandTrackHeaderRect, p0.x, p0.y) -> {
                            DrawInfoManager.expandTrackHeader(drawInfo)
                            return true

                        }
                        isRectPressed(track.expandTrackRect, p0.x, p0.y) -> {
                            DrawInfoManager.expandTrack(track, drawInfo)
                            return true

                        }
                        isRectPressed(track.muteRect, p0.x, p0.y) -> {
                            track.isMuted = !track.isMuted
                            return true

                        }
                        isRectPressed(track.settingsRect, p0.x, p0.y) -> {
                            Toast.makeText(
                                drawInfo.context,
                                "Pressed: ${track.trackName}`s settings",
                                Toast.LENGTH_SHORT
                            ).show()
                            return true

                        }
                        isRectPressed(track.trackHeaderRect, p0.x, p0.y) -> {
                            DrawInfoManager.selectSegment(null, null, drawInfo)
                            drawInfo.selectedTrack = track
                            return true
                        }
                    }
                }
            }
        }
        /*for (track in drawInfo.UITracksOnDraw) {
            if (p0 != null) {
                if (!drawInfo.isTrackHeaderExpanded) {
                    if (isRectPressed(track.expandTrackHeaderRect, p0.x, p0.y)) {

                        DrawInfoManager.expandTrackHeader(drawInfo)
                        eventState.removeState(CompositionState.Id.IS_TRACK_HEADER_PRESSED)
                        return true

                    }
                } else {
                    when {
                        isRectPressed(track.expandTrackHeaderRect, p0.x, p0.y) -> {

                            DrawInfoManager.expandTrackHeader(drawInfo)
                            eventState.removeState(CompositionState.Id.IS_TRACK_HEADER_PRESSED)
                            return true

                        }
                        isRectPressed(track.expandTrackRect, p0.x, p0.y) -> {

                            DrawInfoManager.expandTrack(track, drawInfo)
                            eventState.removeState(CompositionState.Id.IS_TRACK_HEADER_PRESSED)
                            return true

                        }
                        isRectPressed(track.muteRect, p0.x, p0.y) -> {

                            track.isMuted = !track.isMuted
                            return true

                        }
                        isRectPressed(track.settingsRect, p0.x, p0.y) -> {

                            Toast.makeText(
                                drawInfo.context,
                                "Pressed: ${track.trackName}`s settings",
                                Toast.LENGTH_SHORT
                            ).show()
                            return true
                        }
                    }
                }
            }
        }*/

        return false
    }


    override fun onScroll(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {
        //Log.e("TrackHeaderLayout", "onScroll")
        if (eventState.isStateActive(eventState.IS_TRACKHEADER_ON_DOWN) && p0 != null) {
            DrawInfoManager.verticalShift(p3, drawInfo)
            return true
        }

/*        if (eventState.isActive(CompositionState.Id.IS_TRACK_HEADER_PRESSED)) {
            DrawInfoManager.verticalShift(p3, drawInfo)
            return true
        }*/
        return false
    }

    override fun onLongPress(p0: MotionEvent?) {

    }

    override fun onFling(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {

        return false
    }
}