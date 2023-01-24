package com.valtergabriel.desafiolealapps.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valtergabriel.desafiolealapps.mock.Exercises
import com.valtergabriel.desafiolealapps.mock.Training
import com.valtergabriel.desafiolealapps.repo.TrainingRepo
import kotlinx.coroutines.launch

class TrainingViewModel(private val trainingRepo: TrainingRepo) : ViewModel() {

    val listExercises = MutableLiveData<ArrayList<Exercises>>()
    val listTraining = MutableLiveData<ArrayList<Training>>()
    val traning = MutableLiveData<Training>()

    fun createNewTrainingOnFirebase(training: Training, context: Context) {
        viewModelScope.launch {
            trainingRepo.createNewTraining(training, context)
        }
    }

    fun getExercisesFromFirebase(trainingName: String) {
        viewModelScope.launch {
            trainingRepo.getExercisesFromFirebase(trainingName, listExercises)
        }
    }

    fun getTraningsFromFirebase() {
        viewModelScope.launch {
            trainingRepo.getTraningsFromFirebase(listTraining)
        }
    }

    fun getCurrentTraning(trainingName: String) {
        viewModelScope.launch {
            trainingRepo.getTraining(trainingName, traning)
        }
    }


}