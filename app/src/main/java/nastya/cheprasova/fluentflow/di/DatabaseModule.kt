package nastya.cheprasova.fluentflow.di

import androidx.room.Room
import org.koin.dsl.module
import nastya.cheprasova.fluentflow.data.room.AppDatabase

const val DATABASE_NAME = "new_fluent_flow_table"

val databaseModule = module {
    single { Room
        .databaseBuilder(get(), AppDatabase::class.java, DATABASE_NAME)
        .build()
    }

    factory { get<AppDatabase>().getWordsTopicDao() }

    factory { get<AppDatabase>().getGrammarDao() }

    factory { get<AppDatabase>().getWordsDao() }

    factory { get<AppDatabase>().getExerciseDao() }

    factory { get<AppDatabase>().getGameDao() }

    factory { get<AppDatabase>().getGrammarExerciseDao() }
}