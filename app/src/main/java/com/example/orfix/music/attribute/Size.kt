package com.example.orfix.music.attribute

class Size {
    private var beatSize = 0
    private var beatCount = 0

    fun Size(beatSize: Int, beatCount: Int) {
        this.beatSize = beatSize
        this.beatCount = beatCount
    }

    fun getBeatSize(): Int {
        return beatSize
    }

    fun getBeatCount(): Int {
        return beatCount
    }
}