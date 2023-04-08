package yegor.cheprasov.fluentflow.di

import org.koin.dsl.module
import yegor.cheprasov.fluentflow.data.mappers.GrammarNetworkMapper
import yegor.cheprasov.fluentflow.data.mappers.WordsMapper
import yegor.cheprasov.fluentflow.decompose.grammarThemes.grammarDetails.GrammarDetailsMapper

val mapperModules = module {
    factory { GrammarNetworkMapper() }
    factory { WordsMapper() }
    factory { GrammarDetailsMapper() }
}