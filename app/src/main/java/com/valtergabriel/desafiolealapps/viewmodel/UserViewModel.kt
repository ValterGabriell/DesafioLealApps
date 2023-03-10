package com.valtergabriel.desafiolealapps.viewmodel

import android.content.Context
import android.widget.Button
import android.widget.ProgressBar
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valtergabriel.desafiolealapps.dto.User
import com.valtergabriel.desafiolealapps.repo.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(private val userRepository: UserRepository) : ViewModel() {

   fun signUpUser(user: User, context:Context) {
       viewModelScope.launch {
           userRepository.createNewUser(user, context)
       }
    }

    fun signInUser(email:String, password:String, context:Context, btn:Button, progress:ProgressBar) {
        viewModelScope.launch {
            userRepository.signInUser(email, password, context, btn, progress)
        }
    }


}