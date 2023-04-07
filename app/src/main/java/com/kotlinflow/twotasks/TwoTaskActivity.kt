package com.kotlinflow.twotasks

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.kotlinflow.R
import com.kotlinflow.UiState
import com.kotlinflow.singletask.SingleTaskViewModel
import kotlinx.coroutines.launch

class TwoTaskActivity : AppCompatActivity() {
    private lateinit var tv: TextView
    private lateinit var viewModel : TwoTaskViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_task)
        tv = findViewById(R.id.tv)
        viewModel = ViewModelProvider(this).get(TwoTaskViewModel::class.java)
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