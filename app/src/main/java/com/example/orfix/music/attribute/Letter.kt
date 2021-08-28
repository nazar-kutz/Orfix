package com.example.orfix.music.attribute


enum class Letter(val frequency: Double) {
    C   (16.352),
    Ces  (17.324),
    D   (18.354),
    Des  (19.445),
    E   (20.602),
    F   (21.827),
    Fes  (23.125),
    G   (24.500),
    Ges  (25.957),
    A   (27.500),
    Aes  (29.135),
    H   (30.868);

    open fun noteBefore(): Letter? {
        val notes: Array<Letter> = values()
        var currentIndex = notes.size - 1
        for (i in currentIndex downTo 0) {
            if (this === notes[i]) {
                currentIndex = i
                break
            }
        }
        currentIndex--
        if (currentIndex < 0) {
            currentIndex = notes.size - 1
        }
        return notes[currentIndex]
    }

    open fun noteAfter(): Letter? {
        val notes: Array<Letter> = values()
        var currentIndex = 0
        for (i in notes.indices) {
            if (this === notes[i]) {
                currentIndex = i
                break
            }
        }
        currentIndex++
        if (currentIndex >= notes.size) {
            currentIndex = 0
        }
        return notes[currentIndex]
    }

}