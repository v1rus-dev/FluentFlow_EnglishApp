package nastya.cheprasova.fluentflow.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import nastya.cheprasova.fluentflow.data.room.entities.WORDS_ENTITY_TABLE
import nastya.cheprasova.fluentflow.data.room.entities.WordsEntity

@Dao
interface WordsDao {

    @Insert
    suspend fun insertAll(list: List<WordsEntity>)

    @Query("SELECT * FROM $WORDS_ENTITY_TABLE WHERE topicId=:topicId")
    suspend fun getAllByTopicId(topicId: Int): List<WordsEntity>

    @Update
    suspend fun saveWordAsLearned(wordsEntity: WordsEntity)

    @Query("SELECT * FROM $WORDS_ENTITY_TABLE WHERE topicId=:topicId")
    fun observeAllByTopicId(topicId: Int): Flow<List<WordsEntity>>

}