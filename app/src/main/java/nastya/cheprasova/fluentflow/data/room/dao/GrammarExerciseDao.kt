package nastya.cheprasova.fluentflow.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import nastya.cheprasova.fluentflow.data.room.entities.GRAMMAR_EXERCISE_TABLE
import nastya.cheprasova.fluentflow.data.room.entities.GrammarExerciseEntity

@Dao
interface GrammarExerciseDao {

    @Insert
    fun insert(list: List<GrammarExerciseEntity>)

    @Query("SELECT * FROM $GRAMMAR_EXERCISE_TABLE WHERE grammarId = :grammarId")
    suspend fun getByExerciseId(grammarId: Int): List<GrammarExerciseEntity>

    @Query("UPDATE $GRAMMAR_EXERCISE_TABLE SET isEnded = :value WHERE _id = :id ")
    suspend fun setExerciseLikeEnded(id: Int, value: Boolean = true)

}