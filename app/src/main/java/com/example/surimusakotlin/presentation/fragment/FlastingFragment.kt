package com.example.surimusakotlin.presentation.fragment

import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.surimusakotlin.R
import com.example.surimusakotlin.databinding.FlastingFragmentBinding
import com.example.surimusakotlin.domain.DataHelper
import com.mikhaellopez.circularprogressbar.CircularProgressBar
import java.util.Date
import java.util.Timer
import java.util.TimerTask


class FlastingFragment : Fragment() {
    private lateinit var binding: FlastingFragmentBinding
    private lateinit var dataHelper: DataHelper
    private var fastingTimer: CountDownTimer? = null
    private lateinit var circularProgressBar: CircularProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FlastingFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataHelper = DataHelper(requireContext().applicationContext)
        circularProgressBar = binding.circularProgressBarTimer

        if (dataHelper.timerCounting()) {
            val elapsedTime = dataHelper.elapsedTime()
            val remainingTime = if (dataHelper.getFastingPeriod() - elapsedTime > 0) dataHelper.getFastingPeriod() - elapsedTime else 0
            if (remainingTime > 0) {
                startTimer(remainingTime, true)
            } else {
                val eatingTimeLeft = dataHelper.getEatingPeriod() - (elapsedTime - dataHelper.getFastingPeriod())
                if (eatingTimeLeft > 0) {
                    startTimer(eatingTimeLeft, false)
                }
            }
        }

        binding.buttonOnTracker.setOnClickListener {
            startFlastingPeriod()
        }

        binding.buttonStopFlasting.setOnClickListener {
            startEatingPeriod()
        }

        binding.buttonStartFlasting.setOnClickListener {
            continueFastingPeriod()
        }

        binding.buttonStopAllProcess.setOnClickListener {
            stopAllTimers()
        }

        updateUI()
    }

    private fun startFlastingPeriod() {
        dataHelper.setStartTime(Date())
        dataHelper.setTimerCounting(true)
        startTimer(dataHelper.getFastingPeriod(), true)
        binding.buttonStopFlasting.visibility = View.VISIBLE
        binding.buttonStartFlasting.visibility = View.GONE
        binding.buttonOnTracker.visibility = View.GONE
        binding.buttonStopAllProcess.visibility = View.VISIBLE
        binding.trackerStatusText.text = getString(R.string.you_are_starving)
    }

    private fun startEatingPeriod() {
        dataHelper.setStopTime(Date())
        dataHelper.setTimerCounting(true)
        startTimer(dataHelper.getEatingPeriod(), false)
        binding.buttonStartFlasting.visibility = View.VISIBLE
        binding.buttonStopFlasting.visibility = View.GONE
        binding.buttonOnTracker.visibility = View.GONE
        binding.buttonStopAllProcess.visibility = View.VISIBLE
        binding.trackerStatusText.text = getString(R.string.you_can_eat)
    }

    private fun continueFastingPeriod() {
        dataHelper.setStartTime(Date())
        dataHelper.setTimerCounting(true)
        startTimer(dataHelper.getFastingPeriod(), true)
        binding.buttonStopFlasting.visibility = View.VISIBLE
        binding.buttonStartFlasting.visibility = View.GONE
        binding.buttonOnTracker.visibility = View.GONE
        binding.buttonStopAllProcess.visibility = View.VISIBLE
        binding.trackerStatusText.text = getString(R.string.you_are_starving)
    }

    private fun stopAllTimers() {
        fastingTimer?.cancel()
        dataHelper.setTimerCounting(false)
        updateUI()
        val currentProgress = circularProgressBar.progress
        circularProgressBar.setProgressWithAnimation(1f, 1000)
    }

    private fun updateUI() {
        if (!dataHelper.timerCounting()) {
            binding.buttonOnTracker.visibility = View.VISIBLE
            binding.buttonStopFlasting.visibility = View.GONE
            binding.buttonStartFlasting.visibility = View.GONE
            binding.buttonStopAllProcess.visibility = View.GONE
            binding.trackerStatusText.text = getString(R.string.customize_yourself)
        } else {
            binding.buttonOnTracker.visibility = View.GONE
            binding.buttonStopAllProcess.visibility = View.VISIBLE
        }

        binding.lastTime.visibility = if (dataHelper.timerCounting()) View.VISIBLE else View.GONE
        binding.yourTrackerText.visibility = if (!dataHelper.timerCounting()) View.VISIBLE else View.GONE
        binding.variant168.visibility = if (!dataHelper.timerCounting()) View.VISIBLE else View.GONE
    }

    private fun startTimer(duration: Long, isFasting: Boolean) {
        fastingTimer?.cancel() // Отменяем любой существующий таймер
        val totalDuration = if (isFasting) dataHelper.getFastingPeriod() else dataHelper.getEatingPeriod()

        fastingTimer = object : CountDownTimer(duration, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val timeString = timeStringFromLong(millisUntilFinished)
                binding.lastTime.text = timeString
                val progress = 100 * (totalDuration - millisUntilFinished).toFloat() / totalDuration
                circularProgressBar.setProgressWithAnimation(progress, 1000)
            }

            override fun onFinish() {
                if (isFasting) {
                    startEatingPeriod()
                } else {
                    continueFastingPeriod()
                }
            }
        }.start()
        updateUI()
    }


    private fun timeStringFromLong(ms: Long): String {
        val seconds = (ms / 1000) % 60
        val minutes = ((ms / 1000) / 60) % 60
        val hours = ((ms / 1000) / 3600) % 24
        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }

}