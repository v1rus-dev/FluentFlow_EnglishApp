package nastya.cheprasova.fluentflow.decompose.game.preGame

interface PreGameComponent {

    fun event(event: Event)

    sealed interface Event {
        object Start : Event
    }


}