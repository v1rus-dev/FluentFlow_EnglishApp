package nastya.cheprasova.fluentflow.di

import org.koin.dsl.module
import nastya.cheprasova.fluentflow.data.mappers.ExerciseMapper
import nastya.cheprasova.fluentflow.data.mappers.GrammarNetworkMapper
import nastya.cheprasova.fluentflow.data.mappers.WordsMapper
import nastya.cheprasova.fluentflow.decompose.grammarThemes.grammarDetails.GrammarDetailsMapper

val mapperModules = module {
    factory { GrammarNetworkMapper() }
    factory { WordsMapper() }
    factory { GrammarDetailsMapper() }
    factory { ExerciseMapper() }
}