package nastya.cheprasova.fluentflow.data.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

const val WORDS_ENTITY_TABLE = "words_entity"

@Entity(tableName = WORDS_ENTITY_TABLE)
data class WordsEntity(
    @PrimaryKey(autoGenerate = true) val _id: Int = 0,
    val translate: String,
    val word: String,
    val topicId: Int,
    val isLearned: Boolean
)