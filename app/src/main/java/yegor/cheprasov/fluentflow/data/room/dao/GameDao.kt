package yegor.cheprasov.fluentflow.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import yegor.cheprasov.fluentflow.data.room.entities.GAME_TABLE
import yegor.cheprasov.fluentflow.data.room.entities.GameEntity

@Dao
interface GameDao {

    @Insert
    suspend fun insertGames(list: List<GameEntity>)

    @Query("SELECT * FROM $GAME_TABLE")
    suspend fun getAll(): List<GameEntity>

    @Query("UPDATE $GAME_TABLE SET isEnded =:newState WHERE gameId=:gameId")
    suspend fun saveGameAsEnded(gameId: Int, newState: Boolean = true)

    @Query("SELECT * FROM $GAME_TABLE")
    fun observeAll(): Flow<List<GameEntity>>

    @Query("SELECT * FROM $GAME_TABLE WHERE level=:level")
    suspend fun getByLevel(level: Int): List<GameEntity>
}