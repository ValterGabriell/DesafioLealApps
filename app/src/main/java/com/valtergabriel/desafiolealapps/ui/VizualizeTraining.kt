package com.valtergabriel.desafiolealapps.ui

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.awesomedialog.*
import com.valtergabriel.desafiolealapps.R
import com.valtergabriel.desafiolealapps.databinding.ActivityFeedBinding
import com.valtergabriel.desafiolealapps.databinding.ActivityVizualizeTrainingBinding
import com.valtergabriel.desafiolealapps.ui.adapter.ExerciseFirebaseAdapter
import com.valtergabriel.desafiolealapps.ui.adapter.FeedAdapter
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

        val trainingName = intent.extras?.get("traning_name_from_feed").toString()
        binding.txtName.text = trainingName


        val trainingId = intent.extras?.get("traning_id") as Long
        if (trainingId != null) {
            binding.fabFinish.visibility = View.VISIBLE
        }



        binding.imageButton.setOnClickListener {
            Intent(this, FinishTrainingActivity::class.java).also {
                it.putExtra("is_just_see", true)
                it.putExtra("training_name", trainingName)
                startActivity(it)
            }
        }


        binding.fabFinish.setOnClickListener {
            AwesomeDialog.build(this)
                .title("Acompanhe seu desempenho")
                .body("Salve suas fotos de antes e depois do treino")
                .onPositive("Salvar!") {
                    Intent(this, FinishTrainingActivity::class.java).also {
                        it.putExtra("is_just_see", false)
                        it.putExtra("training_name", trainingName)
                        startActivity(it)
                    }
                }
                .onNegative("Não quero, obrigado!") {
                    Intent(this, FeedActivity::class.java).also {
                        startActivity(it)
                    }
                }
        }


        trainingViewModel.getExercisesFromFirebase(trainingName).also {
            trainingViewModel.listExercises.observe(this) { exercies ->

                if (exercies.isNotEmpty()) {
                    binding.loadingList.visibility = View.GONE
                    binding.recyclerView.visibility = View.VISIBLE

                    adapter = ExerciseFirebaseAdapter(exercies)
                    binding.recyclerView.adapter = adapter
                    binding.recyclerView.layoutManager = LinearLayoutManager(this)
                    adapter.setOnClick = { name, duration, type, obs, exercise_id ->
                        AwesomeDialog.build(this)
                            .title(name)
                            .body(obs)
                            .onPositive("Ok!")
                    }
                } else {
                    binding.loadingList.visibility = View.GONE
                    binding.emptyList.visibility = View.VISIBLE
                }

            }

        }
    }

}