package com.example.tolongonline.main

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tolongonline.R
import com.example.tolongonline.databinding.ActivityLoginHpBinding
import com.example.tolongonline.ui.home.HomeFragment

class LoginHp : AppCompatActivity() {

    private lateinit var binding: ActivityLoginHpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginHpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                )
        window.statusBarColor = Color.TRANSPARENT

        supportActionBar?.hide()

        binding.register.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


    }
}