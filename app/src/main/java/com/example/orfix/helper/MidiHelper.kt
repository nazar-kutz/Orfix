package com.example.orfix.helper

import com.example.orfix.music.Party
import com.pdrogfer.mididroid.MidiFile
import com.pdrogfer.mididroid.MidiTrack
import com.pdrogfer.mididroid.event.meta.Tempo
import com.pdrogfer.mididroid.event.meta.TimeSignature
import kotlin.math.log2

object MidiHelper {
    private const val RESOLUTION = 32
    private const val BPM = 130f

    fun convertToMidi(party: Party): MidiFile {
        var tempoTrack = MidiTrack()
        var noteTrack = MidiTrack()

        var timeSignature = TimeSignature()
        timeSignature.setTimeSignature(
            4,
            4,
            TimeSignature.DEFAULT_METER,
            TimeSignature.DEFAULT_DIVISION
        )

        var tempo = Tempo()
        tempo.bpm = BPM
        tempoTrack.insertEvent(timeSignature)
        tempoTrack.insertEvent(tempo)

        for (note in party.notes) {
            val channel = 0
            val pitch = frequencyToMidiNumber(note.frequency)
            val velocity = note.velocity
            val duration = note.duration.toLong()
            val startTick = note.startTick.toLong()
            noteTrack.insertNote(channel, pitch, velocity, startTick, duration)
        }

        var tracks = ArrayList<MidiTrack>()
        tracks.add(tempoTrack)
        tracks.add(noteTrack)
        return MidiFile(RESOLUTION, tracks)
    }

    private fun frequencyToMidiNumber(frequency: Double): Int {
        val midiNumber = 12 * log2(frequency / 440) + 69
        //        using A4 note as reference
        //        12 - notes per octave
        //        440 - frequency of A4 note
        //        69 - midi number of A4 note

        return Math.round(midiNumber).toInt()
    }
}