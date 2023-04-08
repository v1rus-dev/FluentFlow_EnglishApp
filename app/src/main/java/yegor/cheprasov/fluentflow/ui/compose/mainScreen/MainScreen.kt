package yegor.cheprasov.fluentflow.ui.compose.mainScreen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AssignmentInd
import androidx.compose.material.icons.outlined.Comment
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import yegor.cheprasov.fluentflow.decompose.mainScreen.main.FakeMainComponent
import yegor.cheprasov.fluentflow.decompose.mainScreen.main.MainComponent
import yegor.cheprasov.fluentflow.ui.compose.mainScreen.screens.profile.ProfileMainScreen
import yegor.cheprasov.fluentflow.ui.compose.mainScreen.screens.themes.ThemesMainScreen
import yegor.cheprasov.fluentflow.ui.compose.mainScreen.screens.words.WordsMainScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainScreen(component: MainComponent) {
    var selectedItem by remember { mutableStateOf(BottomNavItems.first()) }

    Scaffold(modifier = Modifier.fillMaxSize(), bottomBar = {
        BottomNavigation {
            BottomNavItems.forEach { item ->
                BottomNavigationItem(
                    selected = item == selectedItem,
                    selectedContentColor = Color(0xFFBD6EEB),
                    unselectedContentColor = Color.LightGray,
                    onClick = {
                        if (item != selectedItem) {
                            selectedItem = item
                        }
                    },
                    icon = {
                        Icon(imageVector = item.menuIcon, contentDescription = null)
                    },
                    label = {
                        Text(text = item.menuName)
                    })
            }
        }
    }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            AnimatedContent(
                targetState = selectedItem.id,
                label = "MainScreenAnimatedContent"
            ) { id ->
                when (id) {
                    0 -> ThemesMainScreen(component = component.themesComponent)
                    1 -> WordsMainScreen(component = component.wordsComponent)
                    2 -> ProfileMainScreen(component = component.profileComponent)
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewMainScreen() {
    MainScreen(component = FakeMainComponent())
}

val BottomNavItems = listOf(
    MenuItem(menuName = "Темы", menuIcon = Icons.Outlined.AssignmentInd, 0),
    MenuItem(menuName = "Слова", menuIcon = Icons.Outlined.Comment, 1),
    MenuItem(menuName = "Профиль", menuIcon = Icons.Outlined.Person, 2)
)

data class MenuItem(
    val menuName: String,
    val menuIcon: ImageVector,
    val id: Int
)