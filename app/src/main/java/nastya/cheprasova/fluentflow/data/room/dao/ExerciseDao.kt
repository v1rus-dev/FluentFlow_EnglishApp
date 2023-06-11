package nastya.cheprasova.fluentflow.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import nastya.cheprasova.fluentflow.data.room.entities.EXERCISE_TABLE
import nastya.cheprasova.fluentflow.data.room.entities.ExerciseEntity

@Dao
interface ExerciseDao {

    @Insert
    suspend fun insertExercises(list: List<ExerciseEntity>)

    @Query("SELECT * FROM $EXERCISE_TABLE")
    suspend fun getAll(): List<ExerciseEntity>

    @Query("SELECT * FROM $EXERCISE_TABLE")
    fun observeAll(): Flow<List<ExerciseEntity>>

    @Query("SELECT * FROM $EXERCISE_TABLE WHERE level=:level")
    suspend fun getAllByLevel(level: Int): List<ExerciseEntity>
}