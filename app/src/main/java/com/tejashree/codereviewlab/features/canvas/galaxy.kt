package com.tejashree.codereviewlab.features.canvas

import android.graphics.BlurMaskFilter
import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.math.*
import kotlin.random.Random


@Immutable
data class InkParticle(
    val id: Int,
    var position: Offset,
    var velocity: Offset,
    var radius: Float,
    var alpha: Float,
    val color: Color
)

@Preview
@Composable
fun PreviewFluidCanvasScreen() {
    FluidCanvasScreen()
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FluidCanvasScreen() {
    var selectedColor by remember { mutableStateOf(Color(0xFF8E24AA)) }
    var brushSize by remember { mutableFloatStateOf(42f) }
    var flow by remember { mutableFloatStateOf(1.2f) }
    var glow by remember { mutableFloatStateOf(26f) }
    var blur by remember { mutableFloatStateOf(18f) }

    val particles = remember { mutableStateListOf<InkParticle>() }

    fun spawnInk(position: Offset, strong: Boolean = false) {
        val count = if (strong) 42 else 12

        repeat(count) {
            val angle = Random.nextFloat() * Math.PI.toFloat() * 2f
            val speed = Random.nextFloat() * if (strong) 420f else 180f

            particles.add(
                InkParticle(
                    id = Random.nextInt(),
                    position = position,
                    velocity = Offset(
                        x = cos(angle) * speed,
                        y = sin(angle) * speed
                    ),
                    radius = Random.nextFloat() * brushSize + brushSize / 3f,
                    alpha = 1f,
                    color = selectedColor
                )
            )
        }
    }

    LaunchedEffect(flow) {
        var lastTime = 0L

        while (true) {
            withFrameNanos { time ->
                if (lastTime == 0L) {
                    lastTime = time
                    return@withFrameNanos
                }

                val dt = (time - lastTime) / 1_000_000_000f
                lastTime = time

                particles.forEach { particle ->
                    particle.position += particle.velocity * dt * flow

                    particle.velocity = Offset(
                        x = particle.velocity.x * 0.975f,
                        y = particle.velocity.y * 0.975f + 12f * dt
                    )

                    particle.radius *= 0.996f
                    particle.alpha -= 0.28f * dt
                }

                particles.removeAll {
                    it.alpha <= 0f || it.radius <= 1f
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF060712))
            .padding(16.dp)
    ) {
        val context =   LocalContext.current
        TopAppBar(
            title = { Text("FluidCanvas") },
            actions = {
                TextButton(onClick = {
                    Toast.makeText(context, "Exporting canvas...", Toast.LENGTH_SHORT).show()
                }) {
                        Text("Export")
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent,
                titleContentColor = Color.White
            )
        )

        Spacer(Modifier.height(12.dp))

        LiquidCanvas(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            particles = particles,
            glow = glow,
            blur = blur,
            onDraw = { offset -> spawnInk(offset) },
            onExplosion = { offset -> spawnInk(offset, strong = true) }
        )

        Spacer(Modifier.height(16.dp))

        ColorPalette(
            selectedColor = selectedColor,
            onColorSelected = { selectedColor = it }
        )

        ControlSlider("Brush", brushSize, 12f..90f) { brushSize = it }
        ControlSlider("Flow", flow, 0.4f..2.5f) { flow = it }
        ControlSlider("Glow", glow, 0f..60f) { glow = it }
        ControlSlider("Blur", blur, 0f..45f) { blur = it }

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(
                modifier = Modifier.weight(1f),
                onClick = {
                    spawnInk(
                        Offset(
                            x = Random.nextFloat() * 800f,
                            y = Random.nextFloat() * 900f
                        ),
                        strong = true
                    )
                }
            ) {
                Text("Splash")
            }

            OutlinedButton(
                modifier = Modifier.weight(1f),
                onClick = { particles.clear() }
            ) {
                Text("Reset")
            }
        }

        Spacer(Modifier.height(8.dp))

        Text(
            text = "Drag to paint liquid ink. Double tap for a glowing explosion.",
            color = Color.White.copy(alpha = 0.55f),
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Suppress("ParamsComparedByRef")
@Composable
fun LiquidCanvas(
    modifier: Modifier,
    particles: List<InkParticle>,
    glow: Float,
    blur: Float,
    onDraw: (Offset) -> Unit,
    onExplosion: (Offset) -> Unit
) {
    Canvas(
        modifier = modifier
            .background(
                Brush.radialGradient(
                    colors = listOf(
                        Color(0xFF161B3A),
                        Color(0xFF060712)
                    )
                )
            )
            .pointerInput(Unit) {
                detectTapGestures(
                    onDoubleTap = { offset ->
                        onExplosion(offset)
                    },
                    onTap = { offset ->
                        onDraw(offset)
                    },
                    onLongPress = { offset ->
                        onExplosion(offset)
                    }
                )
            }
            .pointerInput(Unit) {
                detectDragGestures { change, _ ->
                    onDraw(change.position)
                }
            }
    ) {
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    Color.White.copy(alpha = 0.05f),
                    Color.Transparent
                ),
                center = center,
                radius = size.maxDimension / 1.2f
            ),
            radius = size.maxDimension / 1.2f,
            center = center
        )

        particles.forEach { particle ->
            drawLiquidParticle(
                particle = particle,
                glow = glow,
                blur = blur
            )
        }
    }
}

fun androidx.compose.ui.graphics.drawscope.DrawScope.drawLiquidParticle(
    particle: InkParticle,
    glow: Float,
    blur: Float
) {
    drawIntoCanvas { canvas ->
        val paint = Paint().asFrameworkPaint().apply {
            isAntiAlias = true
            color = particle.color.copy(alpha = particle.alpha).toArgb()
            if (blur > 0f) {
                maskFilter = BlurMaskFilter(blur, BlurMaskFilter.Blur.NORMAL)
            }
        }

        canvas.nativeCanvas.drawCircle(
            particle.position.x,
            particle.position.y,
            particle.radius,
            paint
        )
    }

    if (glow > 0f) {
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    particle.color.copy(alpha = particle.alpha * 0.65f),
                    particle.color.copy(alpha = particle.alpha * 0.18f),
                    Color.Transparent
                ),
                center = particle.position,
                radius = particle.radius + glow
            ),
            radius = particle.radius + glow,
            center = particle.position
        )
    }

    drawCircle(
        color = Color.White.copy(alpha = particle.alpha * 0.18f),
        radius = particle.radius / 3f,
        center = particle.position + Offset(
            x = -particle.radius / 4f,
            y = -particle.radius / 4f
        )
    )
}

@Composable
fun ColorPalette(
    selectedColor: Color,
    onColorSelected: (Color) -> Unit
) {
    val colors = listOf(
        Color(0xFF8E24AA),
        Color(0xFF5E35B1),
        Color(0xFF1E88E5),
        Color(0xFF00BCD4),
        Color(0xFFFF4081),
        Color(0xFFFF9800),
        Color(0xFF00E676)
    )

    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        colors.forEach { color ->
            Surface(
                modifier = Modifier.size(if (color == selectedColor) 34.dp else 28.dp),
                shape = CircleShape,
                color = color,
                tonalElevation = if (color == selectedColor) 8.dp else 0.dp,
                onClick = { onColorSelected(color) }
            ) {}
        }
    }
}

@Composable
fun ControlSlider(
    title: String,
    value: Float,
    range: ClosedFloatingPointRange<Float>,
    onValueChange: (Float) -> Unit
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(title, color = Color.White)
            Text(
                text = value.toInt().toString(),
                color = Color.White.copy(alpha = 0.55f)
            )
        }

        Slider(
            value = value,
            valueRange = range,
            onValueChange = onValueChange
        )
    }
}