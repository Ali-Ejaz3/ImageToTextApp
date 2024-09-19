package com.example.imagetotext

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class OpeningScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_opening_screen)

        supportActionBar?.hide()

        Handler(Looper.getMainLooper()).postDelayed({
        val i = Intent(this@OpeningScreen,MainActivity::class.java)
            startActivity(i)
            finish()
        },3000)
    }
}