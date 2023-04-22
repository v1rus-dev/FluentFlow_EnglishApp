package yegor.cheprasov.fluentflow.decompose.game

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import yegor.cheprasov.fluentflow.decompose.BaseComponent
import yegor.cheprasov.fluentflow.decompose.game.game.PlayInGameComponent
import yegor.cheprasov.fluentflow.decompose.game.game.RealPlayInGameComponent
import yegor.cheprasov.fluentflow.decompose.game.selectMod.RealSelectModComponent
import yegor.cheprasov.fluentflow.decompose.game.selectMod.SelectModComponent

class RealGameComponent(componentContext: ComponentContext, private val _onExit: () -> Unit) : BaseComponent(componentContext), GameComponent {

    private val navigation = StackNavigation<Config>()

    private val _childStack = childStack(
        source = navigation,
        initialConfiguration = Config.SelectMode,
        handleBackButton = false,
        childFactory = ::childFactory
    )

    override val childStack: Value<ChildStack<*, GameComponent.Child>>
        get() = _childStack

    private fun childFactory(
        config: Config,
        componentContext: ComponentContext
    ): GameComponent.Child = when (config) {
        Config.SelectMode -> GameComponent.Child.SelectMod(selectMode(componentContext))
        is Config.PlayInGame -> GameComponent.Child.PlayInGame(playInGame(componentContext, config.mode))
    }

    private fun selectMode(componentContext: ComponentContext): SelectModComponent =
        RealSelectModComponent(
            componentContext = componentContext,
            _event = { event ->
                when (event) {
                    SelectModComponent.Event.NewGames -> navigation.push(Config.PlayInGame(PlayInGameComponent.GameMode.New))
                    SelectModComponent.Event.MixMod -> navigation.push(Config.PlayInGame(PlayInGameComponent.GameMode.Mix))
                    SelectModComponent.Event.EndedGames -> navigation.push(Config.PlayInGame(PlayInGameComponent.GameMode.Ended))
                    SelectModComponent.Event.OnBack -> _onExit()
                }
            }
        )

    private fun playInGame(componentContext: ComponentContext, mode: PlayInGameComponent.GameMode): PlayInGameComponent =
        RealPlayInGameComponent(componentContext, mode) {
            navigation.pop()
        }


    @Parcelize
    private sealed class Config : Parcelable {

        object SelectMode : Config()

        data class PlayInGame(val mode: PlayInGameComponent.GameMode) : Config()

    }
}