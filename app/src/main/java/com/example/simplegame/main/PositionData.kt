package com.example.simplegame.main

data class PositionData(
    //玩家位置
    val playerY: Int = 0,
    //食物(愛心)位置
    val foodX: Int = -90,
    val foodY: Int = -90,
    //鑽石位置
    val diamondX: Int = -90,
    val diamondY: Int = -90,
    //怪物一位置
    val monster1X: Int = -90,
    val monster1Y: Int = -90,
    //怪物二位置
    val monster2X: Int = -90,
    val monster2Y: Int = -90,
)