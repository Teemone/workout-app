package com.example.workoutapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.workoutapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolBar.title = getString(R.string.app_name)
        binding.flButtonStart.setOnClickListener {
            val toExerciseFragment =
                HomeFragmentDirections.actionHomeFragmentToExerciseFragment()
            view.findNavController().navigate(toExerciseFragment)
        }

        binding.flButtonBMI.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToBmiFragment()
            )
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}