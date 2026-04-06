package com.mobilecodespace.feature.terminal

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobilecodespace.core.data.proot.PRootManager
import com.termux.terminal.TerminalSession
import com.termux.terminal.TerminalSessionClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TerminalViewModel @Inject constructor(
    application: Application
) : ViewModel(), TerminalSessionClient {

    private val prootManager = PRootManager(application)
    private val _session = MutableStateFlow<TerminalSession?>(null)
    val session: StateFlow<TerminalSession?> = _session

    init {
        startTerminal()
    }

    private fun startTerminal() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                prootManager.installProotBinary()
                prootManager.setupRootfs()
                
                val process = prootManager.startProot()
                
                val newSession = TerminalSession(
                    process,
                    null, // cwd
                    null, // env
                    this@TerminalViewModel
                )
                _session.value = newSession
                
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun sendKey(key: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val bytes = when (key) {
                    "ESC" -> byteArrayOf(0x1B.toByte())
                    "CTRL" -> byteArrayOf(0x03.toByte())
                    "ALT" -> byteArrayOf(0x1B.toByte())
                    "TAB" -> byteArrayOf(0x09.toByte())
                    else -> key.toByteArray()
                }
                _session.value?.write(bytes, 0, bytes.size)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onTextChanged(changedSession: TerminalSession?) {}
    override fun onTitleChanged(changedSession: TerminalSession?) {}
    override fun onSessionFinished(finishedSession: TerminalSession?) { _session.value = null }
    override fun onCopyTextToClipboard(text: String?) {}
    override fun onPasteTextFromClipboard() {}
    override fun onBell() {}
    override fun onColorsChanged() {}
    override fun getTerminalCursorStyle(): String = "block"
    override fun onTerminalCursorStateChange(state: Boolean) {}
    override fun onTerminalSizeChanged(newRows: Int, newCols: Int) {}
    override fun onTerminalAuthenticationFailure() {}
    override fun onTerminalIOError(error: Throwable?) {}
    override fun onTerminalReady() {}
    override fun onTerminalSessionClosed(session: TerminalSession?) {}
    override fun onTerminalSessionStarted(session: TerminalSession?) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?) {}
    override fun onTerminalSessionBell(session: TerminalSession?) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionInput(session: TerminalSession?, input: String?, length: Int) {}
    override fun onTerminalSessionResize(session: TerminalSession?, rows: Int, cols: Int, pixelWidth: Int, pixelHeight: Int) {}
    override fun onTerminalSessionTitleChanged(session: TerminalSession?, title: String?, length: Int) {}
    override fun onTerminalSessionBell(session: TerminalSession?, bellType: Int) {}
    override fun onTerminalSessionColorsChanged(session: TerminalSession?, colors: IntArray?) {}
    override fun onTerminalSessionCursorStyleChanged(session: TerminalSession?, style: Int) {}
    override fun onTerminalSessionCursorStateChanged(session: TerminalSession?, state: Boolean) {}
    override fun onTerminalSessionAuthenticationFailure(session: TerminalSession?, failureType: Int) {}
    override fun onTerminalSessionIOError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionReady(session: TerminalSession?, ready: Boolean) {}
    override fun onTerminalSessionClosed(session: TerminalSession?, exitCode: Int) {}
    override fun onTerminalSessionStarted(session: TerminalSession?, pid: Int) {}
    override fun onTerminalSessionUpdated(session: TerminalSession?, output: String?, length: Int) {}
    override fun onTerminalSessionError(session: TerminalSession?, error: Throwable?) {}
    override fun onTerminalSessionOutput(session: TerminalSession?, output: String?, length: Int) {}