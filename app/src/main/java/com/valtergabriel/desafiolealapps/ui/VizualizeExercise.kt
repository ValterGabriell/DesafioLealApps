package com.valtergabriel.desafiolealapps.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.valtergabriel.desafiolealapps.databinding.ActivityVizualizeExerciseBinding
import com.valtergabriel.desafiolealapps.mock.Exercises
import com.valtergabriel.desafiolealapps.mock.MockExercise
import com.valtergabriel.desafiolealapps.mock.Training
import com.valtergabriel.desafiolealapps.viewmodel.TrainingViewModel
import org.koin.android.ext.android.inject
import java.time.LocalDateTime

class VizualizeExercise : AppCompatActivity() {

    private lateinit var binding: ActivityVizualizeExerciseBinding
    private val trainingViewModel by inject<TrainingViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVizualizeExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val exerciseName = intent.extras?.get("exercise_name").toString()
        val trainingName = intent.extras?.get("training_name").toString()
        val description = intent.extras?.get("description").toString()
        val type = intent.extras?.get("type").toString()
        val id = intent.extras?.get("id").toString()

        binding.txtTitle.text = exerciseName

        binding.btnExercise.setOnClickListener {
            val listExercise = ArrayList<Exercises>()

            val exercise = Exercises(
                id,
                exerciseName,
                description,
                type
            )
            listExercise.add(exercise)
            val training = Training(
                trainingName,
                exercise,
                LocalDateTime.now().toString()
            )
            trainingViewModel.createNewTrainingOnFirebase(training, this)
        }


    }
}