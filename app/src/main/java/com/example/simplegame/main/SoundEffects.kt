package com.example.simplegame.main

import android.media.AudioAttributes
import android.media.SoundPool
import com.example.simplegame.GameApplication
import com.example.simplegame.R

object SoundEffects {

    private const val MAX = 2

    private val context =
        GameApplication.instance
    private val soundPool by lazy {
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .build()
        SoundPool.Builder().setAudioAttributes(audioAttributes).setMaxStreams(MAX).build()
    }

    private val collectSound = soundPool.load(
        context,
        R.raw.collect, 1)
    private val loseSound = soundPool.load(
        context,
        R.raw.lose, 1)

    fun collectSound() {
        soundPool.play(
            collectSound, 1f, 1f ,1,0, 1f)
    }

    fun loseSound() {
        soundPool.play(
            loseSound, 1f, 1f ,1,0, 1f)
    }
}