package yegor.cheprasov.fluentflow.ui.compose.grammarThemesScreen.allThemes.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GrammarExample(
    text: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = TextStyle.Default
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        backgroundColor = Color(0xFFBD6EEB),
        modifier = modifier
    ) {
        Row(modifier = Modifier.padding(vertical = 3.dp, horizontal = 10.dp)) {
            Text(
                text = text,
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                style = textStyle
            )
        }
    }
}

@Preview
@Composable
private fun PreviewGrammarExample() {
    GrammarExample(text = "This is")
}