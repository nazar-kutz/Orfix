package com.example.orfix.music.util

import com.example.orfix.music.Note
import java.util.*


enum class NotesComparator: Comparator<Note> {
    HEIGHT,
    START_TICK,
    DURATION;

    override fun compare(p0: Note, p1: Note): Int {
        return when {
            this == HEIGHT -> {
                p0.frequency.compareTo(p1.frequency)
            }
            this == START_TICK -> {
                p0.startTick.compareTo(p1.startTick)
            }
            else -> {
                p0.duration.compareTo(p1.duration)
            }
        }
    }

}