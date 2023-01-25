package com.valtergabriel.desafiolealapps.ui

import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.valtergabriel.desafiolealapps.databinding.ActivityCreateTrainingBinding
import com.valtergabriel.desafiolealapps.ui.adapter.ExerciseFirebaseAdapter
import com.valtergabriel.desafiolealapps.util.Constants.EXEMPLE
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

        val btnAddNewExercise = binding.btnAddNewExercise
        val fabCreateTraining = binding.fabCreateTraining

        setNameFromEditText()
        verifyIfThereIsANameOnEditText()


        fabCreateTraining.setOnClickListener {
            Intent(this, FeedActivity::class.java).also {
                startActivity(it)
            }
        }

        btnAddNewExercise.setOnClickListener {
            validateIfTheresNameThatCameFromAddNewExerciseActivity()
        }
    }

    private fun setNameFromEditText(){

        /**
         * Quando o nome do treino é setado e o usuario avança para a tela de adicionar exercicios,
         * esse parametro é recebido ao voltar da tela de adicionar exercicios para a tela de criacao
         * de treinos, para que possamos setar o edit text do nome do treino com o nome setado inicialmente.
         * assim, garantimos que todos os exercicios serao associados ao treino.
         */
        intent.extras?.get("traning_name_from_last_exercise_added").toString().also { trainingName ->

            if (trainingName != EXEMPLE){
                trainingViewModel.getExercisesFromFirebase(trainingName).also {
                    binding.nameTrainingEditText.apply {
                        setText(trainingName)
                    }
                    trainingViewModel.listExercises.observe(this) { exercises ->
                        adapter = ExerciseFirebaseAdapter(exercises)
                        binding.recyclerViewAddExercisesActivity.adapter = adapter
                        binding.recyclerViewAddExercisesActivity.layoutManager = LinearLayoutManager(this)
                    }
                }
            }else{
                binding.nameTrainingEditText.setText("")
            }
        }

    }

    private fun verifyIfThereIsANameOnEditText(){
        val text = binding.nameTrainingEditText.text
        if (text?.isEmpty() == true){
            binding.fabCreateTraining.visibility = View.GONE
        }
    }

    private fun validateIfTheresNameThatCameFromAddNewExerciseActivity(){
        val trainingName = binding.nameTrainingEditText.text.toString()
        if (!Validation.isEmptyField(trainingName)) {
            Intent(this, AddNewExerciseActivity::class.java).also {
                it.putExtra("training_name", trainingName)
                startActivity(it)
            }
        } else {
            Toast.makeText(this, "Defina um nome para seu treino antes", Toast.LENGTH_SHORT)
                .show()
        }
    }
}