package com.example.orfix.music

import android.icu.text.LocaleDisplayNames.UiListItem.getComparator
import com.example.orfix.music.util.NotesComparator
import java.util.*


class Party(
    var name: String,
    var notes: MutableList<Note> = mutableListOf<Note>()
) {

    fun add(note: Note) {
        notes.add(note)
    }

    fun remove(note: Note) {
        notes.remove(note)
    }

    fun addAll(source: Party) {
        notes.addAll(source.notes)
    }

    fun removeAll() {
        notes.clear()
    }

    // TODO improve efficient
    fun getDuration(): Int {
        var total: Int = 0
        for (note in notes) {
            val duration = note.startTick + note.duration
            if (total < duration) {
                total = duration
            }
        }
        return total
    }

    fun sort(notesComparator: NotesComparator) {
        notes.sortWith(notesComparator)
    }
}