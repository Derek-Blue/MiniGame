package com.example.simplegame

import android.app.Application

class GameApplication : Application() {

    companion object {
        lateinit var instance: GameApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}