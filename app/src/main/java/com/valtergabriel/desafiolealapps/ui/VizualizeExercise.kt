package com.valtergabriel.desafiolealapps.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.valtergabriel.desafiolealapps.databinding.ActivityVizualizeExerciseBinding
import com.valtergabriel.desafiolealapps.dto.DataFromRecycler
import com.valtergabriel.desafiolealapps.dto.Exercises
import com.valtergabriel.desafiolealapps.dto.Training
import com.valtergabriel.desafiolealapps.util.Constants
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

        val data = intent.extras?.getSerializable(Constants.DATA) as DataFromRecycler


        /**
         * Setando os dados de acordo com o que foi recebido
         */
        binding.editTextDuration.setText(data.duration)
        binding.txtTitle.text = data.title
        binding.txtDescView.text = data.description

        /**
         * Se o usuario quiser editar o exercicio, será recebido atraves da variavel
         * wannaEdit, entao a logica sera aplicada
         */
        wannaEditExercise(data.wannaEdit, data.staticTitle, data.name)

        createExercise(data)
    }

    private fun createExercise(data:DataFromRecycler){
        val btnAddExercise = binding.btnAddExercise
        btnAddExercise.setOnClickListener {
            val duration = binding.editTextDuration.text.toString()

            if (duration != "0") {

                val exercise = Exercises(
                    data.name,
                    data.title,
                    data.description,
                    data.type,
                    duration
                )

                val training = Training(
                    data.trainingId,
                    data.trainingName,
                    data.staticTitle,
                    exercise,
                    LocalDateTime.now().toString(),
                    data.trainingDesc
                )
                trainingViewModel.addExerciseFromTrainingAndCreateTrainingOnFirebaseIfNotExists(
                    training,
                    this
                )
            } else {
                Toast.makeText(
                    this,
                    "A duracao precisa ser maior do que 0 minutos nem vazio!",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
    }


    private fun wannaEditExercise(wannaEdit: Boolean, staticTitle: String, exerciseId: Long) {
        if (wannaEdit) {
            binding.btnAddExercise.apply {
                visibility = View.GONE
                setOnClickListener {}
            }
            binding.btnUpdateDuration.apply {
                visibility = View.VISIBLE
                setOnClickListener {
                    val time = binding.editTextDuration.text.toString()
                    trainingViewModel.updateDuration(
                        time,
                        this@VizualizeExercise,
                        staticTitle,
                        exerciseId
                    )
                }
            }
        }

    }
}