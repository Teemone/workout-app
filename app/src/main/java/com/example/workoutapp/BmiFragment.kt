package com.example.workoutapp

import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.example.workoutapp.databinding.FragmentBmiBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import kotlin.math.pow

class BmiFragment : Fragment() {
    private var _binding: FragmentBmiBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

        binding.rgSwitch.check(binding.rbMetric.id)

        binding.toolbar.setNavigationIcon(
            R.drawable.ic_back
        )
        binding.toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.btnCalculate.setOnClickListener {
            when(binding.rgSwitch.checkedRadioButtonId){
                binding.rbMetric.id -> {
                    when {
                        binding.etWeight.text.isNullOrEmpty() -> {
                            binding.tilWeight.isErrorEnabled = true
                            binding.tilWeight.error = "Can't be blank!"
                        }
                        binding.etHeight.text.isNullOrEmpty() -> {
                            binding.tilHeight.isErrorEnabled = true
                            binding.tilHeight.error = "Can't be blank!"
                        }
                        else -> {
                            calculateBmiInMetric()
                        }
                    }
                }
                else -> {
                    when{
                        binding.etWeight.text.isNullOrEmpty() -> {
                            binding.tilWeight.isErrorEnabled = true
                            binding.tilWeight.error = "Can't be blank!"
                        }
                        binding.etFeet.text.isNullOrEmpty() -> {
                            binding.tilFeat.isErrorEnabled = true
                            binding.tilFeat.error = "Can't be blank!"
                        }
                        else -> {
                            calculateBmiInImperial()
                        }

                    }

                }

            }

        }

        binding.etWeight.addTextChangedListener {
            binding.tilWeight.isErrorEnabled = false
        }
        binding.etHeight.addTextChangedListener {
            binding.tilHeight.isErrorEnabled = false
        }
        binding.etFeet.addTextChangedListener {
            binding.tilFeat.isErrorEnabled = false
        }


        binding.rgSwitch.setOnCheckedChangeListener {
                _, checkedId ->
            if (checkedId==binding.rbMetric.id){
                switchToImperial(false)
                Snackbar.make(binding.rbMetric, "METRIC UNIT", Snackbar.LENGTH_SHORT).show()
            }
            else{
                Snackbar.make(binding.rbMetric, "IMPERIAL UNIT", Snackbar.LENGTH_SHORT).show()
                switchToImperial()


            }
        }

    }

    private fun switchToImperial(toImperial: Boolean = true){
        if (toImperial){
            binding.llImperial.visibility = View.VISIBLE
            binding.tilHeight.visibility = View.INVISIBLE
        }
        else{
            binding.llImperial.visibility = View.INVISIBLE
            binding.tilHeight.visibility = View.VISIBLE
        }
    }

    private fun calculateBmiInMetric() {
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

        if (!binding.tvYourBmi.isVisible)
            binding.tvYourBmi.visibility = View.VISIBLE
        binding.tvYourBmi.text = span
    }

    private fun calculateBmiInImperial() {
        val w = binding.etWeight.text.toString().toFloat()
        val hFeet = binding.etFeet.text.toString().toFloat()
        val hInches = binding.etInches.text?.toString()?.toFloat() ?: 0
        val toInches = (hFeet * 12f)+hInches.toFloat()

        val bmi = (w.toDouble() / (toInches.toDouble().pow(2.0)))*703

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
        if (!binding.tvYourBmi.isVisible)
            binding.tvYourBmi.visibility = View.VISIBLE
        binding.tvYourBmi.text = span
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}