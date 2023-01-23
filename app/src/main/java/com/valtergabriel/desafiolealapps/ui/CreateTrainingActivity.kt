package com.valtergabriel.desafiolealapps.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.valtergabriel.desafiolealapps.databinding.ActivityCreateTrainingBinding


class CreateTrainingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateTrainingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateTrainingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAddNewExercise.setOnClickListener {
            Intent(this, CreateNewExerciseActivity::class.java).also {
                startActivity(it)
            }
        }
    }

}