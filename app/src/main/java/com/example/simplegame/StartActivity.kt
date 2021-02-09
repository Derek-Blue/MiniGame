package com.example.simplegame

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import androidx.appcompat.app.AppCompatActivity
import com.example.simplegame.databinding.ActivityStartBinding
import com.example.simplegame.main.GameActivity

class StartActivity : BaseFullScreenActivity() {

    companion object {
        fun newIntent(context: Context): Intent =
            Intent(context, StartActivity::class.java)
    }

    private lateinit var binding: ActivityStartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startImageView.setOnClickListener {
            startActivity(GameActivity.newIntent(this))
            finish()
        }
    }
}