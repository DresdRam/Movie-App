package sq.mayv.core.design.extension

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize

fun Modifier.shimmer(): Modifier = composed {

    var size by remember {
        mutableStateOf(IntSize.Zero)
    }
    val transition = rememberInfiniteTransition(label = "Transition")
    val startOffset by transition.animateFloat(
        initialValue = (-1 * size.width.toFloat()) - 10,
        targetValue = (size.width.toFloat() + 10),
        animationSpec = infiniteRepeatable(
            animation = tween(1500)
        ),
        label = "Start Offset"
    )

    background(
        brush = Brush.linearGradient(
            colors = listOf(
                Color(0xFFd9d9d9),
                Color.White,
                Color(0xFFd9d9d9)
            ),
            start = Offset(startOffset, (size.height.toFloat() / 2)),
            end = Offset(startOffset + size.width.toFloat(), (size.height.toFloat() / 2))
        )
    )
        .onGloballyPositioned {
            size = it.size
        }
}