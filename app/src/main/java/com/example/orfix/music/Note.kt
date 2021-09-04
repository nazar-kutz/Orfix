package com.example.orfix.music

import com.example.orfix.music.MusicConstants.MAX_VELOCITY
import com.example.orfix.music.MusicConstants.MIN_VELOCITY
import com.example.orfix.music.attribute.Letter
import com.example.orfix.music.attribute.Octave

class Note (
    var octave: Octave = Octave.FIFTH_OCTAVE,
    var letter: Letter = Letter.A,
    var startTick: Int = 0,
    var duration: Int = 0,
    var velocity: Int = 0
) {

    constructor(n: Note) : this(n.octave, n.letter, n.startTick, n.duration, n.velocity)

    fun getAbsoluteHeight(): Double {
        return letter.frequency * Math.pow(2.0, octave.index.toDouble())
    }

    fun getHeightIndex(): Int {
        return (octave.ordinal + 1) * Letter.values().size + (letter.ordinal + 1)
    }

    // TODO improve, search more efficient algorithm
    fun moveHeight(halfTones: Int) {
        val octaves: Array<Octave> = Octave.values()
        val notes: Array<Letter> = Letter.values()

        val newNoteIndex = getHeightIndex() + halfTones - 1

        val octaveIndex = newNoteIndex / notes.size - 1
        val noteIndex = newNoteIndex % notes.size

        octave = if (octaveIndex > 0) {
            if (octaveIndex < octaves.size) {
                octaves[octaveIndex]
            } else {
                octaves[octaves.size - 1]
            }
        } else {
            octaves[0]
        }

        letter = if (noteIndex > 0) {
            if (noteIndex < notes.size) {
                notes[noteIndex]
            } else {
                notes[notes.size - 1]
            }
        } else {
            notes[0]
        }
    }

    fun moveDuration(interval: Int) {
        val result = duration + interval
        duration = if (interval < 0) {
            if (result > 1) {
                result
            } else {
                1
            }
        } else {
            result
        }
    }

    fun moveStartTick(interval: Int) {
        val result: Int = startTick + interval
        startTick = if (interval < 0) {
            if (result >= 0) {
                result
            } else {
                0
            }
        } else {
            result
        }
    }

    fun moveVelocity(value: Int) {
        val result: Int = velocity + value
        velocity = when {
            result < MIN_VELOCITY -> {
                MIN_VELOCITY
            }
            result > MAX_VELOCITY -> {
                MAX_VELOCITY
            }
            else -> {
                result
            }
        }
    }

}