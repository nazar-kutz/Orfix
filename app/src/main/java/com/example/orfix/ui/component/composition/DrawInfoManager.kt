package com.example.orfix.ui.component.composition

import android.content.Context
import android.graphics.Rect
import android.util.Log
import androidx.core.content.ContextCompat
import com.example.orfix.R
import com.example.orfix.ui.component.composition.layer.data.*
import java.lang.Exception
import kotlin.math.absoluteValue
import kotlin.random.Random


object DrawInfoManager {

    const val SEGMENT_INDENT = 6


    fun setViewSizes(width: Int, height: Int, drawInfo: DrawInfo, context: Context) {
        drawInfo.compositionViewRect = Rect(0, 0, width, height)
        try {
            drawInfo.timelineRect = Rect(
                drawInfo.compositionViewRect.left,
                drawInfo.compositionViewRect.top,
                drawInfo.compositionViewRect.right,
                context.resources.getDimension(R.dimen.timeline_height).toInt()
            )

            drawInfo.timelineSerifsRect = Rect(
                drawInfo.trackHeadersRect.right,
                drawInfo.timelineRect.top,
                drawInfo.timelineRect.right,
                drawInfo.timelineRect.bottom
            )

            drawInfo.compositionFieldRect = Rect(
                drawInfo.trackHeadersRect.right,
                drawInfo.timelineRect.bottom,
                drawInfo.compositionViewRect.right,
                drawInfo.compositionViewRect.bottom
            )
        }catch (e: Exception){
            Log.e("setViewSizes", "Init Error")
        }
    }

    fun initDrawInfo(drawInfo: DrawInfo, context: Context) {
        drawInfo.context = context
        initColorPool(drawInfo)

        drawInfo.timelineRect = Rect(
            drawInfo.compositionViewRect.left,
            drawInfo.compositionViewRect.top,
            drawInfo.compositionViewRect.right,
            context.resources.getDimension(R.dimen.timeline_height).toInt()
        )

        drawInfo.trackHeadersRect = Rect(
            drawInfo.compositionViewRect.left,
            drawInfo.timelineRect.bottom,
            (drawInfo.context.resources.getDimension(R.dimen.track_header_width_collapsed).toInt()),
            drawInfo.compositionViewRect.bottom
        )

        drawInfo.timelineSerifsRect = Rect(
            drawInfo.trackHeadersRect.right,
            drawInfo.timelineRect.top,
            drawInfo.timelineRect.right,
            drawInfo.timelineRect.bottom
        )

        drawInfo.compositionFieldRect = Rect(
            drawInfo.trackHeadersRect.right,
            drawInfo.timelineRect.bottom,
            drawInfo.compositionViewRect.right,
            drawInfo.compositionViewRect.bottom
        )

        drawInfo.trackHeaderHeight =
            (drawInfo.context.resources.getDimension(R.dimen.track_header_height).toInt())

        drawInfo.maxStepPPQN = 256
        drawInfo.minStepPPQN = 8
        drawInfo.maxHorizontalStepPx = 80
        drawInfo.minHorizontalStepPX = 40

        drawInfo.stepPPQN = 16
        drawInfo.horizontalStepPx = 60f
        drawInfo.shiftPPQN = 0
        drawInfo.horizontalShiftPx = 0f

        drawInfo.cursorPPQN = 128

        drawInfo.segmentButtons = UISegmentButtons(
            drawInfo.context.resources.getDimension(R.dimen.track_segment_button_size).toInt()
        )
        drawInfo.segmentButtons.buttons.add(
            UIButton(
                ContextCompat.getDrawable(
                    drawInfo.context,
                    R.drawable.ic_delete
                ), "delete"
            )
        )
        drawInfo.segmentButtons.buttons.add(
            UIButton(
                ContextCompat.getDrawable(
                    drawInfo.context,
                    R.drawable.ic_edit
                ), "edit"
            )
        )

        drawInfo.newSegmentButton = UIButton(
            ContextCompat.getDrawable(
                drawInfo.context,
                R.drawable.ic_add
            ), "newSegment"
        )

        drawInfo.newTrackButton = UIButton(
            ContextCompat.getDrawable(
                drawInfo.context,
                R.drawable.ic_add_track
            ), "newTrack"
        )

        fillTracks(drawInfo)
        updateVerticalLines(drawInfo)
        updateUITracks(drawInfo, true)
        updateUISegments(drawInfo)
        updateCursorPosition(drawInfo)
        updateUITrackRects(drawInfo)
    }

    fun initColorPool(drawInfo: DrawInfo) {
        drawInfo.colors.add(ContextCompat.getColor(drawInfo.context, R.color.track_1))
        drawInfo.colors.add(ContextCompat.getColor(drawInfo.context, R.color.track_2))
        drawInfo.colors.add(ContextCompat.getColor(drawInfo.context, R.color.track_3))
        drawInfo.colors.add(ContextCompat.getColor(drawInfo.context, R.color.track_4))
        drawInfo.colors.add(ContextCompat.getColor(drawInfo.context, R.color.track_5))
        drawInfo.colors.add(ContextCompat.getColor(drawInfo.context, R.color.track_6))
        drawInfo.colors.add(ContextCompat.getColor(drawInfo.context, R.color.track_7))
        drawInfo.colors.add(ContextCompat.getColor(drawInfo.context, R.color.track_8))
        drawInfo.colors.add(ContextCompat.getColor(drawInfo.context, R.color.track_9))
        drawInfo.colors.add(ContextCompat.getColor(drawInfo.context, R.color.track_10))
        drawInfo.colors.add(ContextCompat.getColor(drawInfo.context, R.color.track_11))
        drawInfo.colors.add(ContextCompat.getColor(drawInfo.context, R.color.track_12))
        drawInfo.colors.add(ContextCompat.getColor(drawInfo.context, R.color.track_13))
        drawInfo.colors.add(ContextCompat.getColor(drawInfo.context, R.color.track_14))
        drawInfo.colors.add(ContextCompat.getColor(drawInfo.context, R.color.track_15))
        drawInfo.colors.add(ContextCompat.getColor(drawInfo.context, R.color.track_16))

    }

    fun deleteSelectedSegment(drawInfo: DrawInfo) {
        if (drawInfo.selectedSegment != null && drawInfo.selectedTrack != null) {
            for (segment in drawInfo.selectedTrack!!.segments) {
                if (segment == drawInfo.selectedSegment) {
                    drawInfo.selectedTrack!!.segments.remove(segment)
                    break
                }
            }


            selectSegment(null, null, drawInfo)
        }

        updateUISegments(drawInfo)
    }

