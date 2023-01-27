package com.valtergabriel.desafiolealapps.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.awesomedialog.AwesomeDialog
import com.example.awesomedialog.body
import com.example.awesomedialog.onPositive
import com.example.awesomedialog.title
import com.valtergabriel.desafiolealapps.databinding.ActivityCreateTrainingBinding
import com.valtergabriel.desafiolealapps.ui.adapter.ExerciseFirebaseAdapter
import com.valtergabriel.desafiolealapps.util.Constants
import com.valtergabriel.desafiolealapps.util.Constants.EXEMPLE
import com.valtergabriel.desafiolealapps.util.Validation
import com.valtergabriel.desafiolealapps.viewmodel.TrainingViewModel
import org.koin.android.ext.android.inject


class CreateAndEditTrainingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateTrainingBinding
    private lateinit var adapter: ExerciseFirebaseAdapter
    private val trainingViewModel by inject<TrainingViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateTrainingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val btnAddNewExercise = binding.txtExercises
        val fabCreateTraining = binding.fabCreateTraining
        val btnFirstAddTraining = binding.btnFirstAddTraining


        /**
         * Setando variaveis
         */
        setNameFromEditText()

        /**
         * Se o usuario quiser editar o treino
         */
        configViewsToUpdateTraining()

        fabCreateTraining.setOnClickListener {
            Intent(this, FeedActivity::class.java).also {
                startActivity(it)
            }
        }

        btnAddNewExercise.setOnClickListener {
            validadeFieldsAndPassActivityToCreateExercises()
        }

        btnFirstAddTraining.setOnClickListener {
            validadeFieldsAndPassActivityToCreateExercises()
        }
    }

    private fun configViewsToUpdateTraining() {
        val wannaEdit = intent.extras?.get(Constants.WANNA_EDIT) as Boolean
        val trainingName = intent.extras?.get(Constants.STATIC_TITLE).toString()

        if (wannaEdit) {
            binding.txtInputObs.visibility = View.GONE
            binding.txtExercises.visibility = View.GONE
            binding.fabCreateTraining.visibility = View.GONE



            binding.editUpdateName.setText("")
            binding.editUpdateName.visibility = View.VISIBLE
            binding.txtHeader.visibility = View.VISIBLE

            binding.btnDelete.apply {
                visibility = View.VISIBLE
                setOnClickListener {
                    trainingViewModel.deleteTraining(trainingName, this@CreateAndEditTrainingActivity)
                }
            }

            binding.btnUpdate.apply {
                visibility = View.VISIBLE
                setOnClickListener {
                    val newTrainingName = binding.editUpdateName.text.toString()
                    trainingViewModel.updateTrainingData(trainingName, newTrainingName, this@CreateAndEditTrainingActivity)
                }
            }
        }
    }

    private fun setNameFromEditText() {

        /**
         * Quando o nome do treino é setado e o usuario avança para a tela de adicionar exercicios,
         * esse parametro é recebido ao voltar da tela de adicionar exercicios para a tela de criacao
         * de treinos, para que possamos setar o edit text do nome do treino com o nome setado inicialmente.
         * assim, garantimos que todos os exercicios serao associados ao treino.
         */
        intent.extras?.get(Constants.TRAINING_NAME_FROM_LAST_EXERCISES_ADDED).toString()
            .also { trainingName ->
                if (trainingName != EXEMPLE) {
                    binding.txtInputObs.visibility = View.GONE
                    binding.txtInputName.visibility = View.GONE
                    binding.btnFirstAddTraining.visibility = View.GONE
                    binding.txtHeader.visibility = View.GONE

                    binding.recyclerViewAddExercisesActivity.visibility = View.VISIBLE
                    binding.txtExercises.visibility = View.VISIBLE
                    binding.fabCreateTraining.visibility = View.VISIBLE



                    trainingViewModel.getExercisesFromFirebase(trainingName).also {
                        binding.nameTrainingeditText.apply {
                            setText(trainingName)
                        }
                        trainingViewModel.listExercises.observe(this) { exercises ->
                            adapter = ExerciseFirebaseAdapter(exercises)
                            binding.recyclerViewAddExercisesActivity.adapter = adapter
                            binding.recyclerViewAddExercisesActivity.layoutManager =
                                LinearLayoutManager(this)
                            adapter.setOnClick = { name, _, _, obs, _ ->
                                AwesomeDialog.build(this)
                                    .title(name)
                                    .body(obs)
                                    .onPositive("Ok!")
                            }
                        }
                    }
                } else {
                    binding.nameTrainingeditText.setText("")
                }
            }

    }


    private fun validadeFieldsAndPassActivityToCreateExercises() {
        val trainingName = binding.nameTrainingeditText.text.toString()
        val trainingDesc = binding.obsTrainingEditText.text.toString()

        if (!Validation.isEmptyField(trainingName)) {
            Intent(this, AddNewExerciseActivity::class.java).also {
                it.putExtra(Constants.TRAINING_NAME, trainingName)
                it.putExtra(Constants.TRAINING_DESCRIPTION, trainingDesc)
                it.putExtra(Constants.STATIC_TITLE, trainingName)
                startActivity(it)
            }
        } else {
            Toast.makeText(this, "Defina um nome para seu treino antes", Toast.LENGTH_SHORT)
                .show()
        }
    }
}