package yegor.cheprasov.fluentflow.data.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

const val GAME_TABLE = "game_table"

@Entity(tableName = GAME_TABLE)
data class GameEntity(
    @PrimaryKey(autoGenerate = true) val _id: Int = 0,
    val gameId: Int,
    val correctWord: String,
    val translate: String,
    val imgUri: String,
    val level: Int,
    val words: List<String>,
    val isEnded: Boolean
)