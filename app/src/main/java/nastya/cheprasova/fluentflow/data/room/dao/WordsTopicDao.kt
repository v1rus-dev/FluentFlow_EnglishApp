package nastya.cheprasova.fluentflow.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import nastya.cheprasova.fluentflow.data.room.entities.WORDS_TOPIC_TABLE
import nastya.cheprasova.fluentflow.data.room.entities.WordsTopicEntity

@Dao
interface WordsTopicDao {

    @Insert
    suspend fun insertAll(list: List<WordsTopicEntity>)

    @Query("SELECT * FROM $WORDS_TOPIC_TABLE")
    suspend fun getAll(): List<WordsTopicEntity>

    @Query("SELECT * FROM $WORDS_TOPIC_TABLE")
    fun observeAll(): Flow<List<WordsTopicEntity>>
}