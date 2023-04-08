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
            examples = value.examples
        )
}