package com.kotlinflow.singletask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.kotlinflow.R
import com.kotlinflow.UiState
import kotlinx.coroutines.launch

class SingleTaskActivity : AppCompatActivity() {
    private lateinit var tv:TextView
    private lateinit var viewModel : SingleTaskViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_task)
        tv = findViewById(R.id.tv)
        viewModel = ViewModelProvider(this).get(SingleTaskViewModel::class.java)
        setupLongRunningTask()
    }

    private fun setupLongRunningTask() {
        lifecycleScope.launch {
            viewModel.uiState.collect{
                when(it){
                    is UiState.Loading -> {
                        tv.text = "Loading...."
                    }

                    is UiState.Success ->{
                        tv.text = it.data
                    }

                    is UiState.Error ->{
                        tv.text = it.message
                    }
                }
            }
        }
        viewModel.startLongRunningTask()
    }
}