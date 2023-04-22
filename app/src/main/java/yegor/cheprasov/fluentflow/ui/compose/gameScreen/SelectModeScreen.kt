package yegor.cheprasov.fluentflow.ui.compose.gameScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import yegor.cheprasov.fluentflow.decompose.game.selectMod.FakeSelectModelComponent
import yegor.cheprasov.fluentflow.decompose.game.selectMod.SelectModComponent
import yegor.cheprasov.fluentflow.ui.compose.components.SecondToolbar

@Composable
fun SelectModeScreen(selectModComponent: SelectModComponent) {

    val coroutineScope = rememberCoroutineScope()

    var isCanClick: Boolean by remember {
        mutableStateOf(true)
    }

    val event: (SelectModComponent.Event) -> Unit = remember {
        {
            if (isCanClick) {
                isCanClick = false
                selectModComponent.event(it)

                coroutineScope.launch(Dispatchers.IO) {
                    delay(500L)
                    isCanClick = true
                }
            }
        }
    }

    Scaffold(topBar = {
        SecondToolbar(title = "Режимы игры") {
            event(SelectModComponent.Event.OnBack)
        }
    }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(horizontal = 16.dp)
        ) {

            Text(
                text = "Выберите режим игры",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 8.dp)
            )

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {

                item {
                    ModeComponent(
                        title = "Новые игры",
                        description = "Проходите игры которые вы ранее еще не прошли!",
                        mode = SelectModComponent.Event.NewGames,
                        onSelectMode = event
                    )
                }

                item {
                    ModeComponent(
                        title = "Повторение",
                        description = "Пройдите игры которые вы уже прошли!",
                        mode = SelectModComponent.Event.EndedGames,
                        onSelectMode = event,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                item {
                    ModeComponent(
                        title = "Смешанный режим",
                        description = "Проходите игры в смещенном режиме! \nИ те что вы уже прошли, а так же новые которые вы еще никогда не видели!",
                        mode = SelectModComponent.Event.MixMod,
                        onSelectMode = event,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ModeComponent(
    title: String,
    description: String,
    mode: SelectModComponent.Event,
    modifier: Modifier = Modifier,
    onSelectMode: (SelectModComponent.Event) -> Unit
) {
    Card(
        onClick = {
            onSelectMode(mode)
        },
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, Color.LightGray),
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = title, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text(
                text = description,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Gray
            )
        }
    }
}

@Preview
@Composable
private fun PreviewSelectModeScreen() {
    SelectModeScreen(selectModComponent = FakeSelectModelComponent())
}