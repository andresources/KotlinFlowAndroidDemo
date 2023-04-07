package com.kotlinflow.singletask

import androidx.lifecycle.ViewModel

import com.kotlinflow.UiState
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class SingleTaskViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<String>>(UiState.Loading)
    val uiState: StateFlow<UiState<String>> = _uiState
    fun startLongRunningTask() {
        GlobalScope.launch(Dispatchers.Main) {
            _uiState.value = UiState.Loading
            doLongRunningTask()
            .catch {
                _uiState.value = UiState.Error("Something Went Wrong")
            }.collect{
                _uiState.value = UiState.Success("Completed","")
            }
        }
    }

    private fun doLongRunningTask() : Flow<Int> {
        return flow {
            // your code for doing a long running task
            // Added delay to simulate
            delay(5000)
            emit(0)
        }.flowOn(Dispatchers.Default)
    }
}