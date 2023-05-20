package com.example.orfix.ui.component.composition.layer.data

class VerticalLine(
    _lineType: LineType,
    _posX: Float,
    _PPQN: Int,
    _timeLineNumber: Int = 0
) {
    var type: LineType = _lineType
    var timeLineNumber: Int = _timeLineNumber
    var horizontalPosition = _posX
    var PPQN = _PPQN
}