    private fun updateVerticalLines(di: DrawInfo) {

        var pointerPPQN: Int = di.shiftPPQN
        var pointerPx: Float = di.trackHeadersRect.width() + di.horizontalShiftPx

        if (di.verticalLines.isNotEmpty()) {
            di.verticalLines.clear()
        }

        while (pointerPx < di.compositionViewRect.right) {
            if (pointerPPQN % (di.stepPPQN * 16) == 0 || pointerPPQN == 0) {
                if (pointerPPQN % 512 == 0 || pointerPPQN == 0) {
                    var timeLineNumber = 1
                    var partyNumber = 1
                    while ((512 * (partyNumber)) <= pointerPPQN) {
                        partyNumber++
                        timeLineNumber = partyNumber
                    }
                    di.verticalLines.add(
                        VerticalLine(
                            LineType.BIG,
                            pointerPx,
                            pointerPPQN,
                            timeLineNumber
                        )
                    )
                } else {
                    di.verticalLines.add(
                        VerticalLine(
                            LineType.BIG,
                            pointerPx,
                            pointerPPQN
                        )
                    )
                }
            } else if (pointerPPQN % (di.stepPPQN * 4) == 0) {
                di.verticalLines.add(
                    VerticalLine(
                        LineType.MEDIUM,
                        pointerPx,
                        pointerPPQN
                    )
                )
            } else if (pointerPPQN % (di.stepPPQN * 1) == 0) {
                di.verticalLines.add(
                    VerticalLine(
                        LineType.SMALL,
                        pointerPx,
                        pointerPPQN
                    )
                )
            }

            pointerPPQN += di.stepPPQN
            pointerPx += di.horizontalStepPx
        }
    }

    private fun fillTracks(drawInfo: DrawInfo) {

        if (drawInfo.tracks.isNotEmpty()) {
            drawInfo.tracks.clear()
        }

        drawInfo.tracks.add(
            UITrack(
                false,
                "Bass",
                0,
                1,
                false,
                false,
                ContextCompat.getColor(drawInfo.context, R.color.track_17)
            )
        )
        drawInfo.tracks[0].childUITracks.add(
            UITrack(
                false,
                "child1",
                0,
                0,
                false,
                false,
                ContextCompat.getColor(drawInfo.context, R.color.track_16)
            )
        )
        drawInfo.tracks[0].childUITracks.add(
            UITrack(
                false,
                "child2",
                0,
                0,
                false,
                false,
                ContextCompat.getColor(drawInfo.context, R.color.track_14)
            )
        )
        drawInfo.tracks[0].childUITracks[1].addSegment(UISegment(16, 95, 0))
        drawInfo.tracks[0].childUITracks[1].addSegment(UISegment(224, 127, 1))
        drawInfo.tracks[0].childUITracks[0].addSegment(UISegment(16, 95, 0))
        drawInfo.tracks[0].childUITracks[0].addSegment(UISegment(224, 127, 1))
        drawInfo.tracks[0].childUITracks[1].childUITracks.add(
            UITrack(
                false,
                "child2_1",
                0,
                0,
                true,
                false,
                ContextCompat.getColor(drawInfo.context, R.color.track_4)
            )
        )
        drawInfo.tracks[0].childUITracks[1].childUITracks.add(
            UITrack(
                false,
                "child2_2",
                0,
                0,
                true,
                false,
                ContextCompat.getColor(drawInfo.context, R.color.track_2)
            )
        )

        drawInfo.tracks[0].childUITracks.add(
            UITrack(
                false,
                "child3",
                0,
                0,
                false,
                false,
                ContextCompat.getColor(drawInfo.context, R.color.track_9)
            )
        )
        drawInfo.tracks[0].childUITracks.add(
            UITrack(
                false,
                "child4",
                0,
                0,
                false,
                false,
                ContextCompat.getColor(drawInfo.context, R.color.track_7)
            )
        )
        drawInfo.tracks.add(
            UITrack(
                false,
                "Melody",
                0,
                0,
                false,
                true,
                ContextCompat.getColor(drawInfo.context, R.color.track_7)
            )
        )
        drawInfo.tracks.add(
            UITrack(
                false,
                "Parent_3",
                0,
                0,
                false,
                true,
                ContextCompat.getColor(drawInfo.context, R.color.track_18)
            )
        )
        drawInfo.tracks.add(
            UITrack(
                false,
                "Parent_4",
                0,
                0,
                false,
                false,
                ContextCompat.getColor(drawInfo.context, R.color.track_15)
            )
        )
        drawInfo.tracks.add(
            UITrack(
                false,
                "Parent_5",
                0,
                0,
                false,
                false,
                ContextCompat.getColor(drawInfo.context, R.color.track_9)
            )
        )
        drawInfo.tracks.add(
            UITrack(
                false,
                "Parent_6",
                0,
                0,
                false,
                false,
                ContextCompat.getColor(drawInfo.context, R.color.track_13)
            )
        )
        drawInfo.tracks.add(
            UITrack(
                false,
                "Parent_7",
                0,
                0,
                false,
                false,
                ContextCompat.getColor(drawInfo.context, R.color.track_8)
            )
        )

        drawInfo.tracks[0].addSegment(UISegment(0, 63, 0))
        drawInfo.tracks[0].addSegment(UISegment(96, 63, 1))
        drawInfo.tracks[0].addSegment(UISegment(256, 127, 2))
//        drawInfo.tracks[0].color = ContextCompat.getColor(drawInfo.context, R.color.track_cyan)

        drawInfo.tracks[1].addSegment(UISegment(80, 111, 0))
        drawInfo.tracks[1].addSegment(UISegment(224, 111, 1))
//        drawInfo.tracks[1].color = ContextCompat.getColor(drawInfo.context, R.color.track_green)

        drawInfo.tracks[2].addSegment(UISegment(0, 639, 0))
//        drawInfo.tracks[2].color = ContextCompat.getColor(drawInfo.context, R.color.track_purple)

        drawInfo.UITracks =
            drawInfo.tracks.clone() as ArrayList<UITrack> // #todo temporary fill until backend
    }

