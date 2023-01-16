package com.example.workoutapp

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.workoutapp.databinding.FragmentExerciseBinding
import com.google.android.material.snackbar.Snackbar


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ExerciseFragment : Fragment() {
    private lateinit var set: ConstraintSet
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentExerciseBinding? = null
    private val binding get() = _binding!!
    private var cdTimer: CountDownTimer? = null
    private var mLayout: ConstraintLayout? = null
    private var exercises: ArrayList<Exercises>? = null
    private var currentPosition = 0
    private var duration: Long = 0
    private var progress = 0
    private var pbRestMax = 10
    private var durationToInt = 0
    private var pbMax = 0




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        set = ConstraintSet()
        exercises = Constants.returnExercises()



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExerciseBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mLayout = binding.cl1
        set.clone(mLayout)
        binding.toolBar2.setNavigationIcon(R.drawable.ic_back)
        binding.toolBar2.navigationIcon
        binding.toolBar2.setNavigationOnClickListener {
            val toHomeFragment =
                ExerciseFragmentDirections.actionExerciseFragmentToHomeFragment()
            view.findNavController().navigate(toHomeFragment)
        }
        binding.ivExercise.visibility = View.INVISIBLE
        restCountdownSetup()
    }

    private fun restCountdownSetup(){
        duration = exercises!![currentPosition].duration
        durationToInt = (duration/1000).toInt()
        pbMax = durationToInt

        binding.tvInstruction.text = getString(R.string.get_ready, exercises!![currentPosition].name).uppercase()
        changeCountdownPosition(false)
        binding.ivExercise.visibility = View.INVISIBLE
        restCountdown()
    }


    private fun exerciseCountdownSetup(){
        binding.ivExercise.visibility = View.VISIBLE
        binding.ivExercise.setImageResource(
            exercises!![currentPosition].image
        )
        binding.tvInstruction.text = exercises!![currentPosition].name.uppercase()

        changeCountdownPosition(true)
        exerciseCountdown()
    }

    private fun changeCountdownPosition(changePosition: Boolean) {
        if (changePosition) {
            set.connect(
                binding.flCountdown.id, ConstraintSet.TOP,
                binding.ivExercise.id, ConstraintSet.BOTTOM
            )
            set.setVerticalBias(binding.flCountdown.id, 0.90f)
        }
        else{
            set.connect(
                binding.flCountdown.id, ConstraintSet.TOP,
                binding.toolBar2.id, ConstraintSet.BOTTOM
            )
            set.setVerticalBias(binding.flCountdown.id, 0.50f)
            binding.ivExercise.visibility = View.INVISIBLE

        }
        set.applyTo(mLayout)
    }

    private fun restCountdown() {
        progress = 0
        binding.pbCountdown.max = pbRestMax
        binding.pbCountdown.visibility = View.VISIBLE
        cdTimer = object : CountDownTimer(Constants.DEFAULT_REST_COUNTDOWN, Constants.DEFAULT_COUNTDOWN_INTERVAL) {
            override fun onTick(millisUntilFinished: Long) {
                progress++
                binding.tvCounter.text = "${pbRestMax - progress}"
                binding.pbCountdown.progress = pbRestMax - progress
            }

            override fun onFinish() {
                exerciseCountdownSetup()
            }

        }.start()
    }

    private fun exerciseCountdown() {
        progress = 0
        binding.pbCountdown.max = durationToInt
        binding.pbCountdown.visibility = View.VISIBLE
        cdTimer = object : CountDownTimer(duration, Constants.DEFAULT_COUNTDOWN_INTERVAL) {
            override fun onTick(millisUntilFinished: Long) {
                progress++
                binding.tvCounter.text = "${durationToInt - progress}"
                binding.pbCountdown.progress = durationToInt - progress
            }

            override fun onFinish() {
                currentPosition++
                if (currentPosition < exercises!!.size){
                    restCountdownSetup()
                }else{
                    changeCountdownPosition(false)
                    binding.flCountdown.visibility = View.INVISIBLE
                    binding.ivExercise.visibility = View.INVISIBLE
                    binding.tvInstruction.text =
                        "Congratulations! You completed your daily workout"

                }
            }
        }.start()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        if (cdTimer != null)
            cdTimer?.cancel()
        progress = 0
        duration = 0
        _binding = null
    }

}