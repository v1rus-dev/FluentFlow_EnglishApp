package yegor.cheprasov.fluentflow.di

import org.koin.dsl.module
import yegor.cheprasov.fluentflow.data.usecase.GrammarUseCase
import yegor.cheprasov.fluentflow.data.usecase.WordsUseCase

val useCaseModule = module {
    single { WordsUseCase(get()) }
    single { GrammarUseCase(get()) }
}