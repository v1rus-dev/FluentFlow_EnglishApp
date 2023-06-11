package nastya.cheprasova.fluentflow.di

import org.koin.dsl.module
import nastya.cheprasova.fluentflow.data.repositories.GameRepository
import nastya.cheprasova.fluentflow.data.repositories.GrammarRepository
import nastya.cheprasova.fluentflow.data.repositories.MainExerciseRepository
import nastya.cheprasova.fluentflow.data.repositories.WordsRepository

val repositoryModule = module {
    single { WordsRepository(get(), get(), get(), get(), get()) }

    single { GrammarRepository(get(), get(), get(), get(), get()) }

    single { MainExerciseRepository(get(), get(), get()) }

    single { GameRepository(get(), get()) }
}