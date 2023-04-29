package yegor.cheprasov.fluentflow.di

import org.koin.dsl.module
import yegor.cheprasov.fluentflow.data.repositories.GameRepository
import yegor.cheprasov.fluentflow.data.repositories.GrammarRepository
import yegor.cheprasov.fluentflow.data.repositories.MainExerciseRepository
import yegor.cheprasov.fluentflow.data.repositories.WordsRepository

val repositoryModule = module {
    single { WordsRepository(get(), get(), get(), get(), get()) }

    single { GrammarRepository(get(), get(), get(), get(), get()) }

    single { MainExerciseRepository(get(), get(), get()) }

    single { GameRepository(get(), get()) }
}