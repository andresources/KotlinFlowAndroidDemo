package com.kotlinflow.callbackapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.kotlinflow.R

class CallBackEditTextActivity : AppCompatActivity() {
    private lateinit var etName:EditText
    private lateinit var checkBox:CheckBox
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_call_back_edit_text)
        etName = findViewById(R.id.etName)
        checkBox =findViewById(R.id.checkBox)
        val job1 =lifecycleScope.launchWhenResumed {
            etName.getTextWatcherFlow().collect{
                Toast.makeText(applicationContext,""+it,Toast.LENGTH_LONG).show()
            }
        }
        val job2 =lifecycleScope.launchWhenResumed {
            checkBox.getCheckedFlow().collect{
                Toast.makeText(applicationContext,""+it,Toast.LENGTH_LONG).show()
            }
        }
       // job1.cancel()
    }

    override fun onStart() {
        super.onStart()

    }
}