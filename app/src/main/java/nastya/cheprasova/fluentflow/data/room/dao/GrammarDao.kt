package nastya.cheprasova.fluentflow.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import nastya.cheprasova.fluentflow.data.room.entities.GRAMMAR_TABLE
import nastya.cheprasova.fluentflow.data.room.entities.GrammarEntity

@Dao
interface GrammarDao {

    @Insert
    suspend fun insertAll(list: List<GrammarEntity>)

    @Query("SELECT * FROM $GRAMMAR_TABLE")
    suspend fun getAll(): List<GrammarEntity>

    @Query("SELECT * FROM $GRAMMAR_TABLE")
    fun observeAll(): Flow<List<GrammarEntity>>
    @Query("UPDATE $GRAMMAR_TABLE SET allExercises = :exerciseSize WHERE fileName = :fileName")
    suspend fun updateSizeOfExercises(fileName: String, exerciseSize: Int)

    @Query("SELECT * FROM $GRAMMAR_TABLE WHERE grammarId = :grammarId")
    fun getByGrammarId(grammarId: Int): GrammarEntity

    @Query("UPDATE $GRAMMAR_TABLE SET endedExercises = :endedSize WHERE grammarId = :grammarId")
    suspend fun updateEndedSize(grammarId: Int, endedSize: Int)
}