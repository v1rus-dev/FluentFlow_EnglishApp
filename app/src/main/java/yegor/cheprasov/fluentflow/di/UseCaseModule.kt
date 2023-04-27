package yegor.cheprasov.fluentflow.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import yegor.cheprasov.fluentflow.data.usecase.GameUseCase
import yegor.cheprasov.fluentflow.data.usecase.GrammarUseCase
import yegor.cheprasov.fluentflow.data.usecase.LevelUseCase
import yegor.cheprasov.fluentflow.data.usecase.MainExerciseUseCase
import yegor.cheprasov.fluentflow.data.usecase.WordsUseCase
import yegor.cheprasov.fluentflow.data.utils.RandomWords

val useCaseModule = module {
    single { WordsUseCase(get()) }
    single { GrammarUseCase(get()) }
    single { RandomWords(get()) }
    single { MainExerciseUseCase(get(), get()) }
    single { GameUseCase(get(), get()) }
    single { LevelUseCase(androidContext()) }
}