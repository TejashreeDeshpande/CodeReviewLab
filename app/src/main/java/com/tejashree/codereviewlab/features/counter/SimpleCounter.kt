package com.tejashree.codereviewlab.features.counter

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview(showBackground = true)
@Composable
fun PreviewSimpleCounter() {
    SimpleCounter()
}

@Preview(showBackground = true)
@Composable
fun PreviewSimpleCounterContent() {
    SimpleCounterContent(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(200.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleCounter(modifier: Modifier = Modifier) {

    Scaffold(
        topBar = {
            SimpleCounterTopBar("Simple Counter")
        }
    ) { innerPadding ->
        SimpleCounterContent(
            modifier
                .padding(innerPadding)
                .fillMaxWidth()
                .padding(16.dp)
                .height(200.dp)
        )
    }
}

@Composable
fun SimpleCounterContent(
    modifier: Modifier = Modifier
) {
    var counter by rememberSaveable { mutableIntStateOf(0) }
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFEFF6FF),
                        Color(0xFF60A5FA),
                        Color(0xFF1D4ED8)
                    )
                )
            )
            .border(
                width = 1.dp,
                color = Color.White.copy(alpha = 0.35f),
                shape = RoundedCornerShape(12.dp)
            )
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = counter.toString(),
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.background
            )
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                CounterButton(
                    onClick = {
                        counter -= 1
                    }, buttonType = ButtonType.DECREMENT
                )

                CounterButton(
                    onClick = {
                        counter += 1
                    }, buttonType = ButtonType.INCREMENT
                )
            }
        }
    }
}

enum class ButtonType(val label: String) {
    INCREMENT(" + "), DECREMENT(" - ")
}

@Composable
fun CounterButton(
    onClick: () -> Unit, buttonType: ButtonType
) {
    ElevatedButton(
        colors = ButtonDefaults.buttonColors(
            containerColor = when (buttonType) {
                ButtonType.INCREMENT -> MaterialTheme.colorScheme.primary
                ButtonType.DECREMENT -> MaterialTheme.colorScheme.secondary
            },
            contentColor = when (buttonType) {
                ButtonType.INCREMENT -> MaterialTheme.colorScheme.onPrimary
                ButtonType.DECREMENT -> MaterialTheme.colorScheme.onSecondary
            },
        ), onClick = onClick
    ) {
        Text(
            buttonType.label,
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 26.sp
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleCounterTopBar(title: String = "Simple Counter") {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp
            )
        })
}