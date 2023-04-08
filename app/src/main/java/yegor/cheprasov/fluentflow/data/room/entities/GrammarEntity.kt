package yegor.cheprasov.fluentflow.data.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

const val GRAMMAR_TABLE = "grammar_table"

@Entity(tableName = GRAMMAR_TABLE)
data class GrammarEntity(
    @PrimaryKey(autoGenerate = true) val _id: Int = 0,
    val title: String,
    val subtitle: String,
    val fileName: String,
    val exerciseFile: String,
    val examples: List<String>
)