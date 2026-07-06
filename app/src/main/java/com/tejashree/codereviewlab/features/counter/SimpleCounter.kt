package com.tejashree.codereviewlab.features.counter

import android.content.res.Configuration
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
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tejashree.codereviewlab.features.common.AppTopBar
import com.tejashree.codereviewlab.ui.theme.CodeReviewLabTheme

@Preview(name = "Light Mode", showBackground = true)
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun PreviewSimpleCounterContent() {
    CodeReviewLabTheme {
        SimpleCounterContent(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(200.dp)
        )
    }
}

@Preview(name = "Light Mode", showBackground = true)
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun PreviewSimpleCounter() {
    CodeReviewLabTheme {
        SimpleCounter(onBack = {})
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleCounter(
    modifier: Modifier = Modifier,
    onBack: () -> Unit
) {

    Scaffold(
        topBar = {
            AppTopBar("Simple Counter", onBack = onBack)
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
                modifier = Modifier.semantics {
                    liveRegion = LiveRegionMode.Polite
                    contentDescription = "Current count is $counter"
                },
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

enum class ButtonType(
    val label: String,
    val contentDescription: String
) {
    INCREMENT(" + ", "Increment"),
    DECREMENT(" - ", "Decrement")
}

@Composable
fun CounterButton(
    onClick: () -> Unit,
    buttonType: ButtonType,
    modifier: Modifier = Modifier
) {
    ElevatedButton(
        modifier = modifier.clearAndSetSemantics {
            contentDescription = buttonType.contentDescription
            role = Role.Button
        },
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

