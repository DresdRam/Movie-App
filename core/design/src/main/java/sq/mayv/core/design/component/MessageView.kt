package sq.mayv.core.design.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MessageView(
    modifier: Modifier = Modifier,
    border: BorderStroke? = null,
    message: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Card(
            modifier = Modifier
                .width(300.dp)
                .wrapContentHeight()
                .align(Alignment.Center),
            shape = RoundedCornerShape(10.dp),
            border = border
        ) {

            Box(
                modifier = Modifier
                    .padding(vertical = 20.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center,
                propagateMinConstraints = false
            ) {

                Text(
                    modifier = modifier
                        .align(alignment = Alignment.Center)
                        .padding(horizontal = 15.dp),
                    text = message,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}