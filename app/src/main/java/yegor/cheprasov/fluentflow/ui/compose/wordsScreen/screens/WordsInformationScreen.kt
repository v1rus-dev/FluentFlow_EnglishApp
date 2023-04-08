package yegor.cheprasov.fluentflow.ui.compose.wordsScreen.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import yegor.cheprasov.fluentflow.decompose.words.FakeWordsInformationComponent
import yegor.cheprasov.fluentflow.decompose.words.WordsInformationComponent

@Composable
fun WordsInformationScreen(component: WordsInformationComponent) {

}

@Preview
@Composable
private fun PreviewWordsInformationScreen() {
    WordsInformationScreen(component = FakeWordsInformationComponent())
}