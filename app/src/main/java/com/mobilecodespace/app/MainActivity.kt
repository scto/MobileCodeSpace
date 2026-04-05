package com.mobilecodespace.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import com.mobilecodespace.app.ui.MCSScreen
import com.mobilecodespace.core.ui.theme.MCSTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MCSTheme(darkTheme = isSystemInDarkTheme()) {
                MCSScreen()
            }
        }
    }
}
