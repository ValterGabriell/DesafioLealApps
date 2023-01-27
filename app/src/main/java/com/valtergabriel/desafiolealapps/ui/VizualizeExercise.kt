package com.valtergabriel.desafiolealapps.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.valtergabriel.desafiolealapps.databinding.ActivityVizualizeExerciseBinding
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

        val trainingId = intent.extras?.get(Constants.TRAINING_NAME_ID) as Long
        val trainingName = intent.extras?.get(Constants.TRAINING_NAME).toString()
        val staticTitle = intent.extras?.get(Constants.STATIC_TITLE).toString()
        val trainingDesc = intent.extras?.get(Constants.TRAINING_DESCRIPTION).toString()

        val exerciseName = intent.extras?.get(Constants.EXERCISE_NAME).toString()
        val exerciseId = intent.extras?.get(Constants.EXERCISE_NAME_ID) as Long
        val exeDesc = intent.extras?.get(Constants.EXERCISE_DESCRIPTION).toString()
        val duration = intent.extras?.get(Constants.EXERCISE_DURATION).toString()
        val type = intent.extras?.get(Constants.EXERCISE_TYPE).toString()

        val wannaEdit = intent.extras?.get(Constants.WANNA_EDIT) as Boolean

        /**
         * Setando os dados de acordo com o que foi recebido
         */
        binding.editText.setText(duration)
        binding.txtTitle.text = exerciseName
        binding.txtDescView.text = exeDesc

        /**
         * Se o usuario quiser editar o exercicio, será recebido atraves da variavel
         * wannaEdit, entao a logica sera aplicada
         */
        wannaEditExercise(wannaEdit, staticTitle, exerciseId)


        binding.btnExercise.setOnClickListener {
            val listExercise = ArrayList<Exercises>()
            val duration = binding.editText.text.toString()

            if (duration != "0"){
                val exercise = Exercises(
                    exerciseId,
                    exerciseName,
                    exeDesc,
                    type,
                    duration
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
            }else{
                Toast.makeText(this, "A duracao precisa ser maior do que 0 minutos!", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun wannaEditExercise(wannaEdit: Boolean, staticTitle: String, exerciseId:Long) {
        if (wannaEdit) {
            binding.btnExercise.apply {
                visibility = View.GONE
                setOnClickListener {}
            }
            binding.btnUpdateDuration.apply {
                visibility = View.VISIBLE
                setOnClickListener {
                    val time = binding.editText.text.toString()
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