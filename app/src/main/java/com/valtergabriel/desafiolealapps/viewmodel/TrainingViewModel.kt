package com.valtergabriel.desafiolealapps.viewmodel

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valtergabriel.desafiolealapps.dto.Exercises
import com.valtergabriel.desafiolealapps.dto.Training
import com.valtergabriel.desafiolealapps.repo.TrainingExerciseRepository
import kotlinx.coroutines.launch

class TrainingViewModel(private val trainingExerciseRepository: TrainingExerciseRepository) : ViewModel() {

    val listExercises = MutableLiveData<ArrayList<Exercises>>()
    val listTraining = MutableLiveData<ArrayList<Training>>()


    fun addExerciseFromTrainingAndCreateTrainingOnFirebaseIfNotExists(training: Training, context: Context) {
        viewModelScope.launch {
            trainingExerciseRepository.addExerciseFromTrainingAndCreateTrainingOnFirebaseIfNotExists(training, context)
        }
    }

    fun updateDuration(duration:String, context:Context, staticTitle:String, exerciseId:Long) {
        viewModelScope.launch {
            trainingExerciseRepository.updateDuration(duration, context, staticTitle, exerciseId)
        }
    }

    fun createNewExercise(exercises: Exercises,trainingName: String, context: Context) {
        viewModelScope.launch {
            trainingExerciseRepository.createNewExercise(exercises, trainingName, context)
        }
    }

    fun getExercisesFromFirebase(trainingName: String) {
        viewModelScope.launch {
            trainingExerciseRepository.getExercisesFromFirebase(trainingName, listExercises)
        }
    }

    fun getTraningsFromFirebase() {
        viewModelScope.launch {
            trainingExerciseRepository.getTraningsFromFirebase(listTraining)
        }
    }

    fun finishTraining(trainingName: String, imgBefore: Uri, uriAfter: Uri, context: Context) {
        viewModelScope.launch {
            trainingExerciseRepository.finishTraining(trainingName, imgBefore, uriAfter, context)
        }
    }

    fun retriveImages(
        trainingName: String,
        imgBefore: ImageView,
        imgAfter: ImageView,
        context: Context
    ) {
        viewModelScope.launch {
            trainingExerciseRepository.retriveImages(trainingName, imgBefore, imgAfter, context)
        }
    }

    fun updateTrainingData(
        trainingName: String,
        newTrainingName: String,
        context: Context
    ) {
        viewModelScope.launch {
            trainingExerciseRepository.updateTrainingData(trainingName, newTrainingName, context)
        }
    }


    fun deleteTraining(
        trainingName: String,
        context: Context
    ) {
        viewModelScope.launch {
            trainingExerciseRepository.deleteTraining(trainingName, context)
        }
    }



    fun deleteExercise(
        trainingName: String,
        exerciseId: String,
        context: Context
    ) {
        viewModelScope.launch {
            trainingExerciseRepository.deleteExercise(trainingName, exerciseId, context)
        }
    }


}