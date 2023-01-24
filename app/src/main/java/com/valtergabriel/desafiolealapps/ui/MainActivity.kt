package com.valtergabriel.desafiolealapps.ui

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.google.firebase.FirebaseApp
import com.valtergabriel.desafiolealapps.databinding.ActivityMainBinding
import com.valtergabriel.desafiolealapps.mock.MockTrain
import com.valtergabriel.desafiolealapps.util.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        FirebaseApp.initializeApp(this)




        binding.btnSignUp.setOnClickListener {
            Intent(this, FormActivity::class.java).also {
                startActivity(it)
            }
        }

        binding.btnSignIn.setOnClickListener {
            Intent(this, LoginActivity::class.java).also {
                startActivity(it)
            }
        }

    }


}