package com.valtergabriel.desafiolealapps.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.valtergabriel.desafiolealapps.databinding.ActivityVizualizeExerciseBinding
import com.valtergabriel.desafiolealapps.dto.Exercises
import com.valtergabriel.desafiolealapps.dto.Training
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
        val id = intent.extras?.get("id") as Long
        val trainingName = intent.extras?.get("training_name").toString()
        val staticTitle = intent.extras?.get("static_title").toString()
        val trainingDesc = intent.extras?.get("training_desc").toString()
        val obs = intent.extras?.get("exe_desc").toString()
        val trainingId = intent.extras?.get("training_id") as Long
        val duration = intent.extras?.get("duration").toString()
        val type = intent.extras?.get("type").toString()

        binding.editText.setText(duration)
        binding.txtTitle.text = exerciseName


        binding.btnExercise.setOnClickListener {
            val listExercise = ArrayList<Exercises>()
            val time = binding.editText.text.toString()

            val exercise = Exercises(
                id,
                exerciseName,
                obs,
                type,
                time
            )
            listExercise.add(exercise)


            val training = Training(
                trainingId,
                trainingName,
                staticTitle,
                exercise,
                LocalDateTime.now().toString(),
                trainingDesc
            )
            trainingViewModel.createNewTrainingOnFirebase(training, this)
        }


    }
}