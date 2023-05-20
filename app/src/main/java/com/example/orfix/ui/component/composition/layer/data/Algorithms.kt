package com.example.orfix.ui.component.composition.layer.data

import android.util.Log
import kotlin.math.absoluteValue

object Algorithms {

    fun findXBetweenTwoNumbers(
        x: Int,
        firstArg: Int,
        SecondArg: Int,
        distanceBetweenArgs: Int
    ): Int {
        var ratio: Int =
            (firstArg - SecondArg / distanceBetweenArgs)
        var result =
            firstArg - (SecondArg * ratio)
        return result
    }

    /**
     *Returns the closest line to touch
     */
    fun findClosesLine(
        _previousLine: VerticalLine,
        _nextLine: VerticalLine,
        touchPosition: Float,
        drawInfo: DrawInfo
    ): VerticalLine? {
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

            _previousLine.PPQN = previousLine.PPQN
            _previousLine.horizontalPosition = previousLine.horizontalPosition
            _previousLine.timeLineNumber = previousLine.timeLineNumber
            _previousLine.type = previousLine.type

            _nextLine.PPQN = nextLine.PPQN
            _nextLine.horizontalPosition = nextLine.horizontalPosition
            _nextLine.timeLineNumber = nextLine.timeLineNumber
            _nextLine.type = nextLine.type
        }

        if (previousLine != null && nextLine != null) {
            if (touchPosition - previousLine.horizontalPosition < (touchPosition - nextLine.horizontalPosition).absoluteValue) {
                return previousLine
            } else {
                return nextLine
            }
        }
        return null
    }


    /**
     * Changes _previousLine and _nextLine arguments to near lines to touch
     */
    fun findNearLinesToTouch(
        _previousLine: VerticalLine?,
        _nextLine: VerticalLine?,
        touchPosition: Float,
        drawInfo: DrawInfo
    ) {

        for(index in 0..drawInfo.verticalLines.size){

        }

    }
}