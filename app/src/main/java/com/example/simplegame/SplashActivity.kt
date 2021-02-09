package com.example.simplegame

import android.os.Bundle
import com.example.simplegame.databinding.ActivitySplashBinding
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : BaseFullScreenActivity() {

    private lateinit var binding: ActivitySplashBinding

    companion object {
        private const val SPLASH_DURATION = 3000L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        MainScope().launch {
            delay(SPLASH_DURATION)
            val intent = StartActivity.newIntent(this@SplashActivity)
            startActivity(intent)
            finish()
        }
    }
}