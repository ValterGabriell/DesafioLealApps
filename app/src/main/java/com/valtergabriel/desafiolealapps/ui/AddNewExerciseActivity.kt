package com.valtergabriel.desafiolealapps.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.valtergabriel.desafiolealapps.R
import com.valtergabriel.desafiolealapps.databinding.ActivityAddNewExerciseBinding
import com.valtergabriel.desafiolealapps.dto.DataFromRecycler
import com.valtergabriel.desafiolealapps.dto.MockExercise
import com.valtergabriel.desafiolealapps.ui.adapter.ExerciseMockAdapter
import com.valtergabriel.desafiolealapps.util.Constants
import com.valtergabriel.desafiolealapps.util.Constants.DATA

class AddNewExerciseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNewExerciseBinding
    private lateinit var adapterBicepis: ExerciseMockAdapter
    private lateinit var adapterAbdomen: ExerciseMockAdapter
    private lateinit var adapterGetFit: ExerciseMockAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_exercise)
        binding = ActivityAddNewExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val btnCreateOnwExercise = binding.btnCreateOnwExercise

        val trainingName = intent.extras?.get(Constants.TRAINING_NAME).toString()
        val trainingDesc = intent.extras?.get(Constants.TRAINING_DESCRIPTION).toString()
        val staticTitle = intent.extras?.get(Constants.STATIC_TITLE).toString()

        btnCreateOnwExercise.setOnClickListener {
            createOwnExercise(staticTitle)
        }


        /**
         * Lista de exercicios em mock
         */
        val listBicepis = MockExercise().bicepisExercises()
        configureRecyclerBicepis(listBicepis, staticTitle, trainingDesc, trainingName)


        val listAbdomen = MockExercise().abdomenExercises()
        configureRecyclerAbdomen(listAbdomen, staticTitle, trainingDesc, trainingName)


        val listGetFit = MockExercise().getFit()
        configureRecyclerGetFit(listGetFit, staticTitle, trainingDesc, trainingName)


    }

    private fun createOwnExercise(staticTitle: String) {
        Intent(this@AddNewExerciseActivity, CreateOnwExerciseActivity::class.java).also {
            it.putExtra(Constants.STATIC_TITLE, staticTitle)
            startActivity(it)
        }
    }

    private fun configureRecyclerBicepis(
        listBicepis: List<MockExercise>,
        staticTitle: String,
        trainingDesc: String,
        trainingName: String
    ) {
        adapterBicepis = ExerciseMockAdapter(listBicepis)
        binding.recyclerAddExercise.adapter = adapterBicepis
        binding.recyclerAddExercise.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        adapterBicepis.setOnClick = { name, title, duration, type, description ->
            Intent(this@AddNewExerciseActivity, VizualizeExercise::class.java).also {
                val dataFromRecycler = DataFromRecycler()
                dataFromRecycler.apply {
                    this.title = title
                    this.name = name
                    this.duration = duration
                    this.description = description
                    this.staticTitle = staticTitle
                    this.type = type
                    this.trainingName = trainingName
                    this.trainingDesc = trainingDesc
                    this.trainingId = System.currentTimeMillis()
                    this.wannaEdit = false
                }
                it.putExtra(DATA, dataFromRecycler)
                startActivity(it)
            }
        }
    }

    private fun configureRecyclerAbdomen(
        listBicepis: List<MockExercise>,
        staticTitle: String,
        trainingDesc: String,
        trainingName: String
    ) {
        adapterAbdomen = ExerciseMockAdapter(listBicepis)
        binding.recyclerAbdomen.adapter = adapterAbdomen
        binding.recyclerAbdomen.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        adapterAbdomen.setOnClick = { name, title, duration, type, exeDescription ->
            Intent(this@AddNewExerciseActivity, VizualizeExercise::class.java).also {
                val dataFromRecycler = DataFromRecycler()
                dataFromRecycler.apply {
                    this.title = title
                    this.name = name
                    this.duration = duration
                    this.description = description
                    this.staticTitle = staticTitle
                    this.type = type
                    this.trainingName = trainingName
                    this.trainingDesc = trainingDesc
                    this.trainingId = System.currentTimeMillis()
                    this.wannaEdit = false
                }
                it.putExtra(DATA, dataFromRecycler)
                startActivity(it)
            }
        }
    }

    private fun configureRecyclerGetFit(
        listBicepis: List<MockExercise>,
        staticTitle: String,
        trainingDesc: String,
        trainingName: String
    ) {
        adapterGetFit = ExerciseMockAdapter(listBicepis)
        binding.recyclerGetFit.adapter = adapterGetFit
        binding.recyclerGetFit.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        adapterGetFit.setOnClick = { name, title, duration, type, description ->
            Intent(this@AddNewExerciseActivity, VizualizeExercise::class.java).also {

                val dataFromRecycler = DataFromRecycler()
                dataFromRecycler.apply {
                    this.title = title
                    this.name = name
                    this.duration = duration
                    this.description = description
                    this.staticTitle = staticTitle
                    this.type = type
                    this.trainingName = trainingName
                    this.trainingDesc = trainingDesc
                    this.trainingId = System.currentTimeMillis()
                    this.wannaEdit = false
                }
                it.putExtra(DATA, dataFromRecycler)
                startActivity(it)
            }
        }
    }
}