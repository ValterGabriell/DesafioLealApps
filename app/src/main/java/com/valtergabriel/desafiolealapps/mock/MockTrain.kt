package com.valtergabriel.desafiolealapps.mock

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime

data class MockTrain(
    val id: Long? = null, val description: String = "", val data: LocalDateTime? = null
) {
    @RequiresApi(Build.VERSION_CODES.O)
    fun createTraining() : List<MockTrain> {
        val list = ArrayList<MockTrain>()
        list.add(MockTrain(0, "loasldksa", LocalDateTime.now()))
        list.add(MockTrain(1, "s", LocalDateTime.now()))
        list.add(MockTrain(2, "www", LocalDateTime.now()))
        list.add(MockTrain(3, "qqq", LocalDateTime.now()))
        return list
    }
}

