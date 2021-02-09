package com.example.simplegame.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.MotionEvent
import androidx.core.content.ContextCompat
import androidx.core.view.doOnLayout
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.simplegame.BaseFullScreenActivity
import com.example.simplegame.R
import com.example.simplegame.ResultActivity
import com.example.simplegame.databinding.ActivityMainBinding

class GameActivity : BaseFullScreenActivity() {

    companion object {
        private const val SCORE = "Score : "

        fun newIntent(context: Context): Intent{
            return Intent(context, GameActivity::class.java)
        }
    }

    private lateinit var binding: ActivityMainBinding

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(GameViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observer()

        binding.gameFrameLayout.doOnLayout {
            val outMetrics = DisplayMetrics()
            display?.getRealMetrics(outMetrics)

            val frameHeight = binding.gameFrameLayout.height
            val playerHeight = binding.player.height

            val setting = SettingData(
                screenWidth = outMetrics.widthPixels,
                screenHeight = outMetrics.heightPixels,
                frameHeight = frameHeight,
                playerHeight = playerHeight,
                foodHeight = binding.food.height,
                diamondHeight = binding.diamaond.height,
                monster1Height = binding.monster.height,
                monster2Height = binding.monster2.height
            )

            viewModel.initGame(setting)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        viewModel.operationGame(event)
        return false
    }

    private fun observer() {
        viewModel.score.observe(this, Observer { score ->
            val scoreTextView = "$SCORE$score"
            binding.scoreTextView.text = scoreTextView
        })

        viewModel.positionData.observe(this, Observer {
            binding.player.y = it.playerY.toFloat()

            binding.food.x = it.foodX.toFloat()
            binding.food.y = it.foodY.toFloat()

            binding.diamaond.x = it.diamondX.toFloat()
            binding.diamaond.y = it.diamondY.toFloat()

            binding.monster.x = it.monster1X.toFloat()
            binding.monster.y = it.monster1Y.toFloat()

            binding.monster2.x = it.monster2X.toFloat()
            binding.monster2.y = it.monster2Y.toFloat()
        })

        viewModel.playerDirection.observe(this, Observer { type ->
            when (type ?: return@Observer) {
                Direction.RISE -> {
                    binding.player.background = ContextCompat.getDrawable(this, R.drawable.cat2)
                }
                Direction.FALL -> {
                    binding.player.background = ContextCompat.getDrawable(this, R.drawable.cat1)
                }
            }
        })

        viewModel.gameStatus.observe(this, Observer { status ->
            when (status) {
                GameStatus.Pause -> {
                    binding.tapToStartTextView.isVisible = true
                }
                GameStatus.Start -> {
                    binding.tapToStartTextView.isVisible = false
                }
                is GameStatus.GameOver -> {
                    val intent = ResultActivity.newIntent(this, status.score)
                    startActivity(intent)
                    finish()
                }
            }
        })
    }
}