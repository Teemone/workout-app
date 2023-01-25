package com.example.workoutapp

import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.workoutapp.databinding.FragmentExerciseBinding
import java.util.*
import kotlin.collections.ArrayList


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val pbRestMax = 10

class ExerciseFragment : Fragment(), TextToSpeech.OnInitListener {
    private lateinit var set: ConstraintSet
    private var textToSpeech: TextToSpeech? = null
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentExerciseBinding? = null
    private var cdTimer: CountDownTimer? = null
    private var mLayout: ConstraintLayout? = null
    private var exercises: ArrayList<Exercises>? = null
    private var mPlayer: MediaPlayer? = null
    private val binding get() = _binding!!
    private var currentPosition = 0
    private var duration: Long = 0
    private var progress = 0
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
    ): View {
        _binding = FragmentExerciseBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mLayout = binding.cl1
        set.clone(mLayout)
        binding.toolBar2.setNavigationIcon(R.drawable.ic_back)
        binding.toolBar2.setNavigationOnClickListener {
            val toHomeFragment =
                ExerciseFragmentDirections.actionExerciseFragmentToHomeFragment()
            view.findNavController().navigate(toHomeFragment)
//            if (mPlayer?.isPlaying == true)
//                stopSound()

        }
        binding.ivExercise.visibility = View.INVISIBLE
        textToSpeech = TextToSpeech(requireContext(), this)
        binding.rvExercises.adapter = ExerciseItemAdapter(exercises!!)

        restCountdownSetup()
    }


    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            textToSpeech?.language = Locale.ENGLISH
        } else
            Log.e("INIT", "INIT FAILED!!")
    }

    private fun speak(str: String) =
        textToSpeech?.speak(str, TextToSpeech.QUEUE_FLUSH, null, "")


    private fun playSound(soundRes: Int) {
        try {
            mPlayer = MediaPlayer.create(
                requireContext(),
                soundRes
            )
            mPlayer?.isLooping = false
            mPlayer?.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun stopSound() {
        mPlayer?.stop()
    }

    private fun restCountdownSetup() {
        val getReadyText = getString(R.string.get_ready, exercises!![currentPosition].name)
        binding.tvInstruction.text = getReadyText.uppercase()
//        speak(getReadyText)

        duration = exercises!![currentPosition].duration
        durationToInt = (duration / 1000).toInt()
        pbMax = durationToInt

        changeCountdownPosition(false)
        binding.ivExercise.visibility = View.INVISIBLE
        binding.rvExercises.visibility = View.GONE

        exercises!![currentPosition].isSelected = true
        binding.rvExercises.adapter = ExerciseItemAdapter(exercises!!)

        restCountdown()
    }


    private fun exerciseCountdownSetup() {
        val exerciseName = exercises!![currentPosition].name
        binding.ivExercise.visibility = View.VISIBLE
        binding.ivExercise.visibility = View.VISIBLE
        binding.ivExercise.setImageResource(
            exercises!![currentPosition].image
        )
        binding.tvInstruction.text = exerciseName.uppercase()
//        speak(exerciseName)
        changeCountdownPosition(true)
        binding.rvExercises.visibility = View.VISIBLE

        exerciseCountdown()
    }

    private fun restCountdown() {
        progress = 0
        binding.pbCountdown.max = pbRestMax
        binding.pbCountdown.visibility = View.VISIBLE
//        playSound(R.raw.tick_tok_timer)

        cdTimer = object :
            CountDownTimer(1000, Constants.DEFAULT_COUNTDOWN_INTERVAL) {
            override fun onTick(millisUntilFinished: Long) {
                progress++
                binding.tvCounter.text = "${pbRestMax - progress}"
                binding.pbCountdown.progress = pbRestMax - progress
            }

            override fun onFinish() {
//                stopSound()
                exerciseCountdownSetup()
            }

        }.start()
    }

    private fun exerciseCountdown() {
        progress = 0
        binding.pbCountdown.max = durationToInt
        binding.pbCountdown.visibility = View.VISIBLE
//        playSound(R.raw.start_sound)
        cdTimer = object : CountDownTimer(1000, Constants.DEFAULT_COUNTDOWN_INTERVAL) {
            override fun onTick(millisUntilFinished: Long) {
                progress++
                binding.tvCounter.text = "${durationToInt - progress}"
                binding.pbCountdown.progress = durationToInt - progress
            }

            override fun onFinish() {
                exercises!![currentPosition].isCompleted = true
                binding.rvExercises.adapter = ExerciseItemAdapter(exercises!!)
                currentPosition++
                if (currentPosition < exercises!!.size) {
                    restCountdownSetup()
                } else {
                    changeCountdownPosition(false)
                    binding.flCountdown.visibility = View.INVISIBLE
                    binding.ivExercise.visibility = View.INVISIBLE
                    binding.tvInstruction.text =
                        getString(R.string.completed_daily_workout)
//                    stopSound()

                }
            }
        }.start()
    }

    private fun changeCountdownPosition(changePosition: Boolean) {
        if (changePosition) {
            set.connect(
                binding.flCountdown.id, ConstraintSet.TOP,
                binding.ivExercise.id, ConstraintSet.BOTTOM
            )
            set.setVerticalBias(binding.flCountdown.id, 0.90f)
        } else {
            set.connect(
                binding.flCountdown.id, ConstraintSet.TOP,
                binding.toolBar2.id, ConstraintSet.BOTTOM
            )
            set.setVerticalBias(binding.flCountdown.id, 0.50f)
            binding.ivExercise.visibility = View.INVISIBLE

        }
        set.applyTo(mLayout)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (cdTimer != null)
            cdTimer?.cancel()
        progress = 0
        duration = 0
        textToSpeech?.stop()
        textToSpeech?.shutdown()
        mPlayer?.stop()
        mPlayer?.release()
        _binding = null
    }


}