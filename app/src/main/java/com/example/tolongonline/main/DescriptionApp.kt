package com.example.tolongonline.main

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.tolongonline.databinding.ActivityDescriptionAppBinding

class DescriptionApp : AppCompatActivity() {

    private lateinit var binding: ActivityDescriptionAppBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDescriptionAppBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                )
        window.statusBarColor = Color.TRANSPARENT

        supportActionBar?.hide()

        binding.button.setOnClickListener {
            val intent = Intent(this, LoginHp::class.java)
            startActivity(intent)

        }
    }
}
