package com.tejashree.codereviewlab.features.canvas

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun PreviewSimpleKaleidoscopeCanvas() {
    SimpleKaleidoscopeCanvas()
}

@Preview
@Composable
fun PreviewProfessionalKaleidoscopeCanvas() {
    ProfessionalKaleidoscopeCanvas(onBack = {})
}

@Composable
fun SimpleKaleidoscopeCanvas() {
    val transition = rememberInfiniteTransition(label = "kaleidoscope")

    val rotation by transition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(9000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    val pulse by transition.animateFloat(
        initialValue = 40f,
        targetValue = 90f,
        animationSpec = infiniteRepeatable(
            animation = tween(1600, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF050716))
    ) {
        val center = Offset(size.width / 2f, size.height / 2f)
        val slices = 12
        val angleStep = 360f / slices

        drawCircle(
            color = Color(0xFFFFD54F),
            radius = 24f,
            center = center
        )

        repeat(slices) { index ->
            rotate(
                degrees = rotation + index * angleStep,
                pivot = center
            ) {
                drawCircle(
                    color = Color(0xFF9D4DFF).copy(alpha = 0.65f),
                    radius = 18f,
                    center = Offset(center.x, center.y - pulse)
                )

                drawCircle(
                    color = Color(0xFFFF6D00).copy(alpha = 0.55f),
                    radius = 12f,
                    center = Offset(center.x + 35f, center.y - pulse * 1.4f)
                )

                drawLine(
                    color = Color(0xFF40C4FF).copy(alpha = 0.7f),
                    start = center,
                    end = Offset(center.x, center.y - pulse * 2f),
                    strokeWidth = 4f,
                    cap = StrokeCap.Round
                )
            }
        }
    }
}

@Composable
fun ProfessionalKaleidoscopeCanvas(onBack: () -> Unit) {
    val transition = rememberInfiniteTransition(label = "kaleidoscope")

    val rotation by transition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(14000, easing = LinearEasing)
        ),
        label = "rotation"
    )

    val pulse by transition.animateFloat(
        initialValue = 0.85f,
        targetValue = 1.18f,
        animationSpec = infiniteRepeatable(
            animation = tween(2200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    Box(modifier = Modifier.fillMaxSize()) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF030712))
        ) {
            val center = Offset(size.width / 2f, size.height / 2f)
            val maxRadius = minOf(size.width, size.height) * 0.38f
            val slices = 16
            val step = 360f / slices

            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0x3338BDF8),
                        Color(0x111E40AF),
                        Color.Transparent
                    ),
                    center = center,
                    radius = maxRadius * 1.5f
                ),
                radius = maxRadius * 1.5f,
                center = center
            )

            repeat(4) { ring ->
                drawCircle(
                    color = Color.White.copy(alpha = 0.08f),
                    radius = maxRadius * (0.35f + ring * 0.22f),
                    center = center,
                    style = Stroke(width = 1.5f)
                )
            }

            repeat(slices) { index ->
                rotate(rotation + index * step, center) {
                    val petalPath = Path().apply {
                        moveTo(center.x, center.y)
                        cubicTo(
                            center.x - 28f * pulse,
                            center.y - maxRadius * 0.35f,
                            center.x - 18f * pulse,
                            center.y - maxRadius * 0.72f,
                            center.x,
                            center.y - maxRadius
                        )
                        cubicTo(
                            center.x + 18f * pulse,
                            center.y - maxRadius * 0.72f,
                            center.x + 28f * pulse,
                            center.y - maxRadius * 0.35f,
                            center.x,
                            center.y
                        )
                        close()
                    }

                    drawPath(
                        path = petalPath,
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color(0xFF38BDF8).copy(alpha = 0.75f),
                                Color(0xFF8B5CF6).copy(alpha = 0.55f),
                                Color(0xFFFFD166).copy(alpha = 0.25f)
                            )
                        )
                    )

                    drawPath(
                        path = petalPath,
                        color = Color.White.copy(alpha = 0.18f),
                        style = Stroke(width = 1.2f)
                    )

                    drawCircle(
                        color = Color(0xFFFFD166).copy(alpha = 0.75f),
                        radius = 5f,
                        center = Offset(center.x, center.y - maxRadius * 0.65f * pulse)
                    )

                    drawLine(
                        color = Color(0xFF38BDF8).copy(alpha = 0.35f),
                        start = center,
                        end = Offset(center.x, center.y - maxRadius * 0.9f),
                        strokeWidth = 2f,
                        cap = StrokeCap.Round
                    )
                }
            }

            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color.White,
                        Color(0xFFFFD166),
                        Color(0xFFFF7A18),
                        Color.Transparent
                    ),
                    center = center,
                    radius = 46f
                ),
                radius = 46f,
                center = center
            )
        }
        IconButton(
            onClick = onBack,
            modifier = Modifier
                .padding(top = 32.dp, start = 16.dp)
                .align(Alignment.TopStart)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.White
            )
        }
    }
}

fun drawKaleidoscopeSymmetry(center: Offset, radius: Float, pulse: Float) {
    // This is just a dummy function to keep the original structure if needed.
    // In the rewrite I used the actual logic inside ProfessionalKaleidoscopeCanvas.
}
