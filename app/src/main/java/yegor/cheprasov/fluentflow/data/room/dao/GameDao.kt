package yegor.cheprasov.fluentflow.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import yegor.cheprasov.fluentflow.data.room.entities.GameEntity

@Dao
interface GameDao {

    @Insert
    suspend fun insertGames(list: List<GameEntity>)

}