    fun cursorShift(touchPosition: Float, drawInfo: DrawInfo) {
        var minVerticalLineIndex = 0
        var maxVerticalLineIndex = drawInfo.verticalLines.size - 1
        var middleLineIndex = minVerticalLineIndex + (maxVerticalLineIndex / 2)

        var previousLine: VerticalLine? = null
        var nextLine: VerticalLine? = null
        var middleLine: VerticalLine = drawInfo.verticalLines[middleLineIndex]

        while ((maxVerticalLineIndex - minVerticalLineIndex) != 1) {
            if (middleLine.horizontalPosition == touchPosition) {
                drawInfo.cursorPPQN = middleLine.PPQN
                break
            } else if (touchPosition > middleLine.horizontalPosition) {
                minVerticalLineIndex = middleLineIndex
            } else if (touchPosition < middleLine.horizontalPosition) {
                maxVerticalLineIndex = middleLineIndex
            }

            middleLineIndex =
                minVerticalLineIndex + ((maxVerticalLineIndex - minVerticalLineIndex) / 2)
            middleLine = drawInfo.verticalLines[middleLineIndex]
            previousLine = drawInfo.verticalLines[minVerticalLineIndex]
            nextLine = drawInfo.verticalLines[maxVerticalLineIndex]
        }

        if (previousLine != null && nextLine != null) {
            if (touchPosition - previousLine.horizontalPosition < (touchPosition - nextLine.horizontalPosition).absoluteValue) {
                drawInfo.cursorPPQN = previousLine.PPQN
            } else {
                drawInfo.cursorPPQN = nextLine.PPQN
            }
        }

        updateCursorPosition(drawInfo)
    }

    private fun updateCursorPosition(drawInfo: DrawInfo) {
        var horizontalCursorPosition = 0f
        val iconPlaySize =
            drawInfo.context.resources.getDimension(R.dimen.timeline_height).toInt()

        for (line in drawInfo.verticalLines) {
            if (line.PPQN >= drawInfo.cursorPPQN) {
                val ratio = ((line.PPQN - drawInfo.cursorPPQN).toFloat() / drawInfo.stepPPQN)
                horizontalCursorPosition =
                    line.horizontalPosition - (drawInfo.horizontalStepPx * ratio)
            }
        }

        drawInfo.timelineCursorRect = Rect(
            ((horizontalCursorPosition - iconPlaySize / 2).toInt()),
            drawInfo.timelineRect.top,
            ((horizontalCursorPosition + iconPlaySize / 2).toInt()),
            drawInfo.timelineRect.bottom
        )
    }

    fun scale(scale: Float, focusX: Float, drawInfo: DrawInfo) {

        var index = 0
        var minVerticalLineIndex = 0
        var maxVerticalLineIndex = drawInfo.verticalLines.size - 1
        var middleLineIndex = minVerticalLineIndex + (maxVerticalLineIndex / 2)

        var previousLine: VerticalLine? = null
        var nextLine: VerticalLine? = null
        var middleLine: VerticalLine = drawInfo.verticalLines[middleLineIndex]

        while ((maxVerticalLineIndex - minVerticalLineIndex) != 1) {
            if (middleLine.horizontalPosition == focusX) {
                drawInfo.cursorPPQN = middleLine.PPQN
                break
            } else if (focusX > middleLine.horizontalPosition) {
                minVerticalLineIndex = middleLineIndex
            } else if (focusX < middleLine.horizontalPosition) {
                maxVerticalLineIndex = middleLineIndex
            }

            middleLineIndex =
                minVerticalLineIndex + ((maxVerticalLineIndex - minVerticalLineIndex) / 2)
            middleLine = drawInfo.verticalLines[middleLineIndex]
            previousLine = drawInfo.verticalLines[minVerticalLineIndex]
            nextLine = drawInfo.verticalLines[maxVerticalLineIndex]
        }

        if (previousLine != null && nextLine != null) {
            if (focusX - previousLine.horizontalPosition < (focusX - nextLine.horizontalPosition).absoluteValue) {
                index = minVerticalLineIndex
            } else {
                index = maxVerticalLineIndex
            }
        }

        if (scale > 0) {
            if (drawInfo.horizontalStepPx < drawInfo.maxHorizontalStepPx) {
                drawInfo.horizontalStepPx += scale
                drawInfo.horizontalShiftPx = drawInfo.horizontalShiftPx - (scale * index)
            }
            if (drawInfo.horizontalStepPx >= drawInfo.maxHorizontalStepPx && drawInfo.stepPPQN > drawInfo.minStepPPQN) {
                drawInfo.horizontalStepPx =
                    drawInfo.minHorizontalStepPX.toFloat() + (drawInfo.maxHorizontalStepPx - drawInfo.horizontalStepPx)
                drawInfo.stepPPQN /= 2
            }
        } else if (scale < 0) {
            if (drawInfo.horizontalStepPx > drawInfo.minHorizontalStepPX && drawInfo.stepPPQN < drawInfo.maxStepPPQN) {
                drawInfo.horizontalStepPx += scale
                drawInfo.horizontalShiftPx = drawInfo.horizontalShiftPx - (scale * index)

            }
            if (drawInfo.horizontalStepPx <= drawInfo.minHorizontalStepPX && drawInfo.stepPPQN < drawInfo.maxStepPPQN) {
                drawInfo.horizontalStepPx =
                    drawInfo.maxHorizontalStepPx.toFloat() - (drawInfo.minHorizontalStepPX - drawInfo.horizontalStepPx)
                drawInfo.stepPPQN *= 2

                if (drawInfo.shiftPPQN > 0 && drawInfo.shiftPPQN % drawInfo.stepPPQN != 0) {
                    drawInfo.shiftPPQN = drawInfo.shiftPPQN - (drawInfo.stepPPQN / 2)
                    drawInfo.horizontalShiftPx =
                        drawInfo.horizontalShiftPx - (drawInfo.horizontalStepPx / 2)
                }
            }
        }

        drawInfo.newSegmentButton.isVisible = false

        overShiftCheck(drawInfo)
        updateVerticalLines(drawInfo)
        updateUISegments(drawInfo)
        updateCursorPosition(drawInfo)
        updateSegmentResizeButtons(drawInfo)
        updateSegmentEditButtons(drawInfo)

    }

    fun verticalShift(shiftPx: Float, drawInfo: DrawInfo) {
        var pointerY = 0
        if (shiftPx > 0) {
            if (drawInfo.UITracksOnDraw.first().trackHeaderRect.top - shiftPx <= drawInfo.maxVerticalShiftPx.unaryMinus()) {

                pointerY = drawInfo.maxVerticalShiftPx.unaryMinus()

            } else {

                pointerY = drawInfo.UITracksOnDraw.first().trackHeaderRect.top - shiftPx.toInt()

            }
        } else if (shiftPx < 0) {
            if (drawInfo.UITracksOnDraw.first().trackHeaderRect.top - shiftPx >= drawInfo.timelineRect.bottom) {

                pointerY = drawInfo.timelineRect.bottom

            } else {

                pointerY = (drawInfo.UITracksOnDraw.first().trackHeaderRect.top - shiftPx).toInt()

            }
        } else {

            pointerY = drawInfo.UITracksOnDraw.first().trackHeaderRect.top

        }

        for (track in drawInfo.UITracksOnDraw) {
            track.trackHeaderRect.top = pointerY
            track.trackHeaderRect.bottom =
                track.trackHeaderRect.top + drawInfo.trackHeaderHeight
            pointerY = track.trackHeaderRect.bottom
        }
        updateUISegments(drawInfo)
        updateUITrackRects(drawInfo)
        updateSegmentResizeButtons(drawInfo)
        updateSegmentEditButtons(drawInfo)
        updateNewSegmentButton(drawInfo)
    }

