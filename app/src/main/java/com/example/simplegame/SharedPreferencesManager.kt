package com.example.simplegame

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesManager private constructor() {

    companion object {
        private const val FILE_NAME = "shared_preferences_file_name"

        /**
         * SharedPreferenceManager實體
         */
        private var instance: SharedPreferencesManager? = null

        fun getInstance(): SharedPreferencesManager {
            if (instance == null) {
                instance = SharedPreferencesManager()
            }
            return instance!!
        }

        private const val HIGHEST_SCORE = "highest_score"
        private const val GAMES_PLAYED = "games_played"
    }

    private var sharedPreferences: SharedPreferences

    init {
        val application = GameApplication.instance
        sharedPreferences = application.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
    }

    /**
     * 最高分數
     */
    var highestScore: Int
        get() {
            return sharedPreferences.getInt(HIGHEST_SCORE, 0)
        }
        set(value) {
            sharedPreferences.edit().putInt(HIGHEST_SCORE, value).apply()
        }

    /**
     * 遊玩次數
     */
    var gamesPlayed: Int
        get() {
            return sharedPreferences.getInt(GAMES_PLAYED, 0)
        }
        set(value) {
            sharedPreferences.edit().putInt(GAMES_PLAYED, value).apply()
        }
}