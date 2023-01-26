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
        val staticTitle = intent.extras?.get("traning_static_name_from_feed").toString()
        val trainingDesc = intent.extras?.get("traning_desc_from_feed").toString()

        binding.txtName.text = trainingName
        binding.txtDesc.text = trainingDesc

        binding.imageButton.apply {
            setOnClickListener {
                changeActivity(trainingName)
            }
        }

        binding.imgEdit.setOnClickListener {
            Intent(this, CreateTrainingActivity::class.java).also {
                it.putExtra("wanna_edit", true)
                it.putExtra("training_name", staticTitle)
                startActivity(it)
            }
        }

        binding.fabFinish.setOnClickListener {
            AwesomeDialog.build(this)
                .title("Acompanhe seu desempenho")
                .body("Salve suas fotos de antes e depois do treino")
                .onPositive("Vamos lá!") {
                    Intent(this, FinishTrainingActivity::class.java).also {
                        it.putExtra("is_just_see", false)
                        it.putExtra("training_name", trainingName)
                        startActivity(it)
                    }
                }
        }


        trainingViewModel.getExercisesFromFirebase(staticTitle).also {
            trainingViewModel.listExercises.observe(this) { exercies ->

                if (exercies.isNotEmpty()) {
                    binding.loadingList.visibility = View.GONE
                    binding.recyclerView.visibility = View.VISIBLE

                    adapter = ExerciseFirebaseAdapter(exercies)
                    binding.recyclerView.adapter = adapter
                    binding.recyclerView.layoutManager = LinearLayoutManager(this)
                    adapter.setOnClick = { title, duration, type, obs, name ->

                        AwesomeDialog.build(this)
                            .title("$title - $duration min")
                            .body(obs)
                            .onPositive("Ok!")
                            .onNegative("Editar duração") {
                                Intent(this@VizualizeTraining, VizualizeExercise::class.java).also {
                                    it.putExtra("exercise_name", title)
                                    it.putExtra("id", name)
                                    it.putExtra("duration", duration)
                                    it.putExtra("type", type)
                                    it.putExtra("static_title", staticTitle)
                                    it.putExtra("training_desc", trainingDesc)
                                    it.putExtra("exe_desc", obs)
                                    it.putExtra("training_name", trainingName)
                                    it.putExtra("training_id", System.currentTimeMillis())
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

    private fun changeActivity(trainingName: String) {
        Intent(this, FinishTrainingActivity::class.java).also {
            it.putExtra("is_just_see", true)
            it.putExtra("training_name", trainingName)
            startActivity(it)
        }
    }

}