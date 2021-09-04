package com.example.orfix.music

import com.example.orfix.music.MusicConstants.MAX_VELOCITY
import com.example.orfix.music.MusicConstants.MIN_VELOCITY
import com.example.orfix.music.attribute.Letter
import com.example.orfix.music.attribute.Octave
import com.example.orfix.music.util.Algorithms
import kotlin.math.pow

class Note (
    var octave: Octave = Octave.FIFTH_OCTAVE,
    var letter: Letter = Letter.A,
    var startTick: Int = 0,
    var duration: Int = 0,
    var velocity: Int = 0
) {

    val frequency get() = letter.frequency * 2.0.pow(octave.index.toDouble())

    var index get() = (octave.ordinal + 1) * Letter.values().size + letter.ordinal
        set(value) {
            val octaves = Octave.values()
            val letters = Letter.values()
            val octaveIndex = Algorithms.getValueInBounds(value / letters.size - 1, 0, octaves.size - 1)
            val noteIndex = Algorithms.getValueInBounds(value % letters.size, 0, letters.size - 1)
            octave = octaves[octaveIndex]
            letter = letters[noteIndex]
        }

    constructor(n: Note) : this(n.octave, n.letter, n.startTick, n.duration, n.velocity)

    fun shiftHeight(halfTones: Int) {
        val octaves = Octave.values()
        val letters = Letter.values()
        val min = Note(octaves.first(), letters.first()).index
        val max = Note(octaves.last(), letters.last()).index
        index = Algorithms.shiftValueInBounds(index, halfTones, min, max)
    }

    fun shiftDuration(shiftInTicks: Int) {
        duration = Algorithms.shiftValueInBounds(duration, shiftInTicks, 1, Int.MAX_VALUE)
    }

    fun shiftStartTick(shiftInTicks: Int) {
        startTick = Algorithms.shiftValueInBounds(startTick, shiftInTicks, 0, Int.MAX_VALUE)
    }

    fun shiftVelocity(shift: Int) {
        velocity = Algorithms.shiftValueInBounds(velocity, shift, MIN_VELOCITY, MAX_VELOCITY)
    }

}