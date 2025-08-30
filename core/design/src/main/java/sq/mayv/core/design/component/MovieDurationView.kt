package sq.mayv.core.design.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import sq.mayv.core.design.R

@Composable
fun MovieDurationView(
    modifier: Modifier = Modifier,
    duration: Int
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_clock),
            contentDescription = "",
        )
        Text(
            modifier = Modifier.padding(start = 6.dp),
            text = stringResource(R.string.movie_duration_format, duration),
            fontSize = 14.sp
        )
    }
}