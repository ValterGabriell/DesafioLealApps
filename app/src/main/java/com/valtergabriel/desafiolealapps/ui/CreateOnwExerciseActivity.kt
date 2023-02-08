package com.valtergabriel.desafiolealapps.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.valtergabriel.desafiolealapps.R
import com.valtergabriel.desafiolealapps.databinding.ActivityCreateExerciseBinding
import com.valtergabriel.desafiolealapps.dto.Exercises
import com.valtergabriel.desafiolealapps.util.Constants.STATIC_TITLE
import com.valtergabriel.desafiolealapps.util.Constants.WAS_CREATED
import com.valtergabriel.desafiolealapps.util.Validation
import com.valtergabriel.desafiolealapps.viewmodel.TrainingViewModel
import org.koin.android.ext.android.inject

class CreateOnwExerciseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateExerciseBinding
    private val trainingViewModel by inject<TrainingViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_exercise)

        binding = ActivityCreateExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val staticTitle = intent.extras?.get(STATIC_TITLE).toString()

        binding.btnAddExercise.setOnClickListener {
            val title = binding.nameExerciseditText.text.toString()
            val duration = binding.editTextExerciseDuration.text.toString()
            val description = binding.obsExerciseEditText.text.toString()

            if (Validation.isFilledField(title)
                && Validation.isFilledField(duration)
                && Validation.isFilledField(description)
            ) {
                binding.progressBar3.visibility = View.VISIBLE
                binding.btnAddExercise.visibility = View.GONE
                val exercises = Exercises(
                    System.currentTimeMillis(),
                    title,
                    description,
                    WAS_CREATED,
                    duration
                )

                trainingViewModel.createNewExercise(
                    exercises,
                    staticTitle,
                    this
                )
            }else{
                Toast.makeText(this, "Preencha tudo corretamente", Toast.LENGTH_SHORT).show()
            }
        }


    }
}