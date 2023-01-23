package com.example.workoutapp

data class Exercises(
    val id: Int,
    val name: String,
    val image: Int,
    val duration: Long = Constants.DEFAULT_DURATION,
    var isCompleted: Boolean = false,
    var isSelected: Boolean = false
)
