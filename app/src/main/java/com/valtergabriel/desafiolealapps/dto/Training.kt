package com.valtergabriel.desafiolealapps.dto

data class Training(
    val name:Long,
    val title:String,
    val staticTitle:String,
    val exercise: Exercises?,
    val dateTime: String,
    val desc :String
)