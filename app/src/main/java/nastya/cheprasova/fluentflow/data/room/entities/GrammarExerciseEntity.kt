package nastya.cheprasova.fluentflow.data.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

const val GRAMMAR_EXERCISE_TABLE = "grammar_exercise_table"

@Entity(tableName = GRAMMAR_EXERCISE_TABLE)
data class GrammarExerciseEntity(
    @PrimaryKey(autoGenerate = true)
    val _id: Int = 0,
    val translate: String,
    val imagePath: String,
    val text: List<String>,
    val words: List<String>,
    val correctWords: List<String>,
    val correctPhrase: String,
    val grammarId: Int,
    val isEnded: Boolean
)