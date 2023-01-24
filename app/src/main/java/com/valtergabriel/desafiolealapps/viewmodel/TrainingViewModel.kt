package com.valtergabriel.desafiolealapps.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valtergabriel.desafiolealapps.mock.Exercises
import com.valtergabriel.desafiolealapps.mock.Training
import com.valtergabriel.desafiolealapps.repo.TrainingRepo
import kotlinx.coroutines.launch

class TrainingViewModel(private val trainingRepo: TrainingRepo) : ViewModel() {

   fun createNewTrainingOnFirebase(training: Training, context:Context) {
       viewModelScope.launch {
           trainingRepo.createNewTraining(training, context)
       }
    }

    fun getExercisesFromFirebase(trainingName: String) : List<Exercises>{
        var listExercises = ArrayList<Exercises>()
        viewModelScope.launch {
            listExercises = trainingRepo.getExercisesFromFirebase(trainingName) as ArrayList<Exercises>
        }
        return listExercises
    }


}