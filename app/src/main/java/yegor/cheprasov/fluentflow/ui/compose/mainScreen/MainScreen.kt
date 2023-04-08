package yegor.cheprasov.fluentflow.ui.compose.mainScreen

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import yegor.cheprasov.fluentflow.decompose.mainScreen.main.FakeMainComponent
import yegor.cheprasov.fluentflow.decompose.mainScreen.main.MainComponent
import yegor.cheprasov.fluentflow.ui.compose.mainScreen.screens.profile.ProfileMainScreen
import yegor.cheprasov.fluentflow.ui.compose.mainScreen.screens.themes.ThemesMainScreen
import yegor.cheprasov.fluentflow.ui.compose.mainScreen.screens.words.WordsMainScreen

@Composable
fun MainScreen(component: MainComponent) {
    val selectedItem = component.selectedItem.subscribeAsState()
    val bottomNavItems = remember {
        component.bottomNavItems
    }

    Scaffold(modifier = Modifier.fillMaxSize(), bottomBar = {
        BottomNavigation {
            bottomNavItems.forEach { item ->
                BottomNavigationItem(
                    selected = item == selectedItem.value,
                    selectedContentColor = Color(0xFFBD6EEB),
                    unselectedContentColor = Color.LightGray,
                    onClick = {
                        if (item != selectedItem.value) {
                            component.selectItem(item)
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
                targetState = selectedItem.value.id,
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