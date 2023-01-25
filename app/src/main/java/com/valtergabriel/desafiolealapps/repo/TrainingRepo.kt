package com.valtergabriel.desafiolealapps.repo

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import com.squareup.picasso.Picasso
import com.valtergabriel.desafiolealapps.R
import com.valtergabriel.desafiolealapps.dto.Exercises
import com.valtergabriel.desafiolealapps.dto.Training
import com.valtergabriel.desafiolealapps.ui.CreateTrainingActivity
import com.valtergabriel.desafiolealapps.ui.FeedActivity
import com.valtergabriel.desafiolealapps.util.Constants.COLLECTION_EXERCISES
import com.valtergabriel.desafiolealapps.util.Constants.COLLECTION_TRAINING
import com.valtergabriel.desafiolealapps.util.Constants.COLLECTION_USER_NAME
import com.valtergabriel.desafiolealapps.util.Constants.PATH_PATTERN
import com.valtergabriel.desafiolealapps.util.Firebase

class TrainingRepo {


    suspend fun createNewTraining(training: Training, context: Context) {

        val userAuthenticated = Firebase.getAuth().currentUser
        if (userAuthenticated != null) {

            val exercisesData = hashMapOf(
                "name" to training.exercise?.name,
                "title" to training.exercise?.title,
                "obs" to training.exercise?.obs,
                "type" to training.exercise?.type,
                "creationDay" to training.dateTime,
                "duration" to training.exercise?.duration
            )
            val trainingData = hashMapOf(
                "name" to training.name,
                "title" to training.title,
                "date" to training.dateTime,
                "desc" to training.desc,
                "imageBefore" to PATH_PATTERN,
                "imageAfter" to PATH_PATTERN
            )


            Firebase.getFirestore().collection(COLLECTION_USER_NAME)
                .document(userAuthenticated.uid)
                .collection(COLLECTION_TRAINING)
                .document(training.title.toString())
                .set(trainingData)

            Firebase.getFirestore().collection(COLLECTION_USER_NAME)
                .document(userAuthenticated.uid)
                .collection(COLLECTION_TRAINING)
                .document(training.title.toString())
                .collection(COLLECTION_EXERCISES)
                .document(training.exercise!!.name.toString())
                .set(exercisesData)
                .also {
                    Intent(context, CreateTrainingActivity::class.java).also {
                        it.putExtra("traning_name_from_last_exercise_added", training.title)
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
                            item["name"] as Long,
                            item["title"].toString(),
                            item["obs"].toString(),
                            item["type"].toString(),
                            item["duration"].toString()
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
                            item["name"] as Long,
                            item["title"].toString(),
                            null,
                            item["id"].toString(),
                            item["desc"].toString()
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
                        it["name"] as Long,
                        it["title"].toString(),
                        null,
                        it["id"].toString(),
                        it["desc"].toString()
                    )
                    traning.postValue(myTraining)
                }

        }
    }

    suspend fun finishTraining(
        traningName: String,
        uriBefore: Uri,
        uriAfter: Uri,
        context: Context
    ) {

        val userAuthenticated = Firebase.getAuth().currentUser
        if (userAuthenticated != null) {

            savePicturesOnStorage(userAuthenticated, traningName, uriBefore, uriAfter).also {
                updateTraningDataUriOnFirestore(userAuthenticated, traningName).also {
                    Intent(context, FeedActivity::class.java).also {
                        context.startActivity(it)
                    }
                }
            }
        }
    }

     fun retriveImages(traningName: String, imgBefore: ImageView, imgAfter: ImageView) {
        val userAuthenticated = Firebase.getAuth().currentUser


        if (userAuthenticated != null) {

            val trainingRef = Firebase.getFirestore().collection(COLLECTION_USER_NAME)
                .document(userAuthenticated.uid)
                .collection(COLLECTION_TRAINING)
                .document(traningName)
                .get()


            trainingRef.addOnSuccessListener {
                    if (it.exists()) {
                        val path = it.get("imageBefore").toString()
                        if (path != PATH_PATTERN) {
                            val img = Uri.parse(path)
                            Picasso.get().load(img).placeholder(R.drawable.a).into(imgBefore)
                        }
                    }
                }

            trainingRef.addOnSuccessListener {
                if (it.exists()) {
                    val path = it.get("imageAfter").toString()
                    if (path != PATH_PATTERN) {
                        val img = Uri.parse(path)
                        Picasso.get().load(img).placeholder(R.drawable.a).into(imgAfter)
                    }
                }
            }

        }
    }


    private fun updateTraningDataUriOnFirestore(
        userAuthenticated: FirebaseUser,
        traningName: String
    ) {
        val trainingRef = Firebase.getFirestore().collection(COLLECTION_USER_NAME)
            .document(userAuthenticated.uid)
            .collection(COLLECTION_TRAINING)
            .document(traningName)

        Firebase.getStorage().reference
            .child("images/${userAuthenticated.uid}")
            .child("$traningName/after")
            .downloadUrl.addOnSuccessListener { uri ->
                trainingRef.update(
                    "imageAfter",
                    uri.toString()
                )
            }

        Firebase.getStorage().reference
            .child("images/${userAuthenticated.uid}")
            .child("$traningName/before")
            .downloadUrl.addOnSuccessListener { uri ->
                trainingRef.update(
                    "imageBefore",
                    uri.toString()
                )
            }

    }


    private fun savePicturesOnStorage(
        userAuthenticated: FirebaseUser,
        traningName: String,
        uriBefore: Uri,
        uriAfter: Uri
    ) {


        val ref = Firebase.getStorage().reference
            .child("images/${userAuthenticated.uid}")
            .child(traningName)

        ref
            .child("before")
            .putFile(uriBefore)

        ref
            .child("after")
            .putFile(uriAfter)


    }


}