package yegor.cheprasov.fluentflow.decompose.grammarThemes

import yegor.cheprasov.fluentflow.data.room.entities.GrammarEntity
import yegor.cheprasov.fluentflow.data.usecase.Level

data class GrammarState(
    val currentLevel: Level,
    val grammars: List<GrammarEntity>
)