    fun horizontalShift(shiftPx: Float, drawInfo: DrawInfo) {
        drawInfo.horizontalShiftPx = drawInfo.horizontalShiftPx - shiftPx

        overShiftCheck(drawInfo)
        updateVerticalLines(drawInfo)
        updateUISegments(drawInfo)
        updateCursorPosition(drawInfo)
        updateSegmentResizeButtons(drawInfo)
        updateSegmentEditButtons(drawInfo)
        updateNewSegmentButton(drawInfo)

    }

    private fun overShiftCheck(drawInfo: DrawInfo) {
        if (drawInfo.horizontalShiftPx < 0) {
            while (drawInfo.horizontalShiftPx < 0) {
                drawInfo.horizontalShiftPx = drawInfo.horizontalStepPx + drawInfo.horizontalShiftPx
                drawInfo.shiftPPQN += drawInfo.stepPPQN
            }
        } else if (drawInfo.horizontalShiftPx > drawInfo.horizontalStepPx) {
            while (drawInfo.horizontalShiftPx > drawInfo.horizontalStepPx) {
                if (drawInfo.shiftPPQN > 0) {
                    drawInfo.horizontalShiftPx -= drawInfo.horizontalStepPx
                    drawInfo.shiftPPQN -= drawInfo.stepPPQN
                } else {
                    drawInfo.horizontalShiftPx = 0f
                }
            }
        }
        if (drawInfo.shiftPPQN == 0 && drawInfo.horizontalShiftPx > 0) {
            drawInfo.horizontalShiftPx = 0f
        }
    }

    fun updateUISegments(drawInfo: DrawInfo) {
        val pxFactor = drawInfo.horizontalStepPx / drawInfo.stepPPQN
        val firstLinePPQN = drawInfo.verticalLines.first().PPQN
        val firstLinePx = drawInfo.verticalLines.first().horizontalPosition

        for (track in drawInfo.UITracksOnDraw) {
            for (segment in track.segments) {
                segment.segmentRect = Rect(
                    (firstLinePx - (pxFactor * (firstLinePPQN - segment.startPPQN))).toInt() + SEGMENT_INDENT,
                    track.trackHeaderRect.top + SEGMENT_INDENT,
                    (firstLinePx - (pxFactor * (firstLinePPQN - segment.endPPQN))).toInt() - SEGMENT_INDENT + 5,
                    track.trackHeaderRect.bottom - SEGMENT_INDENT
                )
            }
        }
    }

    private fun updateUITracks(drawInfo: DrawInfo, firstInit: Boolean = false) {
        var pointerTopPosY = 0
        if (firstInit) {
            drawInfo.timelineRect.height().unaryMinus()
        }else{
            pointerTopPosY = drawInfo.UITracksOnDraw.first().trackHeaderRect.top

        }
/*        val pointerTopPosY = if (firstInit) {
            drawInfo.timelineRect.height().unaryMinus() //#todo fix first init
        } else {
            drawInfo.UITracksOnDraw.first().trackHeaderRect.top
        }*/

        if (drawInfo.UITracksOnDraw.isNotEmpty()) {
            drawInfo.UITracksOnDraw.clear()
        }



        prepareUITrack(drawInfo.UITracks, drawInfo, pointerTopPosY, 0)
        updateUITrackRects(drawInfo)



        if (drawInfo.UITracksOnDraw.size * drawInfo.trackHeaderHeight >= drawInfo.compositionViewRect.height()) {
            drawInfo.maxVerticalShiftPx =
                (drawInfo.UITracksOnDraw.last().trackHeaderRect.bottom - drawInfo.UITracksOnDraw.first().trackHeaderRect.top
                        - drawInfo.compositionViewRect.height()) + drawInfo.newTrackButton.rect.height()
/*            drawInfo.maxVerticalShiftPx =
                ((drawInfo.UITracksOnDraw.size * drawInfo.trackHeaderHeight) - drawInfo.compositionViewRect.height()) + drawInfo.newTrackButton.rect.height()*/
        }
    }

    private fun prepareUITrack(
        tracks: ArrayList<UITrack>,
        drawInfo: DrawInfo,
        pointerTopPosY: Int,
        numberInBranch: Int,
        parentTrack: UITrack? = null
    ): Int {
        var pointerPosY = pointerTopPosY
        for (track in tracks) {
            track.trackHeaderRect.top = pointerPosY
            track.trackHeaderRect.bottom = track.trackHeaderRect.top + drawInfo.trackHeaderHeight
            track.trackHeaderRect.left = drawInfo.trackHeadersRect.left
            track.trackHeaderRect.right = drawInfo.trackHeadersRect.right
            track.extendsCount = numberInBranch
            track.childCount = track.childUITracks.size
            track.parentTrack = parentTrack
            pointerPosY = track.trackHeaderRect.bottom

            drawInfo.UITracksOnDraw.add(track)

            if (track.isExpanded && track.childUITracks.isNotEmpty()) {
                pointerPosY =
                    prepareUITrack(
                        track.childUITracks,
                        drawInfo,
                        pointerPosY,
                        numberInBranch + 1,
                        track
                    )
            }
        }
        return pointerPosY
    }

