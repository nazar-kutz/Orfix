package com.example.orfix.music

import com.example.orfix.music.attribute.Mode

object MusicConstants {
	const val MIN_VELOCITY = 0
	const val MAX_VELOCITY = 127

	// number of ticks per beat (quarter note)
	const val DEFAULT_GRID_RESOLUTION = 32;

	const val THIRTY_SECONDS_DURATION = 0.125 * DEFAULT_GRID_RESOLUTION
	const val SIXTEENS_DURATION = 0.25 * DEFAULT_GRID_RESOLUTION
	const val EIGHTS_DURATION = 0.5 * DEFAULT_GRID_RESOLUTION
	const val QUARTER_DURATION = DEFAULT_GRID_RESOLUTION
	const val HALF_NOTE_DURATION = 2 * DEFAULT_GRID_RESOLUTION
	const val WHOLE_NOTE_DURATION = 4 * DEFAULT_GRID_RESOLUTION

	val NATURAL_MAJOR = Mode(arrayOf(2, 2, 1, 2, 2, 2))
	val NATURAL_MINOR = Mode(arrayOf(2, 1, 2, 2, 1, 2))
	val MELODIC_MINOR = Mode(arrayOf(2, 1, 2, 2, 2, 3))
	val HARMONIC_MINOR = Mode(arrayOf(2, 1, 2, 2, 1, 3))
	val ARABIAN = Mode(arrayOf(2, 1, 2, 1, 2, 1, 2))
	val PERSIAN = Mode(arrayOf(1, 3, 1, 1, 2, 3))
	val BYZANTINE = Mode(arrayOf(1, 3, 1, 2, 1, 3))
	val EASTERN = Mode(arrayOf(1, 3, 1, 1, 3, 1))
	val JAPAN = Mode(arrayOf(2, 3, 3))
	val INDIAN = Mode(arrayOf(1, 3, 2, 1))
	val ROMAN = Mode(arrayOf(2, 1, 3, 1, 1, 3))
	val JEWISH = Mode(arrayOf(2, 2, 1, 2, 2, 2))
	val ROMANIAN = Mode(arrayOf(2, 1, 3, 1, 2, 1))
}