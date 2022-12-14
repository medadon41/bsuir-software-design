package com.example.cupcake.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cupcake.database.Timer
import com.example.cupcake.database.TimerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class SequenceStage {
    Disabled,
    Preparation,
    Workout,
    Rest
}

@HiltViewModel
class TimerViewModel @Inject constructor(
    private val repository: TimerRepository
) : ViewModel() {
    //var timers by mutableStateOf(emptyList<Timer>())

    private val _timersState = MutableStateFlow(emptyList<Timer>())
    val timersState: StateFlow<List<Timer>> = _timersState.asStateFlow()

    private val _timerState = MutableStateFlow(Timer(0, "aaa", 0, 0, 0, 0))
    val timerState: StateFlow<Timer> = _timerState.asStateFlow()

    var currentStage by mutableStateOf(SequenceStage.Disabled)

    //var timer by mutableStateOf(Timer(0, "aaa", 0, 0, 0, 0))

    fun getTimers() {
        viewModelScope.launch {
            repository.getAllTimers().collect() {
                response -> _timersState.value = response
            }
        }
    }

    fun getTimer(id: Int) {
        viewModelScope.launch {
            repository.getTimer(id).collect() {
                    response -> _timerState.value = response?: Timer(0, "aaa", 0, 0, 0, 0)
            }
        }
    }

    fun addTimer(timer: Timer) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addTimer(timer)
        }
    }

    fun updateTimer(timer: Timer) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateTimer(timer)
        }
    }

    fun deleteTimer(timer: Timer) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTimer(timer)

        }
    }

    fun deleteAllTimers() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllTimers()
        }
    }

}
