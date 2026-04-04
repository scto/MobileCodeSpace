package com.mobilecodespace.feature.terminal

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.mobilecodespace.core.data.proot.PRootManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.InputStream
import java.io.OutputStream

class TerminalViewModel(application: Application) : AndroidViewModel(application) {

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

    fun sendInput(input: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                outputStream?.write(input.toByteArray())
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
