package com.example.orfix

import com.example.orfix.music.MusicConstants.MAX_VELOCITY
import com.example.orfix.music.MusicConstants.QUARTER_DURATION
import com.example.orfix.music.Note
import com.example.orfix.music.Party
import com.example.orfix.music.attribute.Interval
import com.example.orfix.music.attribute.Letter
import com.example.orfix.music.attribute.Octave
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun saveToMidiTest() {
        val party = createParty()
        // TODO (save party to midi here)
    }

    fun createParty(): Party {
        val n1 = Note()
        n1.startTick = 0
        n1.duration = QUARTER_DURATION
        n1.octave = Octave.FIRST_OCTAVE
        n1.letter = Letter.D
        n1.velocity = MAX_VELOCITY

        val n2 = Note(n1)
        n2.shiftStartTick(QUARTER_DURATION)
        n2.shiftHeight(Interval.MAJOR_THIRD.halfTones)

        val n3 = Note(n2)
        n3.shiftStartTick(QUARTER_DURATION)
        n3.shiftHeight(Interval.MINOR_THIRD.halfTones)

        val n4 = Note(n3)
        n4.shiftStartTick(QUARTER_DURATION)
        n4.shiftHeight(Interval.MINOR_THIRD.halfTones)

        val party = Party("Test Party")
        party.add(n1)
        party.add(n2)
        party.add(n3)
        return party
    }
}