    private fun updateUITrackRects(drawInfo: DrawInfo) {
        for (track in drawInfo.UITracksOnDraw) {

            track.trackField = Rect(
                drawInfo.compositionFieldRect.left,
                track.trackHeaderRect.top,
                drawInfo.compositionFieldRect.right,
                track.trackHeaderRect.bottom
            )

            if (drawInfo.isTrackHeaderExpanded) {
                track.expandTrackHeaderRect = Rect(
                    (track.trackHeaderRect.right - drawInfo.context.resources.getDimension(R.dimen.track_headers_collapse_icon_size)).toInt(),
                    track.trackHeaderRect.top,
                    track.trackHeaderRect.right,
                    (track.trackHeaderRect.top + drawInfo.context.resources.getDimension(R.dimen.track_headers_collapse_icon_size)).toInt()
                )

                track.trackNameRect = Rect(
                    track.trackHeaderRect.left + 20,
                    (track.trackHeaderRect.top + track.trackHeaderRect.height() / 2 - drawInfo.context.resources.getDimension(
                        R.dimen.track_header_name_font_size_expanded
                    ) / 2).toInt(),
                    track.expandTrackHeaderRect.left - 20,
                    (track.trackHeaderRect.bottom - track.trackHeaderRect.height() / 2 + drawInfo.context.resources.getDimension(
                        R.dimen.track_header_name_font_size_expanded
                    ) / 2).toInt()
                )

                track.expandTrackRect = Rect(
                    track.trackHeaderRect.left + 10,
                    (track.trackHeaderRect.bottom - 10 - drawInfo.context.resources.getDimension(R.dimen.track_header_icon_size)).toInt(),
                    (track.trackHeaderRect.left + 10 + drawInfo.context.resources.getDimension(R.dimen.track_header_icon_size)).toInt(),
                    track.trackHeaderRect.bottom - 10
                )

                track.settingsRect = Rect(
                    (track.trackHeaderRect.left + (track.trackHeaderRect.width() / 2) - drawInfo.context.resources.getDimension(
                        R.dimen.track_header_icon_size
                    ) / 2).toInt(),
                    track.trackHeaderRect.bottom - 10 - drawInfo.context.resources.getDimension(R.dimen.track_header_icon_size)
                        .toInt(),
                    (track.trackHeaderRect.left + (track.trackHeaderRect.width() / 2) + drawInfo.context.resources.getDimension(
                        R.dimen.track_header_icon_size
                    ) / 2).toInt(),
                    track.trackHeaderRect.bottom - 10
                )

                track.muteRect = Rect(
                    (track.trackHeaderRect.right - 10 - drawInfo.context.resources.getDimension(R.dimen.track_header_icon_size)).toInt(),
                    (track.trackHeaderRect.bottom - 10 - drawInfo.context.resources.getDimension(R.dimen.track_header_icon_size)).toInt(),
                    track.trackHeaderRect.right - 10,
                    track.trackHeaderRect.bottom - 10
                )
                track.childCountRect = Rect(

                )
                track.extendsIconRect = Rect(
                    track.trackHeaderRect.left + 10,
                    track.trackHeaderRect.top + 10,
                    (track.trackHeaderRect.left + 10 + drawInfo.context.resources.getDimension(R.dimen.track_header_parent_indicator_icon_size)).toInt(),
                    (track.trackHeaderRect.top + 10 + drawInfo.context.resources.getDimension(R.dimen.track_header_parent_indicator_icon_size)).toInt()
                )
                track.extendsCountRect = Rect(
                    track.extendsIconRect.right + 5,
                    (track.extendsIconRect.top + track.extendsIconRect.height() / 2 - drawInfo.context.resources.getDimension(
                        R.dimen.track_header_extends_count_font_size
                    )).toInt(),
                    (track.extendsIconRect.right + drawInfo.context.resources.getDimension(R.dimen.track_header_extends_count_font_size) + 5).toInt(),
                    (track.extendsIconRect.top + track.extendsIconRect.height() / 2 + drawInfo.context.resources.getDimension(
                        R.dimen.track_header_extends_count_font_size
                    )).toInt(),
//                    (track.extendsIconRect.top + drawInfo.context.resources.getDimension(R.dimen.track_header_extends_count_font_size)).toInt()
                )
            } else {
                track.trackNameRect = Rect(
                    track.trackHeaderRect.left + 10,
                    (track.trackHeaderRect.bottom - 20 - drawInfo.context.resources.getDimension(R.dimen.track_header_name_font_size_collapsed)).toInt(),
                    track.trackHeaderRect.right - 15,
                    track.trackHeaderRect.bottom - 20
                )

                track.expandTrackHeaderRect = Rect(
                    track.trackHeaderRect.left + 10,
                    track.trackHeaderRect.top,
                    track.trackHeaderRect.left + drawInfo.trackHeadersRect.width() - 10,
                    track.trackNameRect.top
                )
            }
        }


        val newTrackIconSize =
            drawInfo.context.resources.getDimension(R.dimen.track_new_track_button_size).toInt()


        drawInfo.newTrackButton.rect = Rect(
            drawInfo.compositionViewRect.centerX() - (drawInfo.trackHeadersRect.width() / 2),
            drawInfo.UITracksOnDraw.last().trackHeaderRect.bottom,
            drawInfo.compositionViewRect.centerX() + (drawInfo.trackHeadersRect.width() / 2),
            drawInfo.UITracksOnDraw.last().trackHeaderRect.bottom + drawInfo.trackHeaderHeight
        )

        drawInfo.newTrackButton.iconRect = Rect(
            drawInfo.newTrackButton.rect.centerX() - newTrackIconSize,
            drawInfo.newTrackButton.rect.centerY() - newTrackIconSize,
            drawInfo.newTrackButton.rect.centerX() + newTrackIconSize,
            drawInfo.newTrackButton.rect.centerY() + newTrackIconSize,
        )

    }

    fun expandTrackHeader(drawInfo: DrawInfo) {
        if (drawInfo.isTrackHeaderExpanded) {
            drawInfo.isTrackHeaderExpanded = false
            drawInfo.trackHeadersRect.right =
                (drawInfo.context.resources.getDimension(R.dimen.track_header_width_collapsed)
                    .toInt())
            drawInfo.compositionFieldRect.left = drawInfo.trackHeadersRect.right
        } else {
            drawInfo.isTrackHeaderExpanded = true
            drawInfo.trackHeadersRect.right =
                (drawInfo.context.resources.getDimension(R.dimen.track_header_width_expanded)
                    .toInt())
            drawInfo.compositionFieldRect.left = drawInfo.trackHeadersRect.right
        }




        updateVerticalLines(drawInfo)
        updateUITracks(drawInfo)
        updateUISegments(drawInfo)
        updateCursorPosition(drawInfo)
        updateUITrackRects(drawInfo)
        updateSegmentResizeButtons(drawInfo)

    }

    fun expandTrack(track: UITrack, drawInfo: DrawInfo) {
        if (track.childUITracks.isNotEmpty()) {
            track.isExpanded = !track.isExpanded

            updateUITracks(drawInfo)
            updateUISegments(drawInfo)
            updateSegmentResizeButtons(drawInfo)
            updateNewSegmentButton(drawInfo)

        }
    }

    fun selectSegment(segment: UISegment?, track: UITrack?, drawInfo: DrawInfo) {


        if (drawInfo.selectedSegment != null && drawInfo.selectedTrack != null) {
            drawInfo.selectedSegment!!.isSelected = false
            drawInfo.selectedTrack!!.isSelected = false
        }

        drawInfo.selectedSegment = segment
        drawInfo.selectedTrack = track

        if (drawInfo.selectedSegment != null && drawInfo.selectedTrack != null) {
            drawInfo.selectedTrack!!.isSelected = true
            drawInfo.selectedSegment!!.isSelected = true



            updateSegmentResizeButtons(drawInfo)
        }

    }

