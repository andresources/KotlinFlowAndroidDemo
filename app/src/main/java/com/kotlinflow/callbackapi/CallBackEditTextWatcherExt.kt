package com.kotlinflow.callbackapi

import android.text.Editable
import android.text.TextWatcher
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.TextView
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.debounce

fun TextView.getTextWatcherFlow() : Flow<CharSequence?>{
    return callbackFlow<CharSequence?>{
        val txtWatcher = object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                trySend(s)
            }

        }
        addTextChangedListener(txtWatcher)
        awaitClose {
            removeTextChangedListener(txtWatcher)
        }
    }.buffer(Channel.CONFLATED).debounce(300L)
}

fun CompoundButton.getCheckedFlow() : Flow<Boolean>{
    return callbackFlow {
        val chkList = object: CompoundButton.OnCheckedChangeListener{
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                trySend(isChecked)
            }
        }
        setOnCheckedChangeListener(chkList)
        awaitClose {

        }
    }
}