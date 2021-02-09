package com.example.simplegame.main

import kotlin.math.roundToInt

class SettingData(
    val screenWidth: Int,
    val screenHeight: Int,
    val frameHeight: Int,
    val playerHeight: Int,
    val foodHeight: Int,
    val diamondHeight: Int,
    val monster1Height: Int,
    val monster2Height: Int,
) {

    companion object {
        private const val PLAYER_RATE_BASE = 59f
        private const val FOOD_RATE_BASE = 59f
        private const val DIAMOND_RATE_BASE = 35f
        private const val MONSTER_ONE_RATE_BASE = 44f
        private const val MONSTER_TWO_RATE_BASE = 39f
    }

    var playerSpeed: Int = 0
    var foodSpeed: Int = 0
    var diamondSpeed: Int = 0
    var monster1Speed: Int = 0
    var monster2Speed: Int = 0

    init {
        if (screenHeight == 0 || screenWidth == 0) {
            throw IllegalStateException("螢幕大小輸入錯誤")
        }
        this.playerSpeed = (screenWidth / PLAYER_RATE_BASE).roundToInt()
        this.foodSpeed = (screenWidth / FOOD_RATE_BASE).roundToInt()
        this.diamondSpeed = (screenWidth / DIAMOND_RATE_BASE).roundToInt()
        this.monster1Speed = (screenWidth / MONSTER_ONE_RATE_BASE).roundToInt()
        this.monster2Speed = (screenWidth / MONSTER_TWO_RATE_BASE).roundToInt()
    }

    fun playerDisplayArea() : Int{
        return frameHeight - playerHeight
    }

    fun foodDisplayArea() : Int{
        return frameHeight - foodHeight
    }

    fun diamondDisplayArea() : Int{
        return frameHeight - diamondHeight
    }

    fun monster1DisplayArea() : Int{
        return frameHeight - monster1Height
    }

    fun monster2DisplayArea() : Int{
        return frameHeight - monster2Height
    }
}