package com.valtergabriel.desafiolealapps.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.valtergabriel.desafiolealapps.databinding.ActivityCreateTrainingBinding
import com.valtergabriel.desafiolealapps.util.Validation


class CreateTrainingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateTrainingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateTrainingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val trainingName = intent.extras?.get("isExerciseAdded").toString()
        if (!trainingName.isNullOrEmpty()) {
            binding.nameTrainingEditText.setText(trainingName)
        }else{
            binding.nameTrainingEditText.setText("")
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


}