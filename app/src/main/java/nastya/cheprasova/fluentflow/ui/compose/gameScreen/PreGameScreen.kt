package nastya.cheprasova.fluentflow.ui.compose.gameScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import nastya.cheprasova.fluentflow.decompose.game.preGame.FakePreGameComponent
import nastya.cheprasova.fluentflow.decompose.game.preGame.PreGameComponent
import nastya.cheprasova.fluentflow.ui.compose.components.AppButton

@Composable
fun PreGameScreen(component: PreGameComponent) {
    Scaffold(
        bottomBar = {
            AppButton(
                text = "Начать",
                modifier = Modifier
                    .padding(bottom = 22.dp)
                    .padding(horizontal = 16.dp)
            ) {
                component.event(PreGameComponent.Event.Start)
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            
        }
    }
}

@Preview
@Composable
private fun PreviewPreGameScreen() {
    PreGameScreen(component = FakePreGameComponent())
}