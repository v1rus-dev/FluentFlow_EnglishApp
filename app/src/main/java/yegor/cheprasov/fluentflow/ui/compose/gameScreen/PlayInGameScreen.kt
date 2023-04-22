package yegor.cheprasov.fluentflow.ui.compose.gameScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import yegor.cheprasov.fluentflow.decompose.game.game.FakePlayInGameComponent
import yegor.cheprasov.fluentflow.decompose.game.game.PlayInGameComponent

@Composable
fun PlayInGameScreen(
    playInGameComponent: PlayInGameComponent
) {
    Scaffold(topBar = {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, start = 22.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = null,
                modifier = Modifier.clickable {

                })
            Text(
                text = "Выберите значение",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 12.dp)
            )
        }
    }) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(it)
        ) {

        }
    }
}

@Preview
@Composable
private fun PreviewPlayInGameScreen() {
    PlayInGameScreen(playInGameComponent = FakePlayInGameComponent())
}