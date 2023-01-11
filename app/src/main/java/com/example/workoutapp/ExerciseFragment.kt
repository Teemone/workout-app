package com.example.workoutapp

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.workoutapp.databinding.FragmentExerciseBinding
import com.google.android.material.snackbar.Snackbar


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ExerciseFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentExerciseBinding? = null
    private val binding get() = _binding!!
    private var cdTimer: CountDownTimer? = null
    private var progress = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
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

        binding.toolBar2.setNavigationIcon(R.drawable.ic_back)
        binding.toolBar2.navigationIcon
        binding.toolBar2.setNavigationOnClickListener {
            val toHomeFragment =
                ExerciseFragmentDirections.actionExerciseFragmentToHomeFragment()
            view.findNavController().navigate(toHomeFragment)
        }

        countDownTimer()

    }

    private fun countDownTimer(){
        binding.pbCountdown.progress = 0
        binding.pbCountdown.visibility = View.VISIBLE
        cdTimer = object: CountDownTimer(10000, 1000){
            override fun onTick(millisUntilFinished: Long) {
                progress++
                binding.tvCounter.text = "${10-progress}"
                binding.pbCountdown.progress = 10 - progress
            }

            override fun onFinish() {
                binding.pbCountdown.visibility = View.INVISIBLE
                Snackbar.make(binding.flCountdown, "Completed", Snackbar.LENGTH_SHORT).show()
            }

        }.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (cdTimer != null)
            cdTimer?.cancel()
        progress = 0
        _binding = null
    }

}