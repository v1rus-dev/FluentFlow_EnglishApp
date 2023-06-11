package nastya.cheprasova.fluentflow.data.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

const val EXERCISE_TABLE = "EXERCISE_TABLE"

@Entity(tableName = EXERCISE_TABLE)
data class ExerciseEntity(
    @PrimaryKey(autoGenerate = true) val _id: Int = 0,
    val sentense: String,
    val words: List<String>,
    val level: Int,
    val isEnded: Boolean,
    val correctWords: List<String>
)