package com.valtergabriel.desafiolealapps.viewmodel

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valtergabriel.desafiolealapps.dto.Exercises
import com.valtergabriel.desafiolealapps.dto.Training
import com.valtergabriel.desafiolealapps.repo.TrainingRepo
import kotlinx.coroutines.launch

class TrainingViewModel(private val trainingRepo: TrainingRepo) : ViewModel() {

    val listExercises = MutableLiveData<ArrayList<Exercises>>()
    val listTraining = MutableLiveData<ArrayList<Training>>()


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

    fun finishTraining(trainingName: String, imgBefore: Uri, uriAfter: Uri, context: Context) {
        viewModelScope.launch {
            trainingRepo.finishTraining(trainingName, imgBefore, uriAfter, context)
        }
    }

    fun retriveImages(
        trainingName: String,
        imgBefore: ImageView,
        imgAfter: ImageView,
        context: Context
    ) {
        viewModelScope.launch {
            trainingRepo.retriveImages(trainingName, imgBefore, imgAfter, context)
        }
    }

    fun updateTrainingData(
        trainingName: String,
        newTrainingName: String,
        context: Context
    ) {
        viewModelScope.launch {
            trainingRepo.updateTrainingData(trainingName, newTrainingName, context)
        }
    }


    fun deleteTraining(
        trainingName: String,
        context: Context
    ) {
        viewModelScope.launch {
            trainingRepo.deleteTraining(trainingName, context)
        }
    }


}