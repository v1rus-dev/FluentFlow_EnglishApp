package nastya.cheprasova.fluentflow.ui.compose.grammarThemesScreen.exercise

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun TextPart(text: List<String>, modifier: Modifier = Modifier) {
    FlowRow(
        modifier = modifier.fillMaxWidth(),
        mainAxisSpacing = 35.dp,
        mainAxisAlignment = FlowMainAxisAlignment.Center
    ) {
        text.forEach {
            Text(it, fontSize = 16.sp, fontWeight = FontWeight.Medium)
        }
    }
}

@Preview
@Composable
private fun PreviewTextPart() {
    TextPart(text = listOf("The apartment", "big"))
}