package com.example.team_16.ViewModel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*
import kotlin.concurrent.timer

@RequiresApi(Build.VERSION_CODES.O)
class StopwatchViewModel: ViewModel() {
    private var isRunning = MutableLiveData<Boolean>(false)
    private var _hour = MutableLiveData<Long>(0)
    private var _min = MutableLiveData<Long>(0)
    private var _sec = MutableLiveData<Long>(0)
    private var _mil = MutableLiveData<Long>(0)
    private var _timebuff = MutableLiveData<Long>(0)

    val hour: LiveData<Long> = _hour
    val min: LiveData<Long> = _min
    val sec: LiveData<Long> = _sec
    val mil: LiveData<Long> = _mil
    val timebuff: LiveData<Long> = _timebuff


    private var timer: Timer? = null


    fun timerStart() {
        if (isRunning.value == true) return
        isRunning.value = true
        timer = timer(period = 10) {
            if(midnight() == 1) timerStop()

            _timebuff.postValue(_timebuff.value?.inc())
            _sec.postValue(_timebuff.value?.div(100))
            _hour.postValue(_min.value?.div(60))
            _min.postValue(_sec.value?.div(60))
            _mil.postValue(_timebuff.value?.rem(100))

        }
    }

    fun timerStop() {
        isRunning.value = false
        timer?.cancel()
    }


    private fun midnight(): Int{
        val resetTime = Calendar.getInstance()
        val h = resetTime.get(Calendar.HOUR_OF_DAY)
        val m = resetTime.get(Calendar.MINUTE)
        val s = resetTime.get(Calendar.SECOND)
        val ms = resetTime.get(Calendar.MILLISECOND)

        if (h == 16 && m == 50 && s == 0 && ms == 0) { // 시간 지정
            _timebuff.postValue(0)
            _hour.postValue(0)
            _min.postValue(0)
            _sec.postValue(0)
            _timebuff.postValue(0) // reset data

            return 1
        }
        return 0
    }

}