    fun editSegment(segment: UISegment?, track: UITrack?, drawInfo: DrawInfo) {

        drawInfo.isSegmentEditing = true
        selectSegment(segment, track, drawInfo)
        updateSegmentEditButtons(drawInfo)
    }

    fun updateSegmentEditButtons(drawInfo: DrawInfo) {

        if (drawInfo.selectedSegment != null) {
            drawInfo.segmentButtons.setButtonsRect(
                drawInfo.selectedSegment!!.segmentRect.centerX(),
                (drawInfo.selectedSegment!!.segmentRect.bottom + drawInfo.context.resources.getDimension(
                    R.dimen.track_segment_button_size
                )).toInt()
            )
        }
    }

    fun updateSegmentResizeButtons(drawInfo: DrawInfo) {
        val SEGMENT_RESIZE_RECT_SIZE =
            drawInfo.context.resources.getDimension(R.dimen.track_segment_resize_button_size)
                .toInt()

        if (drawInfo.selectedSegment != null) {
            if (drawInfo.selectedSegment!!.segmentRect.width() >= SEGMENT_RESIZE_RECT_SIZE) {

                drawInfo.segmentLeftResizeRect = Rect(
                    drawInfo.selectedSegment!!.segmentRect.left - SEGMENT_RESIZE_RECT_SIZE / 2,
                    drawInfo.selectedSegment!!.segmentRect.centerY() - SEGMENT_RESIZE_RECT_SIZE / 2,
                    drawInfo.selectedSegment!!.segmentRect.left + SEGMENT_RESIZE_RECT_SIZE / 2,
                    drawInfo.selectedSegment!!.segmentRect.centerY() + SEGMENT_RESIZE_RECT_SIZE / 2
                )


                drawInfo.segmentRightResizeRect = Rect(
                    drawInfo.selectedSegment!!.segmentRect.right - SEGMENT_RESIZE_RECT_SIZE / 2,
                    drawInfo.selectedSegment!!.segmentRect.centerY() - SEGMENT_RESIZE_RECT_SIZE / 2,
                    drawInfo.selectedSegment!!.segmentRect.right + SEGMENT_RESIZE_RECT_SIZE / 2,
                    drawInfo.selectedSegment!!.segmentRect.centerY() + SEGMENT_RESIZE_RECT_SIZE / 2
                )
            } else {
                drawInfo.segmentLeftResizeRect = Rect(
                    drawInfo.selectedSegment!!.segmentRect.left - SEGMENT_RESIZE_RECT_SIZE / 2,
                    drawInfo.selectedSegment!!.segmentRect.centerY() - SEGMENT_RESIZE_RECT_SIZE / 2,
                    drawInfo.selectedSegment!!.segmentRect.left + SEGMENT_RESIZE_RECT_SIZE / 2,
                    drawInfo.selectedSegment!!.segmentRect.centerY() + SEGMENT_RESIZE_RECT_SIZE / 2
                )


                drawInfo.segmentRightResizeRect = Rect(
                    drawInfo.selectedSegment!!.segmentRect.right - SEGMENT_RESIZE_RECT_SIZE / 2,
                    drawInfo.selectedSegment!!.segmentRect.centerY() - SEGMENT_RESIZE_RECT_SIZE / 2,
                    drawInfo.selectedSegment!!.segmentRect.right + SEGMENT_RESIZE_RECT_SIZE / 2,
                    drawInfo.selectedSegment!!.segmentRect.centerY() + SEGMENT_RESIZE_RECT_SIZE / 2
                )
            }
        }
    }

    private fun updateNewSegmentButton(drawInfo: DrawInfo) {

        val pxFactor = drawInfo.horizontalStepPx / drawInfo.stepPPQN
        val firstLinePPQN = drawInfo.verticalLines.first().PPQN
        val firstLinePx = drawInfo.verticalLines.first().horizontalPosition

        for (track in drawInfo.UITracksOnDraw) {
            for (segment in track.segments) {
                segment.segmentRect = Rect(
                    (firstLinePx - (pxFactor * (firstLinePPQN - segment.startPPQN))).toInt() + SEGMENT_INDENT,
                    track.trackHeaderRect.top + SEGMENT_INDENT,
                    (firstLinePx - (pxFactor * (firstLinePPQN - segment.endPPQN))).toInt() - SEGMENT_INDENT + 5,
                    track.trackHeaderRect.bottom - SEGMENT_INDENT
                )
            }
        }


        if (drawInfo.selectedTrack != null) {

            var buttonLeftSide =
                (firstLinePx - (pxFactor * (firstLinePPQN - drawInfo.newSegmentStartPPQN))).toInt() + SEGMENT_INDENT
            var buttonRightSide =
                (firstLinePx - (pxFactor * (firstLinePPQN - drawInfo.newSegmentEndPPQN))).toInt() + SEGMENT_INDENT

            var buttonTopSide = 0
            var buttonBottomSide = 0

            var width = buttonRightSide - buttonLeftSide
            var center = buttonLeftSide + (width / 2)

            var INDENT = 20

            if (width > drawInfo.trackHeaderHeight - 10) {
                buttonTopSide =
                    drawInfo.selectedTrack!!.trackHeaderRect.centerY() - ((drawInfo.trackHeaderHeight - INDENT) / 2)
                buttonBottomSide =
                    drawInfo.selectedTrack!!.trackHeaderRect.centerY() + ((drawInfo.trackHeaderHeight - INDENT) / 2)

                buttonLeftSide = center - ((drawInfo.trackHeaderHeight - INDENT) / 2)

                buttonRightSide = center + ((drawInfo.trackHeaderHeight - INDENT) / 2)


            } else {
                buttonTopSide =
                    drawInfo.selectedTrack!!.trackHeaderRect.centerY() - ((buttonRightSide - buttonLeftSide) / 2)
                buttonBottomSide =
                    drawInfo.selectedTrack!!.trackHeaderRect.centerY() + ((buttonRightSide - buttonLeftSide) / 2)
            }


            drawInfo.newSegmentButton.rect = Rect(
                buttonLeftSide,
                buttonTopSide,
                buttonRightSide,
                buttonBottomSide
            )
        }

    }

