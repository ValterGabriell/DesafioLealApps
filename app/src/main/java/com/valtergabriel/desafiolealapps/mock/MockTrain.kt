package com.valtergabriel.desafiolealapps.mock

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime

data class MockTrain(
    val id: Long = 0L, val name: String = "", val data: LocalDateTime? = null
) {
    @RequiresApi(Build.VERSION_CODES.O)
    fun createTraining() : List<MockTrain> {
        val list = ArrayList<MockTrain>()
        list.add(MockTrain(0, "treino", LocalDateTime.now()))
        list.add(MockTrain(1, "treino", LocalDateTime.now()))
        list.add(MockTrain(2, "treino", LocalDateTime.now()))
        list.add(MockTrain(3, "treino", LocalDateTime.now()))
        return list
    }
}

