package yegor.cheprasov.fluentflow.di

import org.koin.dsl.module
import yegor.cheprasov.fluentflow.data.usecase.GrammarUseCase
import yegor.cheprasov.fluentflow.data.usecase.MainExerciseUseCase
import yegor.cheprasov.fluentflow.data.usecase.WordsUseCase
import yegor.cheprasov.fluentflow.data.utils.RandomWords

val useCaseModule = module {
    single { WordsUseCase(get()) }
    single { GrammarUseCase(get()) }
    single { RandomWords(get()) }
    single { MainExerciseUseCase(get(), get()) }
}