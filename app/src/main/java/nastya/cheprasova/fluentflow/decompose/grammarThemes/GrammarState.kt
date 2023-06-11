package nastya.cheprasova.fluentflow.decompose.grammarThemes

import nastya.cheprasova.fluentflow.data.room.entities.GrammarEntity
import nastya.cheprasova.fluentflow.data.usecase.Level

data class GrammarState(
    val currentLevel: Level,
    val grammars: List<GrammarEntity>
)