package com.example.simplegame.main

sealed class GameStatus {
    object Pause: GameStatus()
    object Start: GameStatus()
    class GameOver(val score: Int): GameStatus()
}