package com.example.simplegame.main

import android.view.MotionEvent
import android.view.MotionEvent.ACTION_DOWN
import android.view.MotionEvent.ACTION_UP
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.math.floor
import kotlin.math.roundToInt

class GameViewModel : ViewModel() {

    private lateinit var setting: SettingData

    /**
     * 玩家方向
     */
    private val _playerDirection: MutableLiveData<Direction> = MutableLiveData(Direction.FALL)
    private val currentDirection get() = _playerDirection.value!!
    val playerDirection: LiveData<Direction> = _playerDirection

    /**
     * 分數
     */
    private val _score: MutableLiveData<Int> = MutableLiveData(0)
    private val currentScore get() = _score.value!!
    val score: LiveData<Int> = _score

    /**
     * 位置物件
     */
    private val _positionData: MutableLiveData<PositionData> = MutableLiveData(PositionData())
    private val currentPosition get() = _positionData.value!!
    val positionData: LiveData<PositionData> = _positionData

    /**
     * 遊戲狀態
     */
    private val _gameStatus: MutableLiveData<GameStatus> = MutableLiveData(GameStatus.Pause)
    private val currentGameStatus get() = _gameStatus.value!!
    val gameStatus: LiveData<GameStatus> = _gameStatus

    /**
     * 遊戲進程控制
     */
    private var operationJob: Job? = null

    /**
     * 遊戲初始化
     */
    fun initGame(settingData: SettingData) {
        setting = settingData
        _positionData.value = currentPosition.copy(playerY = settingData.frameHeight/2)
    }

    /**
     * 玩家方向
     */
    fun operationGame(event: MotionEvent?) {
        startGameIfPause()
        when (event?.action) {
            ACTION_DOWN -> {
                _playerDirection.value = Direction.RISE
            }
            ACTION_UP -> {
                _playerDirection.value = Direction.FALL
            }
            else -> {
            }
        }
    }

    /**
     * 開始遊戲
     */
    private fun startGameIfPause() {
        if (currentGameStatus == GameStatus.Pause) {
            _gameStatus.value = GameStatus.Start

            operationJob = viewModelScope.launch {
                while (isActive) {
                    delay(20)
                    calculatePosition(currentPosition)
                }
            }
        }
    }

    /**
     * 計算各部位位置
     */
    private fun calculatePosition(currentPosition: PositionData) {

        val resultPosition = checkHit(currentPosition)

        var foodX = resultPosition.foodX - setting.foodSpeed
        var foodY = resultPosition.foodY
        if (foodX < 0) {
            foodX = setting.screenWidth + 20
            foodY = floor(Math.random() * (setting.foodDisplayArea())).roundToInt()
        }

        var diamondX = resultPosition.diamondX - setting.diamondSpeed
        var diamondY = resultPosition.diamondY
        if (diamondX < 0) {
            diamondX = setting.screenWidth + 5000
            diamondY = floor(Math.random() * (setting.diamondDisplayArea())).roundToInt()
        }

        var monster1X = resultPosition.monster1X - setting.monster1Speed
        var monster1Y = resultPosition.monster1Y
        if (monster1X < 0) {
            monster1X = setting.screenWidth + 10
            monster1Y = floor(Math.random() * (setting.monster1DisplayArea())).roundToInt()
        }

        var monster2X = resultPosition.monster2X - setting.monster2Speed
        var monster2Y = resultPosition.monster2Y
        if (monster2X < 0) {
            monster2X = setting.screenWidth + 4000
            monster2Y = floor(Math.random() * (setting.monster2DisplayArea())).roundToInt()
        }

        var playerY = if (currentDirection == Direction.RISE) {
            resultPosition.playerY - setting.playerSpeed
        } else {
            resultPosition.playerY + setting.playerSpeed
        }
        if (playerY < 0) {
            playerY = 0
        }
        if (playerY > setting.playerDisplayArea()) {
            playerY = setting.playerDisplayArea()
        }

        val newData = PositionData(
            playerY,
            foodX,
            foodY,
            diamondX,
            diamondY,
            monster1X,
            monster1Y,
            monster2X,
            monster2Y
        )
        _positionData.value = newData
    }

    /**
     * 檢察碰撞事件
     */
    private fun checkHit(currentPosition: PositionData): PositionData {
        val score = currentScore

        val foodCenterX = currentPosition.foodX + setting.foodHeight / 2
        val foodCenterY = currentPosition.foodY + setting.foodHeight / 2

        if (foodCenterX > 0 && foodCenterX <= setting.playerHeight
            && foodCenterY > currentPosition.playerY && foodCenterY <= currentPosition.playerY + setting.playerHeight
        ) {
            _score.value = score + 1
            SoundEffects.collectSound()
            return currentPosition.copy(foodX = -10)
        }

        val diamondCenterX = currentPosition.diamondX + setting.diamondHeight / 2
        val diamondCenterY = currentPosition.diamondY + setting.diamondHeight / 2

        if (diamondCenterX > 0 && diamondCenterX <= setting.playerHeight
            && diamondCenterY > currentPosition.playerY && diamondCenterY <= currentPosition.playerY + setting.playerHeight
        ) {
            _score.value = score + 3
            SoundEffects.collectSound()
            return currentPosition.copy(diamondX = -10)
        }

        val monster1CenterX = currentPosition.monster1X + setting.monster1Height / 2
        val monster1CenterY = currentPosition.monster1Y + setting.monster1Height / 2

        if (monster1CenterX > 0 && monster1CenterX <= setting.playerHeight
            && monster1CenterY > currentPosition.playerY && monster1CenterY <= currentPosition.playerY + setting.playerHeight
        ) {
            gameOver(score)
        }

        val monster2CenterX = currentPosition.monster2X + setting.monster2Height / 2
        val monster2CenterY = currentPosition.monster2Y + setting.monster2Height / 2

        if (monster2CenterX > 0 && monster2CenterX <= setting.playerHeight
            && monster2CenterY > currentPosition.playerY && monster2CenterY <= currentPosition.playerY + setting.playerHeight
        ) {
            gameOver(score)
        }

        return currentPosition
    }

    /**
     * 結束遊戲
     */
    private fun gameOver(score: Int) {
        operationJob?.cancel()
        operationJob = null
        SoundEffects.loseSound()
        _gameStatus.value = GameStatus.GameOver(score)
    }

    override fun onCleared() {
        operationJob?.cancel()
        operationJob = null
        super.onCleared()
    }
}