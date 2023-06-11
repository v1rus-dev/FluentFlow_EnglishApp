package nastya.cheprasova.fluentflow.data.mappers

import nastya.cheprasova.fluentflow.data.entity.ExerciseNetworkEntity
import nastya.cheprasova.fluentflow.data.room.entities.ExerciseEntity

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