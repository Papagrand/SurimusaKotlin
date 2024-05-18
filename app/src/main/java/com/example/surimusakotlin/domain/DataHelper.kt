package com.example.surimusakotlin.domain

import android.content.Context
import android.content.SharedPreferences
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DataHelper(context: Context) {
    private var sharedPref: SharedPreferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)
    private var dateFormat = SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.getDefault())

    private var timerCounting = false
    private var startTime: Date? = null
    private var stopTime: Date? = null
    private var fastingPeriod: Long = 0
    private var eatingPeriod: Long = 0
    private var isFasting = true

    init {
        timerCounting = sharedPref.getBoolean(COUNTING_KEY, false)
        fastingPeriod = sharedPref.getLong(FASTING_PERIOD_KEY, 57600)
        eatingPeriod = sharedPref.getLong(EATING_PERIOD_KEY, 28800)
        isFasting = sharedPref.getBoolean(IS_FASTING_KEY, true)

        val startString = sharedPref.getString(START_TIME_KEY, null)
        startTime = startString?.let { dateFormat.parse(it) }
        val stopString = sharedPref.getString(STOP_TIME_KEY, null)
        stopTime = stopString?.let { dateFormat.parse(it) }
    }

    fun startTime(): Date? = startTime

    fun setStartTime(date: Date?) {
        startTime = date
        with(sharedPref.edit()) {
            putString(START_TIME_KEY, date?.let { dateFormat.format(it) })
            apply()
        }
    }

    fun elapsedTime(): Long {
        startTime?.let {
            val now = Date().time
            val start = it.time
            return now - start
        }
        return 0
    }

    fun stopTime(): Date? = stopTime

    fun setStopTime(date: Date?) {
        stopTime = date
        with(sharedPref.edit()) {
            putString(STOP_TIME_KEY, date?.let { dateFormat.format(it) })
            apply()
        }
    }

    fun timerCounting(): Boolean = timerCounting

    fun setTimerCounting(value: Boolean) {
        timerCounting = value
        with(sharedPref.edit()) {
            putBoolean(COUNTING_KEY, value)
            apply()
        }
    }

    fun getFastingPeriod(): Long = fastingPeriod

    fun setFastingPeriod(periodMillis: Long) {
        fastingPeriod = periodMillis
        with(sharedPref.edit()) {
            putLong(FASTING_PERIOD_KEY, periodMillis)
            apply()
        }
    }

    fun getEatingPeriod(): Long = eatingPeriod

    fun setEatingPeriod(periodMillis: Long) {
        eatingPeriod = periodMillis
        with(sharedPref.edit()) {
            putLong(EATING_PERIOD_KEY, periodMillis)
            apply()
        }
    }

    companion object {
        const val PREFERENCES = "prefs"
        const val START_TIME_KEY = "startKey"
        const val STOP_TIME_KEY = "stopKey"
        const val COUNTING_KEY = "countingKey"
        const val FASTING_PERIOD_KEY = "fastingPeriodKey"
        const val EATING_PERIOD_KEY = "eatingPeriodKey"
        const val IS_FASTING_KEY = "isFastingKey"
    }
}