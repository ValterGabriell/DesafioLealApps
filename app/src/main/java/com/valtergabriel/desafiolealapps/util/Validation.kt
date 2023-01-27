package com.valtergabriel.desafiolealapps.util

import android.util.Patterns
import android.widget.EditText
import java.util.regex.Pattern

object Validation {

    fun isEmptyField(text: String): Boolean {
        if (text.isNotEmpty()) {
            return false
        }
        return true
    }

    fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

}