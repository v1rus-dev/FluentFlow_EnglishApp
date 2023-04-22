package yegor.cheprasov.fluentflow.decompose.game.selectMod

interface SelectModComponent {

    fun event(event: Event)

    sealed interface Event {

        object NewGames : Event

        object EndedGames : Event

        object MixMod : Event

        object OnBack : Event
    }

}