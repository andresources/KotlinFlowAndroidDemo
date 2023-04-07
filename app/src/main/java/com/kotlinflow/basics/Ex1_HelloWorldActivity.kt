package com.kotlinflow.basics

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.kotlinflow.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
/* Theory
  flow    -> Flow Builder
  map     -> Operator
  collect -> Collector
 */
class Ex1_HelloWorldActivity : AppCompatActivity() {
    private lateinit var tv:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basic)
        tv = findViewById(R.id.tv)
        GlobalScope.launch(Dispatchers.Main) {
            flow {
                (1..10).forEach {
                    kotlinx.coroutines.delay(500)
                    emit(it)
                }
            }
            .map { it * it }
            .collect{ tv.text = it.toString() }
        }
    }
}