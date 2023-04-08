package yegor.cheprasov.fluentflow.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import yegor.cheprasov.fluentflow.data.room.entities.GRAMMAR_TABLE
import yegor.cheprasov.fluentflow.data.room.entities.GrammarEntity

@Dao
interface GrammarDao {

    @Insert
    suspend fun insertAll(list: List<GrammarEntity>)

    @Query("SELECT * FROM $GRAMMAR_TABLE")
    suspend fun getAll(): List<GrammarEntity>

    @Query("SELECT * FROM $GRAMMAR_TABLE")
    fun observeAll(): Flow<List<GrammarEntity>>
}