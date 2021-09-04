package com.example.orfix.music.util

object Algorithms {

    fun shiftValueInBounds(current: Int, shift: Int, min: Int, max: Int): Int {
        return getValueInBounds(current + shift, min, max)
    }

    fun getValueInBounds(realValue: Int, min: Int, max: Int): Int {
        return when {
            realValue < min -> {
                min
            }
            realValue > max -> {
                max
            }
            else -> {
                realValue
            }
        }
    }

}