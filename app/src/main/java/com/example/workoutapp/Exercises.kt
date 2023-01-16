package com.example.workoutapp

data class Exercises(
    val id: Int,
    val name: String,
    val image: Int,
    val duration: Long = Constants.DEFAULT_DURATION,
    val isCompleted: Boolean = false,
    val isSelected: Boolean = false
)
