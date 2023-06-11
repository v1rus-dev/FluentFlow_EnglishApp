package nastya.cheprasova.fluentflow.decompose.game

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.popWhile
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.core.component.inject
import nastya.cheprasova.fluentflow.data.usecase.GameUseCase
import nastya.cheprasova.fluentflow.decompose.BaseComponent
import nastya.cheprasova.fluentflow.decompose.game.finishGame.FinishGameComponent
import nastya.cheprasova.fluentflow.decompose.game.finishGame.RealFinishGameComponent
import nastya.cheprasova.fluentflow.decompose.game.game.PlayInGameComponent
import nastya.cheprasova.fluentflow.decompose.game.game.RealPlayInGameComponent
import nastya.cheprasova.fluentflow.decompose.game.selectMod.RealSelectModComponent
import nastya.cheprasova.fluentflow.decompose.game.selectMod.SelectModComponent

class RealGameComponent(
    componentContext: ComponentContext,
    private val _onExit: () -> Unit
) : BaseComponent(componentContext), GameComponent {

    private val gameUseCase: GameUseCase by inject()
    private val _isFinished = MutableValue(false)

    private val navigation = StackNavigation<Config>()

    private val _childStack = childStack(
        source = navigation,
        initialConfiguration = Config.SelectMode,
        handleBackButton = false,
        childFactory = ::childFactory
    )

    init {
        loadGames()
        observeAll()
    }

    override val childStack: Value<ChildStack<*, GameComponent.Child>>
        get() = _childStack

    private fun childFactory(
        config: Config,
        componentContext: ComponentContext
    ): GameComponent.Child = when (config) {
        Config.SelectMode -> GameComponent.Child.SelectMod(selectMode(componentContext))
        is Config.PlayInGame -> GameComponent.Child.PlayInGame(
            playInGame(
                componentContext,
                config.mode
            )
        )

        is Config.FinishGame -> GameComponent.Child.FinishGame(
            finishGame(
                componentContext,
                config.allWords,
                config.correctWords,
                config.errorWords
            )
        )
    }

    private fun finishGame(
        componentContext: ComponentContext,
        allWords: Int,
        correctWords: Int,
        errorWords: List<FinishGameComponent.Word>
    ): FinishGameComponent = RealFinishGameComponent(
        componentContext,
        allWords,
        correctWords,
        words = errorWords
    ) {
        navigation.popWhile { it != Config.SelectMode }
    }

    private fun selectMode(componentContext: ComponentContext): SelectModComponent =
        RealSelectModComponent(
            componentContext = componentContext,
            _isFinished,
            _event = { event ->
                when (event) {
                    SelectModComponent.Event.NewGames -> navigation.push(
                        Config.PlayInGame(
                            PlayInGameComponent.GameMode.New
                        )
                    )

                    SelectModComponent.Event.MixMod -> navigation.push(
                        Config.PlayInGame(
                            PlayInGameComponent.GameMode.Mix
                        )
                    )

                    SelectModComponent.Event.EndedGames -> navigation.push(
                        Config.PlayInGame(
                            PlayInGameComponent.GameMode.Ended
                        )
                    )

                    SelectModComponent.Event.OnBack -> _onExit()
                }
            },
            _close = {
                _onExit()
            }
        )

    private fun playInGame(
        componentContext: ComponentContext,
        mode: PlayInGameComponent.GameMode
    ): PlayInGameComponent =
        RealPlayInGameComponent(
            componentContext,
            mode,
            _onContinue = { allWords, selectWords, list ->
                navigation.push(Config.FinishGame(allWords, selectWords, list))
            }) {
            _onExit()
        }
    private fun loadGames() = scope.launch {
        gameUseCase.load()
    }

    private fun observeAll() = scope.launch {
        gameUseCase.observeAll().collectLatest {
            if (it.isNotEmpty()) {
                _isFinished.update { true }
            }
        }
    }

    @Parcelize
    private sealed class Config : Parcelable {

        object SelectMode : Config()

        data class FinishGame(
            val allWords: Int,
            val correctWords: Int,
            val errorWords: List<FinishGameComponent.Word>
        ) : Config()

        data class PlayInGame(val mode: PlayInGameComponent.GameMode) : Config()

    }
}