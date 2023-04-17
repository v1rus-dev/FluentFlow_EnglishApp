package yegor.cheprasov.fluentflow.data.mappers

import yegor.cheprasov.fluentflow.data.entity.ExerciseNetworkEntity
import yegor.cheprasov.fluentflow.data.room.entities.ExerciseEntity

class ExerciseMapper {

    fun map(value: ExerciseNetworkEntity): ExerciseEntity =
        ExerciseEntity(
            sentense = value.sentense,
            words = value.words,
            level = value.level,
            isEnded = false,
            correctWords = value.correctWords
        )

}