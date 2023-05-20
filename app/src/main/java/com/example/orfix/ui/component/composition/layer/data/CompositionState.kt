package com.example.orfix.ui.component.composition.layer.data

class CompositionState {

    //var states = ArrayList<Id>()
    var states = ArrayList<Int>()

    /*enum class Id(val state: Int) {
        IS_BACKGROUND_PRESSED(1),
        IS_BACKGROUND_SHIFTING(2),
        IS_BACKGROUND_SCALING(3),
        IS_TIMELINE_PRESSED(4),
        IS_TIMELINE_SCROLLING(5),
        IS_TRACK_HEADER_PRESSED(6),
        IS_SEGMENT_RESIZING_LEFT(7),
        IS_SEGMENT_RESIZING_RIGHT(8),
        IS_SEGMENT_SHIFTING(9)
    }*/

    val IS_BACKGROUND_ON_DOWN = 0
    val IS_SEGMENT_ON_DOWN = 1
    val IS_BUTTONS_ON_DOWN = 2
    val IS_TIMELINE_ON_DOWN = 3
    val IS_TRACKHEADER_ON_DOWN = 4
    val IS_BACKGROUND_SEGMENT_DOWN = 5

    fun isStateActive(id: Int): Boolean {
        for (state in states) {
            if (state == id) {
                return true
            }
        }
        return false
    }

    fun isNotActive(id: Int):Boolean{
        return !isStateActive(id)
    }

    fun addState(id: Int): Boolean {
        if (states.isEmpty()) {
            states.add(id)
            return true
        } else {
            if (isStateActive(id)) {
                return false
            } else {
                states.add(id)
                return true
            }
        }
    }

    fun removeState(id: Int): Boolean {
        if (isStateActive(id)) {
            states.remove(id)
            return true
        } else {
            return false
        }
    }

    /*fun isActive(_id: Id): Boolean {
        if (states.isNotEmpty()) {
            for (state in states) {
                if (state == _id) {
                    return true
                }
            }
        }
        return false
    }

    fun isNotActive(_id: Id): Boolean {
        return !isActive(_id)
    }

    fun addState(_id: Id): Boolean {
        if (states.isEmpty()) {
            states.add(_id)
            return true
        } else {
            if (isActive(_id)) {
                return false
            }
            states.add(_id)
            return true
        }
    }

    fun removeState(_id: Id): Boolean {
        return if (isActive(_id)) {
            states.remove(_id)
            true
        } else {
            false
        }
    }*/
}
