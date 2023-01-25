package com.valtergabriel.desafiolealapps.repo

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.valtergabriel.desafiolealapps.dto.User
import com.valtergabriel.desafiolealapps.ui.FeedActivity
import com.valtergabriel.desafiolealapps.util.Constants.COLLECTION_USER_NAME
import com.valtergabriel.desafiolealapps.util.Firebase
import java.time.LocalDateTime

class UserRepository {


    suspend fun createNewUser(user: User, context: Context) {
        val pass = user.password + "THE PASS"
        Firebase.getAuth().createUserWithEmailAndPassword(user.email, pass)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userAuthenticated = Firebase.getAuth().currentUser

                    val userData = hashMapOf(
                        "email" to userAuthenticated?.email,
                        "id" to userAuthenticated?.uid,
                        "creationDate" to LocalDateTime.now().toString(),
                        "age" to user.age,
                        "weight" to user.weight,
                        "expectedWeight" to user.expectedWeight,
                        "height" to user.height
                    )

                    Firebase.getFirestore().collection(COLLECTION_USER_NAME)
                        .document(userAuthenticated?.uid.toString())
                        .set(userData).also {
                            Intent(context, FeedActivity::class.java).also {
                                context.startActivity(it)
                            }
                        }

                }
            }.addOnFailureListener {
                Toast.makeText(context, "Falha ao criar usuário", Toast.LENGTH_SHORT).show()
            }
    }

    suspend fun signInUser(email:String, password:String, context: Context) {
        val pass = password + "THE PASS"
        Firebase.getAuth().signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Intent(context, FeedActivity::class.java).also {
                        context.startActivity(it)
                    }
                }
            }.addOnFailureListener {
                Toast.makeText(context, "Falha ao logar usuário", Toast.LENGTH_SHORT).show()
            }
    }




}