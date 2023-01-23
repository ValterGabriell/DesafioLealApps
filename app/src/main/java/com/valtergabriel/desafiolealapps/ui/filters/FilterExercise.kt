package com.valtergabriel.desafiolealapps.ui.filters

import android.content.Context
import android.view.LayoutInflater
import androidx.lifecycle.MutableLiveData
import com.google.android.material.chip.Chip
import com.valtergabriel.desafiolealapps.R
import com.valtergabriel.desafiolealapps.mock.MockExercise

data class FilterExercise(
    val id_filter: String
)

fun FilterExercise.toChip(context: Context): Chip {
    val chip = LayoutInflater.from(context).inflate(R.layout.chip_choice, null, false) as Chip
    chip.apply {
        when (id_filter) {
            R.string.abdomen.toString() -> {
               MockExercise().getExercicesForAbdomen()
            }
            R.string.get_fit.toString() -> {
                MockExercise().getExercicesForGetFit()
            }
            R.string.muscles.toString() -> {
                MockExercise().getExercicesForBiceps()
            }
        }
    }
    return chip
}