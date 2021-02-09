package com.example.simplegame

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import com.example.simplegame.databinding.ActivityResultBinding

class ResultActivity : BaseFullScreenActivity() {

    private lateinit var binding: ActivityResultBinding

    companion object {
        private const val SCORE_KEY = "score_key"
        fun newIntent(context: Context, score: Int): Intent {
            return Intent(context, ResultActivity::class.java).apply {
                this.putExtra(SCORE_KEY, score)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.replay.setOnClickListener {
            val intent = StartActivity.newIntent(this)
            startActivity(intent)
            finish()
        }

        //此次分數
        val score = intent.getIntExtra(SCORE_KEY, 0)
        binding.scoreTextView.text = score.toString()

        //最高分顯示/儲存
        val highestScore = SharedPreferencesManager.getInstance().highestScore
        if (highestScore < score) {
            val highestText = "Highest Score : $score"
            binding.highestScoreTextView.text = highestText
            SharedPreferencesManager.getInstance().highestScore = score
        } else {
            val highestText = "Highest Score : $highestScore"
            binding.highestScoreTextView.text = highestText
        }

        //紀錄遊戲次數
        val cacheGamesPlayed = SharedPreferencesManager.getInstance().gamesPlayed
        val gamesPlayed = cacheGamesPlayed + 1
        val gamesPlayedText = "Games Played : $gamesPlayed"
        binding.gamesPlayedTextView.text = gamesPlayedText
        SharedPreferencesManager.getInstance().gamesPlayed = gamesPlayed
    }

    override fun dispatchKeyEvent(event: KeyEvent?): Boolean {

        if (event?.action == KeyEvent.ACTION_DOWN) {
            when (event.action) {
                KeyEvent.KEYCODE_BACK -> {
                    return true
                }
            }
        }
        return super.dispatchKeyEvent(event)
    }
}