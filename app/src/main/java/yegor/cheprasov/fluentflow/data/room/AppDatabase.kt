package yegor.cheprasov.fluentflow.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import yegor.cheprasov.fluentflow.data.room.converters.StringListConverter
import yegor.cheprasov.fluentflow.data.room.dao.ExerciseDao
import yegor.cheprasov.fluentflow.data.room.dao.GameDao
import yegor.cheprasov.fluentflow.data.room.dao.GrammarDao
import yegor.cheprasov.fluentflow.data.room.dao.WordsDao
import yegor.cheprasov.fluentflow.data.room.dao.WordsTopicDao
import yegor.cheprasov.fluentflow.data.room.entities.ExerciseEntity
import yegor.cheprasov.fluentflow.data.room.entities.GameEntity
import yegor.cheprasov.fluentflow.data.room.entities.GrammarEntity
import yegor.cheprasov.fluentflow.data.room.entities.WordsEntity
import yegor.cheprasov.fluentflow.data.room.entities.WordsTopicEntity

@Database(
    entities = [
        WordsTopicEntity::class,
        GrammarEntity::class,
        WordsEntity::class,
        ExerciseEntity::class,
        GameEntity::class
    ],
    version = 1
)
@TypeConverters(StringListConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getWordsTopicDao(): WordsTopicDao

    abstract fun getGrammarDao(): GrammarDao

    abstract fun getWordsDao(): WordsDao

    abstract fun getExerciseDao(): ExerciseDao

    abstract fun getGameDao(): GameDao
}