package com.mobilecodespace.feature.terminal

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobilecodespace.core.data.proot.PRootManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject

@HiltViewModel
class TerminalViewModel @Inject constructor(
    application: Application
) : ViewModel() {

    private val prootManager = PRootManager(application)
    private var process: Process? = null
    
    var inputStream: InputStream? = null
    var outputStream: OutputStream? = null

    init {
        startTerminal()
    }

    private fun startTerminal() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                prootManager.installProotBinary()
                prootManager.setupRootfs()
                process = prootManager.startProot()
                inputStream = process?.inputStream
                outputStream = process?.outputStream
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun sendKey(key: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                outputStream?.write(key.toByteArray())
                outputStream?.flush()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onCleared() {
        process?.destroy()
        super.onCleared()
    }
}
