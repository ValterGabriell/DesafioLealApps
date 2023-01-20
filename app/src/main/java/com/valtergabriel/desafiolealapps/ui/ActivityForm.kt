package com.valtergabriel.desafiolealapps.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.valtergabriel.desafiolealapps.databinding.ActivityFormBinding

class ActivityForm : AppCompatActivity() {

    private lateinit var binding: ActivityFormBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormBinding.inflate(layoutInflater)
        setContentView(binding.root)



    }
}