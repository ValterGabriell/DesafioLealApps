package com.valtergabriel.desafiolealapps.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.valtergabriel.desafiolealapps.databinding.ActivityLoginBinding
import com.valtergabriel.desafiolealapps.util.Validation
import com.valtergabriel.desafiolealapps.viewmodel.UserViewModel
import org.koin.android.ext.android.inject

class LoginActivity : AppCompatActivity() {

    private val userViewModel by inject<UserViewModel>()
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val login = binding.btnSign
        val loading = binding.loading

        login.setOnClickListener {btnLogin ->
            val username = binding.username.text.toString()
            val password = binding.password.text.toString()
            if (Validation.isFilledField(username)
                && Validation.isFilledField(password)
                && Validation.isValidEmail(username)
            ) {
                btnLogin.visibility = View.GONE
                loading.visibility = View.VISIBLE
                userViewModel.signInUser(username, password, this, login, loading)
            } else {
                showLoginFailed("Preencha corretamente todos os campos")
            }

        }

    }

    private fun showLoginFailed(error: String) {
        Toast.makeText(applicationContext, error, Toast.LENGTH_SHORT).show()
    }
}
