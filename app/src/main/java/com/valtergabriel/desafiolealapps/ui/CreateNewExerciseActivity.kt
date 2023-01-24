package com.valtergabriel.desafiolealapps.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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

        val trainingName = intent.extras?.get("training_name").toString()


        val list = MockExercise().getExercices()
        adapter = ExerciseAdapter(list)
        binding.recyclerAddExercise.adapter = adapter
        binding.recyclerAddExercise.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        adapter.setOnClick = { pos, name, id, type, description ->
            Intent(this@CreateNewExerciseActivity, VizualizeExercise::class.java).also {
                it.putExtra("exercise_name", name)
                it.putExtra("id", id)
                it.putExtra("description", description)
                it.putExtra("type", type)
                it.putExtra("training_name", trainingName)
                startActivity(it)
            }

        }
    }
}