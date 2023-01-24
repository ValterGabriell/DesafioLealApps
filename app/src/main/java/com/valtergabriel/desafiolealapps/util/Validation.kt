package com.valtergabriel.desafiolealapps.util

import android.widget.EditText

object Validation {

    fun isEmptyField(text: String): Boolean {
        if (text.isNotEmpty()) {
            return false
        }
        return true
    }

}