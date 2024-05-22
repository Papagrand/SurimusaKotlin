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
    private var onStopTime: Long = 0
    private var elapsedTimeSum: Long = 0

    init {
        timerCounting = sharedPref.getBoolean(COUNTING_KEY, false)
        fastingPeriod = sharedPref.getLong(FASTING_PERIOD_KEY, 57600000)
        eatingPeriod = sharedPref.getLong(EATING_PERIOD_KEY, 28800000)
        isFasting = sharedPref.getBoolean(IS_FASTING_KEY, true)
        elapsedTimeSum = sharedPref.getLong(ELAPSED_TIME, 2)

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

    fun getElapsedTime(): Long{
        if (isFasting){
            return elapsedTimeSum
        }
        else{
            return elapsedTimeSum
        }
    }
    fun dropElapsedTime(){
        elapsedTimeSum = 2
        with(sharedPref.edit()) {
            putLong(ELAPSED_TIME, 2 )
            apply()
        }
    }

    fun setElapsedTime(): Long{
        if (isFasting){
            startTime?.let {
                val now = Date().time
                val start = it.time
                elapsedTimeSum += now - start
                sharedPref.edit().putLong(ELAPSED_TIME, elapsedTimeSum).apply()
            }
        }
        else{
            stopTime?.let {
                val now = Date().time
                val start = it.time
                elapsedTimeSum += now - start
                sharedPref.edit().putLong(ELAPSED_TIME, elapsedTimeSum).apply()
            }
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

    fun setIsFasting(flag: Boolean){
        isFasting = flag
        with(sharedPref.edit()){
            putBoolean(IS_FASTING_KEY,isFasting)
            apply()
        }
    }

    fun getIsFasting(): Boolean = isFasting

    fun setOnStopTime(date: Long){
        onStopTime = date
        with(sharedPref.edit()){
            putLong(ON_STOP_TIME, onStopTime)
            apply()
        }
    }
    fun getOnStopTime(): Long? {
        return sharedPref.getLong(ON_STOP_TIME, onStopTime)
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
        const val ELAPSED_TIME = "elapsedTime"
        const val ON_STOP_TIME = "onStopTime"
        const val PREFERENCES = "prefs"
        const val START_TIME_KEY = "startKey"
        const val STOP_TIME_KEY = "stopKey"
        const val COUNTING_KEY = "countingKey"
        const val FASTING_PERIOD_KEY = "fastingPeriodKey"
        const val EATING_PERIOD_KEY = "eatingPeriodKey"
        const val IS_FASTING_KEY = "isFastingKey"
    }
}