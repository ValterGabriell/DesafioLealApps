package com.valtergabriel.desafiolealapps.repo

import android.content.Context
import android.content.Intent
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.ktx.toObject
import com.valtergabriel.desafiolealapps.mock.Exercises
import com.valtergabriel.desafiolealapps.mock.Training
import com.valtergabriel.desafiolealapps.ui.CreateTrainingActivity
import com.valtergabriel.desafiolealapps.util.Constants.COLLECTION_EXERCISES
import com.valtergabriel.desafiolealapps.util.Constants.COLLECTION_TRAINING
import com.valtergabriel.desafiolealapps.util.Constants.COLLECTION_USER_NAME
import com.valtergabriel.desafiolealapps.util.Firebase

class TrainingRepo {


    suspend fun createNewTraining(training: Training, context: Context) {

        val userAuthenticated = Firebase.getAuth().currentUser
        if (userAuthenticated != null) {

            val exercisesData = hashMapOf(
                "id_exercise" to training.exercise?.id,
                "name" to training.exercise?.name,
                "desc" to training.exercise?.desc,
                "type" to training.exercise?.type,
                "creationDay" to training.dateTime
            )
            val trainingData = hashMapOf(
                "id" to training.dateTime,
                "name" to training.name
            )


            Firebase.getFirestore().collection(COLLECTION_USER_NAME)
                .document(userAuthenticated.uid)
                .collection(COLLECTION_TRAINING)
                .document(training.name)
                .set(trainingData)

            Firebase.getFirestore().collection(COLLECTION_USER_NAME)
                .document(userAuthenticated.uid)
                .collection(COLLECTION_TRAINING)
                .document(training.name)
                .collection(COLLECTION_EXERCISES)
                .document(training.exercise!!.name)
                .set(exercisesData)
                .also {
                    Intent(context, CreateTrainingActivity::class.java).also {
                        it.putExtra("isExerciseAdded", training.name)
                        context.startActivity(it)
                    }
                }


        }
    }

    suspend fun getExercisesFromFirebase(
        traningName: String,
        listExercises: MutableLiveData<ArrayList<Exercises>>
    ) {

        val userAuthenticated = Firebase.getAuth().currentUser
        if (userAuthenticated != null) {
            val listAux = ArrayList<Exercises>()
            Firebase.getFirestore().collection(COLLECTION_USER_NAME)
                .document(userAuthenticated.uid)
                .collection(COLLECTION_TRAINING)
                .document(traningName)
                .collection(COLLECTION_EXERCISES)
                .get()
                .addOnSuccessListener { exercises ->
                    for (item in exercises) {
                        val model = Exercises(
                            item["id_exercise"].toString(),
                            item["name"].toString(),
                            item["desc"].toString(),
                            item["type"].toString()
                        )
                        listAux.add(model)
                    }
                    listExercises.postValue(listAux)
                }
        }
    }

    suspend fun getTraningsFromFirebase(
        listExercises: MutableLiveData<ArrayList<Training>>
    ) {

        val userAuthenticated = Firebase.getAuth().currentUser
        if (userAuthenticated != null) {
            val listAux = ArrayList<Training>()
            Firebase.getFirestore().collection(COLLECTION_USER_NAME)
                .document(userAuthenticated.uid)
                .collection(COLLECTION_TRAINING)
                .get()
                .addOnSuccessListener { exercises ->
                    for (item in exercises) {
                        val model = Training(
                            item["name"].toString(),
                            null,
                            item["id"].toString()
                        )
                        listAux.add(model)
                    }
                    listExercises.postValue(listAux)
                }
        }
    }

    suspend fun getTraining(
        traningName: String,
        traning: MutableLiveData<Training>
    ) {

        val userAuthenticated = Firebase.getAuth().currentUser
        if (userAuthenticated != null) {
            Firebase.getFirestore().collection(COLLECTION_USER_NAME)
                .document(userAuthenticated.uid)
                .collection(COLLECTION_TRAINING)
                .document(traningName)
                .get()
                .addOnSuccessListener {
                    val myTraining = Training(
                        it["name"].toString(),
                        null,
                        it["id"].toString()
                    )
                    traning.postValue(myTraining)
                }

        }
    }


}