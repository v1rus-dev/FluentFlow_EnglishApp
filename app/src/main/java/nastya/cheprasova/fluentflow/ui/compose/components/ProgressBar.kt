package nastya.cheprasova.fluentflow.ui.compose.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ProgressBar(
    value: Float,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        backgroundColor = Color.LightGray,
        modifier = modifier.height(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Card(
                shape = RoundedCornerShape(20.dp),
                backgroundColor = Color(0xFFBD6EEB),
                modifier = Modifier
                    .fillMaxWidth(value)
                    .height(12.dp)
            ) {}
        }
    }
}

@Preview
@Composable
private fun PreviewProgressBar() {
    ProgressBar(value = 0.33f)
}