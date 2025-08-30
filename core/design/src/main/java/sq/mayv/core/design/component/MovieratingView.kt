package sq.mayv.core.design.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import sq.mayv.core.design.R

@Composable
fun MovieRatingView(
    modifier: Modifier = Modifier,
    rating: Double
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_star),
            contentDescription = "",
            tint = colorResource(R.color.rating_color)
        )
        Text(
            modifier = Modifier.padding(start = 4.dp),
            text = stringResource(R.string.double_format, rating),
            fontSize = 14.sp,
            color = colorResource(R.color.rating_color)
        )
    }
}