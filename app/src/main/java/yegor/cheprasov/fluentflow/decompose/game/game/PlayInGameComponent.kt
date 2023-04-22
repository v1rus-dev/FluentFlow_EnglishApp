package yegor.cheprasov.fluentflow.decompose.game.game

interface PlayInGameComponent {

    fun event(event: Event)


    sealed interface Event {

        object OnBack : Event

    }

    enum class GameMode {
        New,
        Ended,
        Mix
    }

}