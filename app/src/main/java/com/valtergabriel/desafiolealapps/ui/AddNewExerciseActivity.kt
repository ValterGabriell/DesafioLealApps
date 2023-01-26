package com.valtergabriel.desafiolealapps.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.valtergabriel.desafiolealapps.R
import com.valtergabriel.desafiolealapps.databinding.ActivityCreateNewExerciseBinding
import com.valtergabriel.desafiolealapps.mock.MockExercise
import com.valtergabriel.desafiolealapps.ui.adapter.ExerciseMockAdapter

class AddNewExerciseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateNewExerciseBinding
    private lateinit var adapter: ExerciseMockAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_new_exercise)

        binding = ActivityCreateNewExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val trainingName = intent.extras?.get("training_name").toString()
        val trainingDesc = intent.extras?.get("training_desc").toString()
        val staticTitle = intent.extras?.get("static_title").toString()



        val list = MockExercise().getExercices()
        adapter = ExerciseMockAdapter(list)
        binding.recyclerAddExercise.adapter = adapter
        binding.recyclerAddExercise.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        adapter.setOnClick = { name, title, duration, type, description ->
            Intent(this@AddNewExerciseActivity, VizualizeExercise::class.java).also {
                it.putExtra("exercise_name", title)
                it.putExtra("id", name)
                it.putExtra("duration", duration)
                it.putExtra("description", description)
                it.putExtra("static_title", staticTitle)
                it.putExtra("type", type)
                it.putExtra("training_name", trainingName)
                it.putExtra("training_desc", trainingDesc)
                it.putExtra("training_id", System.currentTimeMillis())
                startActivity(it)
            }

        }
    }
}