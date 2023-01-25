package com.example.workoutapp

import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.workoutapp.databinding.FragmentBmiBinding
import kotlin.math.pow

class BmiFragment : Fragment() {
    private var _binding: FragmentBmiBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        requireActivity().onBackPressed()
//            .onBackPressedDispatcher
//            .addCallback(this, object: OnBackPressedCallback(false){
//                override fun handleOnBackPressed() {
//                }
//            })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBmiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationIcon(
            R.drawable.ic_back
        )
        binding.toolbar.setNavigationOnClickListener {
//            findNavController().navigate(
//                BmiFragmentDirections.actionBmiFragmentToHomeFragment()
//            )
            requireActivity().onBackPressed()
        }

        binding.btnCalculate.setOnClickListener {
            calculateBmi()
//            val toExerciseFragment =
//                HomeFragmentDirections.actionHomeFragmentToExerciseFragment()
//            view.findNavController().navigate(toExerciseFragment)
        }
    }

    private fun calculateBmi() {
        val h = binding.etHeight.text.toString().toFloat()
        val w = binding.etWeight.text.toString().toFloat()
        val hToMeters = h/100f
        val bmi = w.toDouble() / (hToMeters.toDouble().pow(2.0))

        val span = SpannableString(getString(R.string.your_bmi_is, bmi))
        span.setSpan(
            StyleSpan(Typeface.BOLD),
            5,
            8,
            Spanned.SPAN_INCLUSIVE_EXCLUSIVE
        )

        span.setSpan(
            StyleSpan(Typeface.BOLD),
            12,
            span.length,
            Spanned.SPAN_INCLUSIVE_EXCLUSIVE
        )

        binding.tvYourBmi.visibility = View.VISIBLE
        binding.tvYourBmi.text = span
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}