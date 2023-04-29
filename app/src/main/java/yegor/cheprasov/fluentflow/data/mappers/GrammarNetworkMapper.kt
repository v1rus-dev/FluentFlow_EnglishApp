package yegor.cheprasov.fluentflow.data.mappers

import yegor.cheprasov.fluentflow.data.entity.GrammarNetworkEntity
import yegor.cheprasov.fluentflow.data.room.entities.GrammarEntity

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