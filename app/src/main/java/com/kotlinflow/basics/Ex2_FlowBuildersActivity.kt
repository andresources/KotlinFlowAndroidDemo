package com.kotlinflow.basics

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.FileUtils
import android.view.View
import android.widget.TextView
import com.kotlinflow.R
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

/*
There are 4 types of flow builders: are used to create flow from given data source
    1. flowOf(): It is used to create flow from a given set of items.
    2. asFlow(): It is an extension function that helps to convert type into flows.
    3. flow{} :
    4. channelFlow : This builder creates flow with the elements using send provided by the builder itself.

    flowOn() -> subscribeOn() in RxJava
 */

class Ex2_FlowBuildersActivity : AppCompatActivity() {
    private lateinit var tv: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ex2_flow_builders)
        tv = findViewById(R.id.tv)
    }

    fun funflowOf(view: View) {
        GlobalScope.launch {
            val myArr = arrayListOf<Int>(1,2,3,4,5)
            flowOf(1,2,3,4,5).filter { it % 2 == 0 }.collect{
                delay(1000)
                withContext(Dispatchers.Main){//To update ui on main ui thread
                    tv.text = it.toString()
                }
            }
        }
    }

    fun funasFlow(view: View) {
        GlobalScope.launch(Dispatchers.Main) {
            var list = mutableListOf<String>("a","b","c")
            list.asFlow()
                .map { it.toUpperCase() }
                .collect{
                    delay(1000)
                    tv.text = it.toString()
                }
        }
    }

    fun funFlow(view: View) {
        GlobalScope.launch(Dispatchers.Main) {
            flow{
                (1..5).forEach {
                    emit(it)
                }
            }.collect{
                delay(1000)
                tv.text = it.toString()
            }
        }
    }

    fun funChannelFlow(view: View) {
        GlobalScope.launch(Dispatchers.Main) {
            channelFlow {
                (1..5).forEach {
                    send(it)
                }
            }.collect {
                delay(1000)
                tv.text = it.toString()
            }
        }
    }

    fun funChannelFlowflowOn(view: View) {
        var cFlow=channelFlow {
            (1..5).forEach {
                send(it)
            }
        }.flowOn(Dispatchers.IO)

        GlobalScope.launch(Dispatchers.Main) {
            cFlow.collect{
                delay(1000)
                tv.text = it.toString()
            }
        }
    }

    fun funRealtimeExamples(view: View) {
        //1. Move File from one location to another location
        var fileMoveFlow = flow{
            //FileUtils.move(source, destination)
            emit("Done")
        }.flowOn(Dispatchers.IO)
        GlobalScope.launch {
            fileMoveFlow.collect{
                //Ui Update here
            }
        }

        //2. Downloading an Image
        val downloadImageflow = flow {
            // start downloading
            // send progress
            emit(10)
            // downloading...
            // ......
            // send progress
            emit(75)
            // downloading...
            // ......
            // send progress
            emit(100)
        }.flowOn(Dispatchers.IO)

        CoroutineScope(Dispatchers.Main).launch {
            downloadImageflow.collect {
                // we will get the progress here
            }
        }
    }
}