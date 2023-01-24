package com.valtergabriel.desafiolealapps.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.valtergabriel.desafiolealapps.R
import com.valtergabriel.desafiolealapps.databinding.ActivityCreateNewExerciseBinding
import com.valtergabriel.desafiolealapps.databinding.ActivityVizualizeExerciseBinding

class VizualizeExercise : AppCompatActivity() {

    private lateinit var binding: ActivityVizualizeExerciseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVizualizeExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val extras = intent.extras?.get("exercise_name").toString()
        binding.txtTitle.text = extras


    }
}