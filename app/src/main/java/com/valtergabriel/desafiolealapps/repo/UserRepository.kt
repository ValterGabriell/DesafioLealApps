package com.valtergabriel.desafiolealapps.repo

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseUser
import com.valtergabriel.desafiolealapps.dto.User
import com.valtergabriel.desafiolealapps.ui.FeedActivity
import com.valtergabriel.desafiolealapps.util.Constants.COLLECTION_USER_NAME
import com.valtergabriel.desafiolealapps.util.Constants.PASSWORD_HANDLE
import com.valtergabriel.desafiolealapps.util.Firebase
import com.valtergabriel.desafiolealapps.util.PasswordEcrypt
import java.time.LocalDateTime

class UserRepository {


    suspend fun createNewUser(userToBeCreated: User, context: Context) {
        val password = PasswordEcrypt.encrypt(userToBeCreated.password)
        val email = userToBeCreated.email

        Firebase.getAuth().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userAuthenticated = Firebase.getAuth().currentUser
                    val userData = createUserData(userToBeCreated, userAuthenticated, password)

                    Firebase.getFirestore().collection(COLLECTION_USER_NAME)
                        .document(userAuthenticated?.uid.toString())
                        .set(userData).addOnCompleteListener {
                            Intent(context, FeedActivity::class.java).also {
                                context.startActivity(it)
                            }
                        }
                }
            }.addOnFailureListener {
                Toast.makeText(context, "Falha ao criar usuário", Toast.LENGTH_SHORT).show()
            }
    }

    private fun createUserData(
        user: User,
        userAuthenticated: FirebaseUser?,
        passwordEcrypted:String
    ): HashMap<String, String?> {
        return hashMapOf(
            "email" to userAuthenticated?.email,
            "id" to userAuthenticated?.uid,
            "creationDate" to LocalDateTime.now().toString(),
            "age" to user.age,
            "weight" to user.weight,
            "expectedWeight" to user.expectedWeight,
            "height" to user.height,
            "password" to passwordEcrypted
        )
    }

    suspend fun signInUser(
        email: String,
        password: String,
        context: Context,
        btn: Button,
        progressBar: ProgressBar
    ) {
        val passwordEncrypted = PasswordEcrypt.encrypt(password)
        Firebase.getAuth().signInWithEmailAndPassword(email, passwordEncrypted)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Intent(context, FeedActivity::class.java).also {
                        context.startActivity(it)
                    }
                }
            }.addOnFailureListener {
                Toast.makeText(
                    context,
                    "Falha ao logar usuário, tente novamente",
                    Toast.LENGTH_SHORT
                ).show()
                btn.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
            }
    }


}