    fun setNewSegmentPPQN(touchPosition: Float, drawInfo: DrawInfo) {

        if (touchPosition > drawInfo.verticalLines.last().horizontalPosition) {

            drawInfo.newSegmentStartPPQN = drawInfo.verticalLines.last().PPQN
            drawInfo.newSegmentEndPPQN = drawInfo.newSegmentStartPPQN + drawInfo.stepPPQN
        } else {

            for (line in drawInfo.verticalLines) {
                if (line.horizontalPosition >= touchPosition && (line.PPQN - drawInfo.stepPPQN) >= 0) {

                    drawInfo.newSegmentStartPPQN = line.PPQN - drawInfo.stepPPQN
                    drawInfo.newSegmentEndPPQN = line.PPQN - 1
                    break
                }
            }
        }

        var newSegmentStartPPQN = drawInfo.newSegmentStartPPQN
        var newSegmentDuration = drawInfo.newSegmentEndPPQN - drawInfo.newSegmentStartPPQN

        if (!segmentCollisionCheck(
                UISegment(newSegmentStartPPQN, newSegmentDuration, 0),
                drawInfo
            )
        ) {

            if (segmentCollisionCheck(
                    UISegment(newSegmentStartPPQN - drawInfo.stepPPQN, newSegmentDuration, 0),
                    drawInfo
                )
            ) {
                drawInfo.newSegmentStartPPQN -= drawInfo.stepPPQN
                drawInfo.newSegmentEndPPQN -= drawInfo.stepPPQN

            } else if (segmentCollisionCheck(
                    UISegment(newSegmentStartPPQN + drawInfo.stepPPQN, newSegmentDuration, 0),
                    drawInfo
                )
            ) {
                drawInfo.newSegmentStartPPQN += drawInfo.stepPPQN
                drawInfo.newSegmentEndPPQN += drawInfo.stepPPQN
            }
        }


        var isPPQNExpandLeft: Boolean = true
        var isPPQNExpandRight: Boolean = true
        if (drawInfo.selectedTrack != null) {
            for (segment in drawInfo.selectedTrack!!.segments) {
                if (drawInfo.newSegmentStartPPQN - drawInfo.stepPPQN >= 0) {
                    if (drawInfo.newSegmentStartPPQN - drawInfo.stepPPQN > segment.startPPQN &&
                        drawInfo.newSegmentStartPPQN - drawInfo.stepPPQN < segment.endPPQN
                    ) {
                        isPPQNExpandLeft = false
                    }
                } else {
                    isPPQNExpandLeft = false
                }

                if (drawInfo.newSegmentEndPPQN + drawInfo.stepPPQN > segment.startPPQN &&
                    drawInfo.newSegmentEndPPQN + drawInfo.stepPPQN < segment.endPPQN
                ) {
                    isPPQNExpandRight = false
                }
            }

            if (isPPQNExpandLeft) {
                drawInfo.newSegmentStartPPQN -= drawInfo.stepPPQN
            } else if (isPPQNExpandRight) {
                drawInfo.newSegmentEndPPQN += (drawInfo.stepPPQN)
            }

        }

        drawInfo.newSegmentButton.isVisible = true

        updateNewSegmentButton(drawInfo)
        updateUISegments(drawInfo)

    }

    fun createSegment(drawInfo: DrawInfo) {



        var newSegmentId = 0
        var isIdTaken = false
        if (drawInfo.selectedTrack != null) {

            while (newSegmentId <= 1000) {
                for (segment in drawInfo.selectedTrack!!.segments) {
                    if (newSegmentId == segment.id) {
                        isIdTaken = true
                    }
                }
                if (isIdTaken) {
                    newSegmentId++
                    isIdTaken = false
                } else {
                    break
                }
            }
        }


        if (drawInfo.selectedTrack != null && segmentCollisionCheck(
                UISegment(
                    drawInfo.newSegmentStartPPQN,
                    drawInfo.newSegmentEndPPQN - drawInfo.newSegmentStartPPQN,
                    newSegmentId
                ),
                drawInfo
            )
        ) {
            drawInfo.selectedTrack!!.segments.add(
                UISegment(
                    drawInfo.newSegmentStartPPQN,
                    drawInfo.newSegmentEndPPQN - drawInfo.newSegmentStartPPQN,
                    newSegmentId,
                    true
                )
            )

            drawInfo.selectedSegment = drawInfo.selectedTrack!!.segments.last()
            drawInfo.newSegmentButton.isVisible = false
            updateUISegments(drawInfo)

            editSegment(drawInfo.selectedSegment, drawInfo.selectedTrack, drawInfo)

        }
    }

    fun reSizeSegment(isStartResizing: Boolean, touchPosition: Float, drawInfo: DrawInfo) {

        var minVerticalLineIndex = 0
        var maxVerticalLineIndex = drawInfo.verticalLines.size - 1
        var middleLineIndex = minVerticalLineIndex + (maxVerticalLineIndex / 2)

        var previousLine: VerticalLine? = null
        var nextLine: VerticalLine? = null
        var middleLine: VerticalLine = drawInfo.verticalLines[middleLineIndex]

        while ((maxVerticalLineIndex - minVerticalLineIndex) != 1) {
            if (middleLine.horizontalPosition == touchPosition) {
                drawInfo.cursorPPQN = middleLine.PPQN
                break
            } else if (touchPosition > middleLine.horizontalPosition) {
                minVerticalLineIndex = middleLineIndex
            } else if (touchPosition < middleLine.horizontalPosition) {
                maxVerticalLineIndex = middleLineIndex
            }

            middleLineIndex =
                minVerticalLineIndex + ((maxVerticalLineIndex - minVerticalLineIndex) / 2)
            middleLine = drawInfo.verticalLines[middleLineIndex]
            previousLine = drawInfo.verticalLines[minVerticalLineIndex]
            nextLine = drawInfo.verticalLines[maxVerticalLineIndex]
        }


        var tempSelectedSegment = UISegment(
            drawInfo.selectedSegment!!.startPPQN,
            drawInfo.selectedSegment!!.endPPQN - drawInfo.selectedSegment!!.startPPQN,
            drawInfo.selectedSegment!!.id
        )
        tempSelectedSegment.segmentRect = Rect(
            drawInfo.selectedSegment!!.segmentRect.left,
            drawInfo.selectedSegment!!.segmentRect.top,
            drawInfo.selectedSegment!!.segmentRect.right,
            drawInfo.selectedSegment!!.segmentRect.bottom
        )

        if (previousLine != null && nextLine != null && drawInfo.selectedSegment != null) {
            if (touchPosition - previousLine.horizontalPosition < (touchPosition - nextLine.horizontalPosition).absoluteValue) {
                if (isStartResizing) {
                    tempSelectedSegment.startPPQN = previousLine.PPQN
                } else {
                    tempSelectedSegment.endPPQN = previousLine.PPQN - 1
                }
            } else {
                if (isStartResizing) {
                    tempSelectedSegment.startPPQN = nextLine.PPQN
                } else {
                    tempSelectedSegment.endPPQN = nextLine.PPQN - 1
                }
            }
        }

        if (segmentCollisionCheck(tempSelectedSegment, drawInfo)) {
            drawInfo.selectedSegment!!.startPPQN = tempSelectedSegment.startPPQN
            drawInfo.selectedSegment!!.endPPQN = tempSelectedSegment.endPPQN
            drawInfo.selectedSegment!!.id = tempSelectedSegment.startPPQN
        }

        updateUISegments(drawInfo)
        updateSegmentResizeButtons(drawInfo)
        updateSegmentEditButtons(drawInfo)

    }

