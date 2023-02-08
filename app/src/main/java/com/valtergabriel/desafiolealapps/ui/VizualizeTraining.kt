package com.valtergabriel.desafiolealapps.ui

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.awesomedialog.*
import com.valtergabriel.desafiolealapps.databinding.ActivityVizualizeTrainingBinding
import com.valtergabriel.desafiolealapps.ui.adapter.ExerciseFirebaseAdapter
import com.valtergabriel.desafiolealapps.util.Constants.EXERCISE_DESCRIPTION
import com.valtergabriel.desafiolealapps.util.Constants.EXERCISE_DURATION
import com.valtergabriel.desafiolealapps.util.Constants.EXERCISE_NAME
import com.valtergabriel.desafiolealapps.util.Constants.EXERCISE_NAME_ID
import com.valtergabriel.desafiolealapps.util.Constants.EXERCISE_TYPE
import com.valtergabriel.desafiolealapps.util.Constants.JUST_WANNA_SEE
import com.valtergabriel.desafiolealapps.util.Constants.STATIC_TITLE
import com.valtergabriel.desafiolealapps.util.Constants.TRAINING_DESCRIPTION
import com.valtergabriel.desafiolealapps.util.Constants.TRAINING_DESCRIPTION_FROM_FEED_SCREEN
import com.valtergabriel.desafiolealapps.util.Constants.TRAINING_NAME
import com.valtergabriel.desafiolealapps.util.Constants.TRAINING_NAME_FROM_FEED_SCREEN
import com.valtergabriel.desafiolealapps.util.Constants.TRAINING_NAME_ID
import com.valtergabriel.desafiolealapps.util.Constants.TRAINING_STATIC_NAME_FROM_FEED_SCREEN
import com.valtergabriel.desafiolealapps.util.Constants.WANNA_EDIT
import com.valtergabriel.desafiolealapps.viewmodel.TrainingViewModel
import org.koin.android.ext.android.inject

class VizualizeTraining : AppCompatActivity() {
    private lateinit var binding: ActivityVizualizeTrainingBinding
    private lateinit var adapter: ExerciseFirebaseAdapter
    private val trainingViewModel by inject<TrainingViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVizualizeTrainingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val trainingName = intent.extras?.get(TRAINING_NAME_FROM_FEED_SCREEN).toString()
        val staticTitle = intent.extras?.get(TRAINING_STATIC_NAME_FROM_FEED_SCREEN).toString()
        val trainingDesc = intent.extras?.get(TRAINING_DESCRIPTION_FROM_FEED_SCREEN).toString()

        binding.txtName.text = trainingName
        binding.txtDesc.text = trainingDesc

        binding.imageButton.apply {
            setOnClickListener {
                changeActivity(staticTitle)
            }
        }

        binding.btnAddMoreExercise.setOnClickListener {
            Intent(this, AddNewExerciseActivity::class.java).also {
                it.putExtra(STATIC_TITLE, staticTitle)
                it.putExtra(TRAINING_NAME, trainingName)
                it.putExtra(TRAINING_DESCRIPTION, trainingDesc)
                startActivity(it)
            }
        }

        binding.imgEdit.setOnClickListener {
            Intent(this, CreateAndEditTrainingActivity::class.java).also {
                it.putExtra(WANNA_EDIT, true)
                it.putExtra(STATIC_TITLE, staticTitle)
                startActivity(it)
            }
        }

        binding.fabFinish.setOnClickListener {
            AwesomeDialog.build(this)
                .title("Acompanhe seu desempenho")
                .body("Salve suas fotos de antes e depois do treino")
                .onPositive("Vamos lá!") {
                    Intent(this, FinishTrainingActivity::class.java).also {
                        it.putExtra(JUST_WANNA_SEE, false)
                        it.putExtra(STATIC_TITLE, staticTitle)
                        startActivity(it)
                    }
                }
        }


        trainingViewModel.getExercisesFromFirebase(staticTitle).also {
            trainingViewModel.listExercises.observe(this) { exercies ->
                if (exercies.isNotEmpty()) {
                    binding.loadingList.visibility = View.GONE
                    binding.recyclerView.visibility = View.VISIBLE
                    binding.fabFinish.visibility = View.VISIBLE

                    adapter = ExerciseFirebaseAdapter(exercies)
                    binding.recyclerView.adapter = adapter
                    binding.recyclerView.layoutManager = LinearLayoutManager(this)
                    adapter.setOnClick = { title, duration, type, obs, exercise_id ->

                        AwesomeDialog.build(this)
                            .title("$title - $duration min")
                            .body(obs)
                            .onPositive("Deletar") {
                                trainingViewModel.deleteExercise(
                                    staticTitle,
                                    exercise_id.toString(),
                                    this
                                )
                            }
                            .onNegative("Editar duração") {
                                Intent(this@VizualizeTraining, VizualizeExercise::class.java).also {
                                    it.putExtra(EXERCISE_NAME, title)
                                    it.putExtra(EXERCISE_NAME_ID, exercise_id)
                                    it.putExtra(EXERCISE_DURATION, duration)
                                    it.putExtra(EXERCISE_TYPE, type)
                                    it.putExtra(STATIC_TITLE, staticTitle)
                                    it.putExtra(TRAINING_DESCRIPTION, trainingDesc)
                                    it.putExtra(EXERCISE_DESCRIPTION, obs)
                                    it.putExtra(TRAINING_NAME, trainingName)
                                    it.putExtra(TRAINING_NAME_ID, System.currentTimeMillis())
                                    it.putExtra(WANNA_EDIT, true)
                                    startActivity(it)
                                }
                            }
                    }
                } else {
                    binding.loadingList.visibility = View.GONE
                    binding.emptyList.visibility = View.VISIBLE
                }

            }

        }
    }

    private fun changeActivity(staticTitle: String) {
        Intent(this, FinishTrainingActivity::class.java).also {
            it.putExtra(JUST_WANNA_SEE, true)
            it.putExtra(STATIC_TITLE, staticTitle)
            startActivity(it)
        }
    }

}