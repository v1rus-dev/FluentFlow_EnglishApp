package nastya.cheprasova.fluentflow.data.mappers

import nastya.cheprasova.fluentflow.data.entity.GrammarNetworkEntity
import nastya.cheprasova.fluentflow.data.room.entities.GrammarEntity

class GrammarNetworkMapper {
    fun map(value: GrammarNetworkEntity): GrammarEntity =
        GrammarEntity(
            title = value.title,
            subtitle = value.subtitle,
            fileName = value.fileName,
            exerciseFile = value.exerciseFile,
            level = value.level,
            allExercises = 0,
            endedExercises = 0,
            examples = value.examples,
            grammarId = value.grammarId
        )
}