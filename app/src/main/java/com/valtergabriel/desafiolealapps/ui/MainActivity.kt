package com.valtergabriel.desafiolealapps.ui

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.google.firebase.FirebaseApp
import com.valtergabriel.desafiolealapps.databinding.ActivityMainBinding

import com.valtergabriel.desafiolealapps.util.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        FirebaseApp.initializeApp(this)

        val signUpBtn = binding.btnSignUp
        val signInBtn = binding.btnSignIn
        /**
         * Verificar se existe algum usuário logado para transferir de activity caso nao seja nulo.
         */
        if (Firebase.getAuth().currentUser != null){
            Intent(this, FeedActivity::class.java).also {
                startActivity(it)
            }
        }



        signUpBtn.setOnClickListener {
            Intent(this, FormActivity::class.java).also {
                startActivity(it)
            }
        }

        signInBtn.setOnClickListener {
            Intent(this, LoginActivity::class.java).also {
                startActivity(it)
            }
        }

    }


}