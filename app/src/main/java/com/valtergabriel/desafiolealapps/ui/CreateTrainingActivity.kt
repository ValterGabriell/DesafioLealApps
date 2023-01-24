package com.valtergabriel.desafiolealapps.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.valtergabriel.desafiolealapps.databinding.ActivityCreateTrainingBinding
import com.valtergabriel.desafiolealapps.ui.adapter.ExerciseFirebaseAdapter
import com.valtergabriel.desafiolealapps.ui.adapter.FeedAdapter
import com.valtergabriel.desafiolealapps.util.Validation
import com.valtergabriel.desafiolealapps.viewmodel.TrainingViewModel
import org.koin.android.ext.android.inject


class CreateTrainingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateTrainingBinding
    private lateinit var adapter: ExerciseFirebaseAdapter
    private val trainingViewModel by inject<TrainingViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateTrainingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val trainingName = intent.extras?.get("isExerciseAdded").toString()
        configureEditText(trainingName)


        trainingViewModel.getExercisesFromFirebase(trainingName).also {
            trainingViewModel.listExercises.observe(this){ exercises ->
                adapter = ExerciseFirebaseAdapter(exercises)
                binding.recyclerViewAddExercisesActivity.adapter = adapter
                binding.recyclerViewAddExercisesActivity.layoutManager = GridLayoutManager(this, 2)

            }
        }




        binding.btnAddNewExercise.setOnClickListener {
            val trainingName = binding.nameTrainingEditText.text.toString()
            if (!Validation.isEmptyField(trainingName)) {
                Intent(this, CreateNewExerciseActivity::class.java).also {
                    it.putExtra("training_name", trainingName)
                    startActivity(it)
                }
            } else {
                Toast.makeText(this, "Defina um nome para seu treino antes", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun configureEditText(trainingName: String) {

        if (trainingName.isNotEmpty()) {
            binding.nameTrainingEditText.setText(trainingName)
        }else{
            binding.nameTrainingEditText.setText("")
        }
    }


}