package com.tejashree.codereviewlab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.tejashree.codereviewlab.features.counter.SimpleCounter
import com.tejashree.codereviewlab.ui.theme.CodeReviewLabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CodeReviewLabTheme {
                SimpleCounter()
            }
        }
    }
}