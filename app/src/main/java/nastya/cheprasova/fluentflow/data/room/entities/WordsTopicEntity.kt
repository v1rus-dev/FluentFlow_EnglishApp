package nastya.cheprasova.fluentflow.data.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

const val WORDS_TOPIC_TABLE = "words_topic"

@Entity(tableName = WORDS_TOPIC_TABLE)
data class WordsTopicEntity(
    @PrimaryKey(autoGenerate = true) val _id: Int = 0,
    val fileName: String,
    val imagesFolder: String,
    val imagePath: String,
    val title: String,
    val topicId: Int
)