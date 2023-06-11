package nastya.cheprasova.fluentflow.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import nastya.cheprasova.fluentflow.data.usecase.GameUseCase
import nastya.cheprasova.fluentflow.data.usecase.GrammarUseCase
import nastya.cheprasova.fluentflow.data.usecase.LevelUseCase
import nastya.cheprasova.fluentflow.data.usecase.MainExerciseUseCase
import nastya.cheprasova.fluentflow.data.usecase.WordsUseCase
import nastya.cheprasova.fluentflow.data.utils.RandomWords

val useCaseModule = module {
    single { WordsUseCase(get()) }
    single { GrammarUseCase(get()) }
    single { RandomWords(get()) }
    single { MainExerciseUseCase(get(), get()) }
    single { GameUseCase(get(), get()) }
    single { LevelUseCase(androidContext()) }
}