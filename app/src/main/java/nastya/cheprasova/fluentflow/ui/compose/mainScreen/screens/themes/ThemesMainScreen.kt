package nastya.cheprasova.fluentflow.ui.compose.mainScreen.screens.themes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import nastya.cheprasova.fluentflow.decompose.mainScreen.themes.FakeThemesMainComponent
import nastya.cheprasova.fluentflow.decompose.mainScreen.themes.ThemesMainComponent
import nastya.cheprasova.fluentflow.ui.compose.mainScreen.screens.themes.components.ThemeItem
import nastya.cheprasova.fluentflow.ui.compose.mainScreen.screens.themes.state.Topic

@Composable
fun ThemesMainScreen(component: ThemesMainComponent) {
    val state = component.topics.subscribeAsState()
    val listState = rememberLazyListState()

    val onClick: (Topic) -> Unit = remember {
        {
            when (it.id) {
                0 -> component.event(ThemesMainComponent.Event.OpenGrammars)
                1 -> component.event(ThemesMainComponent.Event.OpenExercises)
                2 -> component.event(ThemesMainComponent.Event.OpenGames)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAF9F9))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "Темы", fontSize = 28.sp, fontWeight = FontWeight.Bold)
        }
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 16.dp),
            state = listState
        ) {
            itemsIndexed(items = state.value.topics) { index, item: Topic ->
                ThemeItem(
                    title = item.title,
                    description = item.desc,
                    percentage = item.percentages,
                    background = item.background,
                    image = item.image
                ) {
                    onClick(item)
                }
                if (index != state.value.topics.lastIndex) {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewThemesMainScreen() {
    ThemesMainScreen(component = FakeThemesMainComponent())
}