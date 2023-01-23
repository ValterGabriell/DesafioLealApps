package com.valtergabriel.desafiolealapps.ui

import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import com.valtergabriel.desafiolealapps.R
import com.valtergabriel.desafiolealapps.databinding.ActivityCreateNewExerciseBinding
import com.valtergabriel.desafiolealapps.mock.MockExercise
import com.valtergabriel.desafiolealapps.ui.adapter.ExerciseAdapter

class CreateNewExerciseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateNewExerciseBinding
    private lateinit var adapter: ExerciseAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_new_exercise)

        binding = ActivityCreateNewExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val list = MockExercise().getExercicesForBiceps()
        adapter = ExerciseAdapter(list)
        binding.recyclerAddExercise.adapter = adapter
        binding.recyclerAddExercise.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        adapter.setOnClick = { pos, name ->


        }
    }
}