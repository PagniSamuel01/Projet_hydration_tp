package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.TealSecondary
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TurquoisePrimary

@Composable
fun CircularWaterProgress(
    progressFraction: Float,
    currentMl: Int,
    goalMl: Int,
    modifier: Modifier = Modifier,
    size: Dp = 260.dp,
    strokeWidth: Dp = 20.dp
) {
    val animatedProgress by animateFloatAsState(
        targetValue = progressFraction.coerceIn(0f, 1f),
        animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing),
        label = "water_progress_animation"
    )

    val percentage = (animatedProgress * 100).toInt()
    val isGoalCompleted = currentMl >= goalMl

    Box(
        modifier = modifier
            .size(size)
            .testTag("circular_progress_container"),
        contentAlignment = Alignment.Center
    ) {
        // Outer glow radial background
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .clip(CircleShape)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            TurquoisePrimary.copy(alpha = if (isGoalCompleted) 0.25f else 0.12f),
                            Color.Transparent
                        )
                    )
                )
        )

        // Custom Canvas for Track and Arc Progress
        val primaryColor = MaterialTheme.colorScheme.primary
        val secondaryColor = MaterialTheme.colorScheme.secondary
        val trackColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)

        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeWidthPx = strokeWidth.toPx()

            // Background Track Arc
            drawArc(
                color = trackColor,
                startAngle = 135f,
                sweepAngle = 270f,
                useCenter = false,
                style = Stroke(width = strokeWidthPx, cap = StrokeCap.Round)
            )

            // Foreground Progress Arc
            if (animatedProgress > 0f) {
                drawArc(
                    brush = Brush.sweepGradient(
                        colors = listOf(
                            primaryColor,
                            secondaryColor,
                            primaryColor
                        )
                    ),
                    startAngle = 135f,
                    sweepAngle = 270f * animatedProgress,
                    useCenter = false,
                    style = Stroke(width = strokeWidthPx, cap = StrokeCap.Round)
                )
            }
        }

        // Inner Information Content
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(24.dp)
        ) {
            Icon(
                imageVector = Icons.Default.WaterDrop,
                contentDescription = "Water drop",
                tint = if (isGoalCompleted) TealSecondary else TurquoisePrimary,
                modifier = Modifier.size(36.dp)
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "$percentage %",
                style = MaterialTheme.typography.displayMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 42.sp
                ),
                color = TextPrimary
            )

            Spacer(modifier = Modifier.height(2.dp))

            val currentL = String.format(java.util.Locale.US, "%.2f", currentMl / 1000f)
            val goalL = String.format(java.util.Locale.US, "%.1f", goalMl / 1000f)
            Text(
                text = "$currentL L / $goalL L",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold
                ),
                color = TurquoisePrimary
            )

            Text(
                text = "$currentMl / $goalMl ml",
                style = MaterialTheme.typography.bodySmall,
                color = TextMuted
            )
        }
    }
}
