package com.valtergabriel.desafiolealapps.ui

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.valtergabriel.desafiolealapps.databinding.ActivityMainBinding
import com.valtergabriel.desafiolealapps.mock.MockTrain

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnSignUp.setOnClickListener {
            Intent(this, FeedActivity::class.java).also {
                startActivity(it)
            }
        }

    }


}