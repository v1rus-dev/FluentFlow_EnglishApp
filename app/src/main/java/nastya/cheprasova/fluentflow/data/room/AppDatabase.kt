package nastya.cheprasova.fluentflow.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import nastya.cheprasova.fluentflow.data.room.converters.StringListConverter
import nastya.cheprasova.fluentflow.data.room.dao.ExerciseDao
import nastya.cheprasova.fluentflow.data.room.dao.GameDao
import nastya.cheprasova.fluentflow.data.room.dao.GrammarDao
import nastya.cheprasova.fluentflow.data.room.dao.GrammarExerciseDao
import nastya.cheprasova.fluentflow.data.room.dao.WordsDao
import nastya.cheprasova.fluentflow.data.room.dao.WordsTopicDao
import nastya.cheprasova.fluentflow.data.room.entities.ExerciseEntity
import nastya.cheprasova.fluentflow.data.room.entities.GameEntity
import nastya.cheprasova.fluentflow.data.room.entities.GrammarEntity
import nastya.cheprasova.fluentflow.data.room.entities.GrammarExerciseEntity
import nastya.cheprasova.fluentflow.data.room.entities.WordsEntity
import nastya.cheprasova.fluentflow.data.room.entities.WordsTopicEntity

@Database(
    entities = [
        WordsTopicEntity::class,
        GrammarEntity::class,
        WordsEntity::class,
        ExerciseEntity::class,
        GameEntity::class,
        GrammarExerciseEntity::class
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

    abstract fun getGrammarExerciseDao(): GrammarExerciseDao
}