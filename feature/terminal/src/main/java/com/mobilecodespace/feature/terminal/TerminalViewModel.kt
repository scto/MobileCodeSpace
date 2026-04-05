package com.mobilecodespace.feature.terminal

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobilecodespace.core.data.proot.PRootManager
import com.termux.terminal.TerminalSession
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TerminalViewModel @Inject constructor(
    application: Application
) : ViewModel() {

    private val prootManager = PRootManager(application)
    var session: TerminalSession? = null

    init {
        startTerminal()
    }

    private fun startTerminal() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                prootManager.installProotBinary()
                prootManager.setupRootfs()
                
                // Starten des PRoot-Prozesses
                val process = prootManager.startProot()
                
                // Initialisierung der TerminalSession
                session = TerminalSession(
                    process,
                    null, // cwd
                    null, // env
                    null  // client
                )
                
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun sendKey(key: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Sende Key an die Session
                val bytes = when (key) {
                    "ESC" -> byteArrayOf(0x1B.toByte())
                    "CTRL" -> byteArrayOf(0x03.toByte())
                    "ALT" -> byteArrayOf(0x1B.toByte())
                    "TAB" -> byteArrayOf(0x09.toByte())
                    else -> key.toByteArray()
                }
                session?.write(bytes, 0, bytes.size)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onCleared() {
        session?.finishIfRunning()
        super.onCleared()
    }
}
