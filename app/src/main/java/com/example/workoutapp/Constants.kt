package com.example.workoutapp

object Constants {
    const val DEFAULT_DURATION: Long = 30000
    const val DEFAULT_COUNTDOWN_INTERVAL: Long = 1000
    const val DEFAULT_REST_COUNTDOWN: Long = 10000

    fun returnExercises(): ArrayList<Exercises> {
        val exercises = ArrayList<Exercises>()
        val ex = arrayOf(
            Exercises(
                0,
                "Lunges",
                R.drawable.lunges
            ),
            Exercises(
                1,
                "pushups",
                R.drawable.pushups,
                duration = 20000
            ),
            Exercises(
                2,
                "squats",
                R.drawable.squats
            ),
            Exercises(
                3,
                "side planks",
                R.drawable.side_planks,
                duration = 20000
            ),
            Exercises(
                4,
                "planks",
                R.drawable.planks
            ),
            Exercises(
                5,
                "glute bridge",
                R.drawable.glute_bridge,
                duration = 40000
            ),
            Exercises(
                6,
                "jumping jack",
                R.drawable.jumping_jacks,
                duration = 45000
            ),
        )
        for (i in ex){
            exercises.add(i)
        }
        return exercises
    }
}