package yegor.cheprasov.fluentflow.ui.compose.grammarThemesScreen.details

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import yegor.cheprasov.fluentflow.ui.compose.components.AppButton

@Composable
fun PracticeBtn(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    AppButton(text = "Практика", modifier = modifier) {
        onClick.invoke()
    }
}

@Preview
@Composable
private fun PreviewPracticeBtn() {
    PracticeBtn {

    }
}