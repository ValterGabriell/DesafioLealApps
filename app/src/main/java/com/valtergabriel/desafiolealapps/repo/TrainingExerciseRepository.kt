package com.valtergabriel.desafiolealapps.repo

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import com.squareup.picasso.Picasso
import com.valtergabriel.desafiolealapps.R
import com.valtergabriel.desafiolealapps.dto.Exercises
import com.valtergabriel.desafiolealapps.dto.Training
import com.valtergabriel.desafiolealapps.ui.CreateAndEditTrainingActivity
import com.valtergabriel.desafiolealapps.ui.FeedActivity
import com.valtergabriel.desafiolealapps.util.Constants.COLLECTION_EXERCISES
import com.valtergabriel.desafiolealapps.util.Constants.COLLECTION_TRAINING
import com.valtergabriel.desafiolealapps.util.Constants.COLLECTION_USER_NAME
import com.valtergabriel.desafiolealapps.util.Constants.PATH_PATTERN
import com.valtergabriel.desafiolealapps.util.Constants.TRAINING_NAME_FROM_LAST_EXERCISES_ADDED
import com.valtergabriel.desafiolealapps.util.Constants.WANNA_EDIT
import com.valtergabriel.desafiolealapps.util.Firebase
import java.time.LocalDateTime

class TrainingExerciseRepository {


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
                "staticTitle" to training.staticTitle,
                "date" to training.dateTime,
                "desc" to training.desc,
                "imageBefore" to PATH_PATTERN,
                "imageAfter" to PATH_PATTERN
            )


            Firebase.getFirestore().collection(COLLECTION_USER_NAME)
                .document(userAuthenticated.uid)
                .collection(COLLECTION_TRAINING)
                .document(training.staticTitle.toString())
                .set(trainingData)

            Firebase.getFirestore().collection(COLLECTION_USER_NAME)
                .document(userAuthenticated.uid)
                .collection(COLLECTION_TRAINING)
                .document(training.staticTitle.toString())
                .collection(COLLECTION_EXERCISES)
                .document(training.exercise!!.name.toString())
                .set(exercisesData)
                .also {
                    Intent(context, CreateAndEditTrainingActivity::class.java).also {
                        it.putExtra(TRAINING_NAME_FROM_LAST_EXERCISES_ADDED, training.staticTitle)
                        it.putExtra(WANNA_EDIT, false)
                        context.startActivity(it)
                    }
                }


        }
    }

    suspend fun createNewExercise(exercises: Exercises, traningName: String, context: Context) {

        val userAuthenticated = Firebase.getAuth().currentUser
        if (userAuthenticated != null) {

            val exercisesData = hashMapOf(
                "name" to exercises.name,
                "title" to exercises.title,
                "obs" to exercises.obs,
                "type" to "was created",
                "creationDay" to exercises.name,
                "duration" to exercises.duration
            )

            Firebase.getFirestore().collection(COLLECTION_USER_NAME)
                .document(userAuthenticated.uid)
                .collection(COLLECTION_TRAINING)
                .document(traningName)
                .collection(COLLECTION_EXERCISES)
                .document(exercises.name.toString())
                .set(exercisesData)
                .also {
                    Intent(context, CreateAndEditTrainingActivity::class.java).also {
                        it.putExtra(TRAINING_NAME_FROM_LAST_EXERCISES_ADDED, traningName)
                        it.putExtra(WANNA_EDIT, false)
                        context.startActivity(it)
                    }
                }


        }
    }

    suspend fun updateDuration(
        duration: String,
        context: Context,
        staticTitle: String,
        exerciseId: Long
    ) {

        val userAuthenticated = Firebase.getAuth().currentUser
        if (userAuthenticated != null) {
            Firebase.getFirestore().collection(COLLECTION_USER_NAME)
                .document(userAuthenticated.uid)
                .collection(COLLECTION_TRAINING)
                .document(staticTitle)
                .collection(COLLECTION_EXERCISES)
                .document(exerciseId.toString())
                .update("duration", duration).addOnSuccessListener {
                    Intent(context, FeedActivity::class.java).also {
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
                            item["staticTitle"].toString(),
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


    suspend fun finishTraining(
        traningName: String,
        uriBefore: Uri,
        uriAfter: Uri,
        context: Context
    ) {

        val userAuthenticated = Firebase.getAuth().currentUser
        if (userAuthenticated != null) {

            savePicturesOnStorage(userAuthenticated, traningName, uriBefore, uriAfter, context)
        }
    }

    fun retriveImages(
        traningName: String,
        imgBefore: ImageView,
        imgAfter: ImageView,
        context: Context
    ) {
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
                    } else {
                        Toast.makeText(
                            context,
                            "Ainda nao foi setado fotos para esse treino",
                            Toast.LENGTH_SHORT
                        ).show()
                        Intent(context, FeedActivity::class.java).also {intent ->
                            context.startActivity(intent)
                        }
                    }
                }
            }

            trainingRef.addOnSuccessListener {
                if (it.exists()) {
                    val path = it.get("imageAfter").toString()
                    if (path != PATH_PATTERN) {
                        val img = Uri.parse(path)
                        Picasso.get().load(img).placeholder(R.drawable.a).into(imgAfter)
                    } else {
                        Toast.makeText(
                            context,
                            "Ainda nao foi setado fotos para esse treino",
                            Toast.LENGTH_SHORT
                        ).show()
                        Intent(context, FeedActivity::class.java).also {
                            context.startActivity(it)
                        }
                    }
                }
            }

        }
    }

    fun updateTrainingData(traningName: String, newTrainingName: String, context: Context) {
        val userAuthenticated = Firebase.getAuth().currentUser
        if (userAuthenticated != null) {
            val trainingRef = Firebase.getFirestore().collection(COLLECTION_USER_NAME)
                .document(userAuthenticated.uid)
                .collection(COLLECTION_TRAINING)
                .document(traningName)


            trainingRef.update(
                "title",
                newTrainingName
            ).addOnSuccessListener {
                Intent(context, FeedActivity::class.java).also {
                    context.startActivity(it)
                }
            }.addOnFailureListener {
                Log.i("TAG", "erro ao atualizar")
            }

            trainingRef.update(
                "date",
                LocalDateTime.now().toString()
            )
        }

    }

    fun deleteTraining(traningName: String, context: Context) {
        val userAuthenticated = Firebase.getAuth().currentUser
        if (userAuthenticated != null) {

            Firebase.getFirestore().collection(COLLECTION_USER_NAME)
                .document(userAuthenticated.uid)
                .collection(COLLECTION_TRAINING)
                .document(traningName)
                .delete()
                .addOnSuccessListener {
                    Toast.makeText(context, "Deletado com sucesso", Toast.LENGTH_SHORT).show()
                    Intent(context, FeedActivity::class.java).also {
                        context.startActivity(it)
                    }
                }.addOnFailureListener {
                    Toast.makeText(context, "Falha ao deletar", Toast.LENGTH_SHORT).show()
                    Intent(context, FeedActivity::class.java).also {
                        context.startActivity(it)
                    }
                }


            Firebase.getStorage().reference
                .child("images/${userAuthenticated.uid}/$traningName")
                .delete()

        }

    }

    fun deleteExercise(traningName: String, exerciseId: String, context: Context) {
        val userAuthenticated = Firebase.getAuth().currentUser
        if (userAuthenticated != null) {

            Firebase.getFirestore().collection(COLLECTION_USER_NAME)
                .document(userAuthenticated.uid)
                .collection(COLLECTION_TRAINING)
                .document(traningName)
                .collection(COLLECTION_EXERCISES)
                .document(exerciseId)
                .delete()
                .addOnSuccessListener {
                    Toast.makeText(context, "Exercicio deletado com sucesso", Toast.LENGTH_SHORT)
                        .show()
                    Intent(context, FeedActivity::class.java).also {
                        context.startActivity(it)
                    }
                }.addOnFailureListener {
                    Toast.makeText(context, "Falha ao deletar", Toast.LENGTH_SHORT).show()
                    Intent(context, FeedActivity::class.java).also {
                        context.startActivity(it)
                    }
                }
        }

    }


    private fun updateTraningDataUriOnFirestore(
        userAuthenticated: FirebaseUser,
        traningName: String,
        context: Context
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
                ).addOnSuccessListener {
                    Intent(context, FeedActivity::class.java).also {
                        context.startActivity(it)
                    }
                }
            }



        Firebase.getStorage().reference
            .child("images/${userAuthenticated.uid}")
            .child("$traningName/before")
            .downloadUrl.addOnSuccessListener { uri ->
                trainingRef.update(
                    "imageBefore",
                    uri.toString()
                ).addOnSuccessListener {
                    Toast.makeText(context, "Foto salva com sucesso", Toast.LENGTH_SHORT).show()
                }
            }

    }


    private fun savePicturesOnStorage(
        userAuthenticated: FirebaseUser,
        traningName: String,
        uriBefore: Uri,
        uriAfter: Uri,
        context: Context
    ) {


        val ref = Firebase.getStorage().reference
            .child("images/${userAuthenticated.uid}")
            .child(traningName)

        ref
            .child("before")
            .putFile(uriBefore)
            .addOnSuccessListener {
                updateTraningDataUriOnFirestore(userAuthenticated, traningName, context)
            }

        ref
            .child("after")
            .putFile(uriAfter)
            .addOnSuccessListener {
                updateTraningDataUriOnFirestore(userAuthenticated, traningName, context)
            }
    }


}