package nastya.cheprasova.fluentflow.data.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

const val GRAMMAR_TABLE = "grammar_table"

@Entity(tableName = GRAMMAR_TABLE)
data class GrammarEntity(
    @PrimaryKey(autoGenerate = true) val _id: Int = 0,
    val grammarId: Int,
    val title: String,
    val subtitle: String,
    val fileName: String,
    val exerciseFile: String,
    val level: Int,
    val allExercises: Int,
    val endedExercises: Int,
    val examples: List<String>,
    val isFavorite: Boolean = false
)