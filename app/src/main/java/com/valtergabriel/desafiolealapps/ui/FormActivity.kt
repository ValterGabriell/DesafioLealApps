package com.valtergabriel.desafiolealapps.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.valtergabriel.desafiolealapps.databinding.ActivityFormBinding
import com.valtergabriel.desafiolealapps.dto.User
import com.valtergabriel.desafiolealapps.util.Validation.isFilledField
import com.valtergabriel.desafiolealapps.util.Validation.isValidEmail
import com.valtergabriel.desafiolealapps.viewmodel.UserViewModel
import org.koin.android.ext.android.inject

class FormActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFormBinding
    private val userViewModel by inject<UserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val btnSignUp = binding.btnSignUp2

        btnSignUp.setOnClickListener { buttonSignUp ->

            val email = binding.emailEditTextActivityForm.text.toString()
            val password = binding.passwordEditTextActivityForm.text.toString()
            val age = binding.ageEditTextActivityForm.text.toString()
            val height = binding.heightEditTextActivityForm.text.toString()
            val weight = binding.weightEditTextActivityForm.text.toString()
            val expectedWeight = binding.expetedWeightEditTextActivityForm.text.toString()

            if (isFilledField(email)
                && isFilledField(password)
                && isFilledField(age)
                && isFilledField(height)
                && isFilledField(weight)
                && isFilledField(expectedWeight)
                && isValidEmail(email)
            ) {
                buttonSignUp.visibility = View.GONE
                binding.progressBar.visibility = View.VISIBLE
                signUp(email, password, age, height, weight, expectedWeight)
            } else {
                Toast.makeText(this, "Preencha corretamente todos os campos", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    }


    private fun signUp(
        email: String,
        password: String,
        age: String,
        height: String,
        weight: String,
        expectedWeight: String
    ) {
        val user = User(
            email,
            password,
            age,
            height,
            weight,
            expectedWeight
        )
        userViewModel.signUpUser(user, this)
    }

}