package com.kotlinflow.twotasks

import androidx.lifecycle.ViewModel
import com.kotlinflow.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class TwoTaskViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<String>>(UiState.Loading)
    val uiState: StateFlow<UiState<String>> = _uiState
    fun startLongRunningTask(){
        GlobalScope.launch(Dispatchers.Main) {
            _uiState.value = UiState.Loading
            doLongRunningTaskOne().zip(doLongRunningTaskTwo()){
                    resultOne, resultTwo ->
                return@zip resultOne + resultTwo
            }.catch { e ->
                _uiState.value = UiState.Error(e.toString())
            }.collect {
                _uiState.value = UiState.Success(it,"")
            }
        }
    }
    private fun doLongRunningTaskOne() : Flow<String> {
        return flow {
            // your code for doing a long running task
            // Added delay to simulate
            delay(2000)
            emit("1")
            delay(2000)
            emit("2")
        }.flowOn(Dispatchers.Default)
    }
    private fun doLongRunningTaskTwo() : Flow<String> {
        return flow {
            // your code for doing a long running task
            // Added delay to simulate
            delay(5000)
            emit("A")
            delay(5000)
            emit("B")
        }.flowOn(Dispatchers.Default)
    }
}


