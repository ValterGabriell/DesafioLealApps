package com.valtergabriel.desafiolealapps.ui

import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.awesomedialog.AwesomeDialog
import com.example.awesomedialog.body
import com.example.awesomedialog.onPositive
import com.example.awesomedialog.title
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
        val btnFirstAddTraining = binding.btnFirstAddTraining



        setNameFromEditText()
        configViewsToUpdateTraining()

        fabCreateTraining.setOnClickListener {
            Intent(this, FeedActivity::class.java).also {
                startActivity(it)
            }
        }

        btnAddNewExercise.setOnClickListener {
            validateIfTheresNameThatCameFromAddNewExerciseActivity()
        }

        btnFirstAddTraining.setOnClickListener {
            validateIfTheresNameThatCameFromAddNewExerciseActivity()
        }
    }

    private fun configViewsToUpdateTraining() {
        val wannaEdit = intent.extras?.get("wanna_edit") as Boolean
        val trainingName = intent.extras?.get("training_name").toString()
        if (wannaEdit) {
            binding.txtInputObs.visibility = View.GONE
            binding.txtExercises.visibility = View.GONE
            binding.btnAddNewExercise.visibility = View.GONE



            binding.editUpdateName.setText("")
            binding.editUpdateName.visibility = View.VISIBLE
            binding.txtHeader.visibility = View.VISIBLE

            binding.btnDelete.apply {
                visibility = View.VISIBLE
                setOnClickListener {
                    trainingViewModel.deleteTraining(trainingName, this@CreateTrainingActivity)
                }
            }

            binding.btnUpdate.apply {
                visibility = View.VISIBLE
                setOnClickListener {
                    val newTrainingName = binding.editUpdateName.text.toString()
                    trainingViewModel.updateTrainingData(trainingName, newTrainingName, this@CreateTrainingActivity)
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
        intent.extras?.get("traning_name_from_last_exercise_added").toString()
            .also { trainingName ->
                if (trainingName != EXEMPLE) {
                    binding.txtInputObs.visibility = View.GONE
                    binding.txtInputName.visibility = View.GONE
                    binding.btnFirstAddTraining.visibility = View.GONE
                    binding.txtHeader.visibility = View.GONE

                    binding.txtExercises.visibility = View.VISIBLE
                    binding.recyclerViewAddExercisesActivity.visibility = View.VISIBLE
                    binding.btnAddNewExercise.visibility = View.VISIBLE
                    binding.fabCreateTraining.visibility = View.VISIBLE



                    trainingViewModel.getExercisesFromFirebase(trainingName).also {
                        binding.nameTrainingEditText.apply {
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
                    binding.nameTrainingEditText.setText("")
                }
            }

    }


    private fun validateIfTheresNameThatCameFromAddNewExerciseActivity() {
        val trainingName = binding.nameTrainingEditText.text.toString()
        val trainingDesc = binding.obsTrainingEditText.text.toString()
        if (!Validation.isEmptyField(trainingName)) {
            Intent(this, AddNewExerciseActivity::class.java).also {
                it.putExtra("training_name", trainingName)
                it.putExtra("training_desc", trainingDesc)
                it.putExtra("static_title", trainingName)
                startActivity(it)
            }
        } else {
            Toast.makeText(this, "Defina um nome para seu treino antes", Toast.LENGTH_SHORT)
                .show()
        }
    }
}