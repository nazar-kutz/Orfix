package com.example.orfix.music

import com.example.orfix.music.attribute.Mode

object MusicConstants {
    const val MIN_VELOCITY = 0
    const val MAX_VELOCITY = 127

    // number of ticks per beat (quarter note)
    const val DEFAULT_GRID_RESOLUTION = 32;

    const val THIRTY_SECONDS = 0.125 * DEFAULT_GRID_RESOLUTION
    const val SIXTEENS = 0.25 * DEFAULT_GRID_RESOLUTION
    const val EIGHTS = 0.5 * DEFAULT_GRID_RESOLUTION
    const val QUARTER = DEFAULT_GRID_RESOLUTION
    const val HALF_NOTE = 2 * DEFAULT_GRID_RESOLUTION
    const val WHOLE_NOTE = 4 * DEFAULT_GRID_RESOLUTION

    val NATURAL_MAJOR = Mode(arrayOf(2, 2, 1, 2, 2, 2))
    var NATURAL_MINOR = Mode(arrayOf(2, 1, 2, 2, 1, 2))
    var MELODIC_MINOR = Mode(arrayOf(2, 1, 2, 2, 2, 3))
    var HARMONIC_MINOR = Mode(arrayOf(2, 1, 2, 2, 1, 3))
    var ARABIAN = Mode(arrayOf(2, 1, 2, 1, 2, 1, 2))
    var PERSIAN = Mode(arrayOf(1, 3, 1, 1, 2, 3))
    var BYZANTINE = Mode(arrayOf(1, 3, 1, 2, 1, 3))
    var EASTERN = Mode(arrayOf(1, 3, 1, 1, 3, 1))
    var JAPAN = Mode(arrayOf(2, 3, 3))
    var INDIAN = Mode(arrayOf(1, 3, 2, 1))
    var ROMAN = Mode(arrayOf(2, 1, 3, 1, 1, 3))
    var JEWISH = Mode(arrayOf(2, 2, 1, 2, 2, 2))
    var ROMANIAN = Mode(arrayOf(2, 1, 3, 1, 2, 1))
}