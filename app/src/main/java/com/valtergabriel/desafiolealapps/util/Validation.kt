package com.valtergabriel.desafiolealapps.util

import android.util.Patterns

object Validation {

    fun isFilledField(text: String): Boolean {
        if (text.isNotEmpty()) {
            return true
        }
        return false
    }

    fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }



}