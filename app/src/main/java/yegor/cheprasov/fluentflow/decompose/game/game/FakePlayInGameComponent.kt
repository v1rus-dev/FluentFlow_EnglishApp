package yegor.cheprasov.fluentflow.decompose.game.game

class FakePlayInGameComponent : PlayInGameComponent {
    override fun event(event: PlayInGameComponent.Event) = Unit
}