    fun restoreSelectedSegment(drawInfo: DrawInfo) {
        drawInfo.selectedSegment!!.startPPQN =
            drawInfo.savedSelectedSegment!!.startPPQN
        drawInfo.selectedSegment!!.endPPQN = drawInfo.savedSelectedSegment!!.endPPQN
        drawInfo.selectedSegment!!.id = drawInfo.savedSelectedSegment!!.id

        drawInfo.selectedSegment!!.segmentRect.left =
            drawInfo.savedSelectedSegment!!.segmentRect.left
        drawInfo.selectedSegment!!.segmentRect.right =
            drawInfo.savedSelectedSegment!!.segmentRect.right
        drawInfo.selectedSegment!!.segmentRect.bottom =
            drawInfo.savedSelectedSegment!!.segmentRect.bottom
        drawInfo.selectedSegment!!.segmentRect.top =
            drawInfo.savedSelectedSegment!!.segmentRect.top

        drawInfo.isSegmentInWrongPosition = false

        updateUISegments(drawInfo)
        updateSegmentResizeButtons(drawInfo)
        updateSegmentEditButtons(drawInfo)
    }

    fun saveSelectedSegment(drawInfo: DrawInfo) {
        drawInfo.savedSelectedSegment = UISegment(
            drawInfo.selectedSegment!!.startPPQN,
            drawInfo.selectedSegment!!.endPPQN - drawInfo.selectedSegment!!.startPPQN,
            drawInfo.selectedSegment!!.id
        )
        drawInfo.savedSelectedSegment!!.segmentRect = Rect(
            drawInfo.selectedSegment!!.segmentRect.left,
            drawInfo.selectedSegment!!.segmentRect.top,
            drawInfo.selectedSegment!!.segmentRect.right,
            drawInfo.selectedSegment!!.segmentRect.bottom
        )
    }

    /**
     * Returns true if segment doesn't collide with other segments
     */
    fun segmentCollisionCheck(
        updatedSegment: UISegment,
        drawInfo: DrawInfo
    ): Boolean {
        if (drawInfo.selectedTrack != null)
            for (segment in drawInfo.selectedTrack!!.segments) {
                if (segment.id != updatedSegment.id) {
                    if (segment.startPPQN >= updatedSegment.startPPQN && segment.endPPQN <= updatedSegment.endPPQN) {

                        return false
                    } else if (segment.startPPQN <= updatedSegment.startPPQN && segment.endPPQN >= updatedSegment.endPPQN) {

                        return false
                    } else if (segment.startPPQN >= updatedSegment.startPPQN && segment.startPPQN <= updatedSegment.endPPQN) {

                        return false
                    } else if (segment.endPPQN >= updatedSegment.startPPQN && segment.endPPQN <= updatedSegment.endPPQN) {

                        return false
                    } else if (updatedSegment.startPPQN > updatedSegment.endPPQN) {

                        return false
                    }
                } else {
                    if (updatedSegment.startPPQN > updatedSegment.endPPQN) {

                        return false
                    }
                }
            }

        return true

/*
        if (drawInfo.selectedTrack != null && drawInfo.selectedSegment != null) {
            if (isSegmentStartResizing) {

                for (segment in drawInfo.selectedTrack!!.segments) {
                    if (segment.startPPQN < drawInfo.selectedSegment!!.startPPQN && segment.endPPQN > targetedLine.PPQN) {
                        return false
                    }
                }

                if (targetedLine.PPQN > drawInfo.selectedSegment!!.endPPQN) {
                    drawInfo.selectedSegment!!.startPPQN =
                        drawInfo.selectedSegment!!.endPPQN - drawInfo.stepPPQN + 1
                    return false
                }

                drawInfo.selectedSegment!!.startPPQN = targetedLine.PPQN


            } else {

                    if (targetedLine.PPQN <= drawInfo.selectedSegment!!.startPPQN) {
                        drawInfo.selectedSegment!!.endPPQN = drawInfo.selectedSegment!!.startPPQN + drawInfo.stepPPQN - 1
                        return false
                    }

                    for (segment in drawInfo.selectedTrack!!.segments) {
                        if (segment.endPPQN > drawInfo.selectedSegment!!.endPPQN && segment.startPPQN < targetedLine.PPQN) {
                            return false
                        }
                    }

                    drawInfo.selectedSegment!!.endPPQN = targetedLine.PPQN - 1
            }
        }
*/


    }

    fun segmentShift(leftShift: Boolean, drawInfo: DrawInfo) {
        if (drawInfo.selectedSegment != null) {

            if (leftShift) {
                if ((drawInfo.selectedSegment!!.startPPQN - drawInfo.stepPPQN >= 0)) {
                    drawInfo.selectedSegment!!.startPPQN -= drawInfo.stepPPQN
                    drawInfo.selectedSegment!!.endPPQN -= drawInfo.stepPPQN
                }
            } else {
                drawInfo.selectedSegment!!.startPPQN += drawInfo.stepPPQN
                drawInfo.selectedSegment!!.endPPQN += drawInfo.stepPPQN
            }
            drawInfo.isSegmentShifting = true

        }


        drawInfo.isSegmentInWrongPosition =
            !segmentCollisionCheck(drawInfo.selectedSegment!!, drawInfo)

        updateUISegments(drawInfo)
        updateSegmentResizeButtons(drawInfo)
        updateSegmentEditButtons(drawInfo)
    }

    fun createUITrack(parentTrack: UITrack?, drawInfo: DrawInfo) {
        if (parentTrack == null) {

            drawInfo.UITracks.add(UITrack())
            drawInfo.UITracks.last().trackName = "000"
            drawInfo.UITracks.last().color = drawInfo.colors.random()
        }

        updateUITracks(drawInfo)
        updateUITrackRects(drawInfo)
        updateUISegments(drawInfo)
        updateCursorPosition(drawInfo)
        updateNewSegmentButton(drawInfo)

    }
}