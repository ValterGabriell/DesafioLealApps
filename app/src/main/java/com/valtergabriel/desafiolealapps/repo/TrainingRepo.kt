package com.valtergabriel.desafiolealapps.repo

import android.content.Context
import android.content.Intent
import com.valtergabriel.desafiolealapps.mock.Exercises
import com.valtergabriel.desafiolealapps.mock.Training
import com.valtergabriel.desafiolealapps.ui.CreateTrainingActivity
import com.valtergabriel.desafiolealapps.util.Constants.COLLECTION_USER_NAME
import com.valtergabriel.desafiolealapps.util.Firebase

class TrainingRepo {


    suspend fun createNewTraining(training: Training, context: Context) {

        val userAuthenticated = Firebase.getAuth().currentUser
        if (userAuthenticated != null) {

            val trainingData = hashMapOf(
                "id" to training.dateTime,
                "name" to training.name,
                "exercises" to training.exercise,
                "creationDay" to training.dateTime
            )

            Firebase.getFirestore().collection(COLLECTION_USER_NAME)
                .document(userAuthenticated.uid)
                .collection("Treinos")
                .document(training.name)
                .collection("Exercicios")
                .document(training.exercise.name)
                .set(trainingData)
                .also {
                    Intent(context, CreateTrainingActivity::class.java).also {
                        it.putExtra("isExerciseAdded", training.name)
                        context.startActivity(it)
                    }
                }

        }
    }

    suspend fun getExercisesFromFirebase(traningName: String): List<Exercises> {

        val userAuthenticated = Firebase.getAuth().currentUser
        var listExercises = ArrayList<Exercises>()
        if (userAuthenticated != null) {

            Firebase.getFirestore().collection(COLLECTION_USER_NAME)
                .document(userAuthenticated.uid)
                .collection("Treinos")
                .document(traningName)
                .get()
                .addOnSuccessListener { document ->
                    listExercises.add(document.get("exercises") as Exercises)
                }
        }
        return listExercises
    }


}