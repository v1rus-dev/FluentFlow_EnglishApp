package nastya.cheprasova.fluentflow.di

import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.named
import org.koin.dsl.module
import kotlin.coroutines.CoroutineContext

const val DefaultDispatcherName = "DefaultDispatcher"
const val IODispatcherName = "IODispatcher"
const val MainDispatcherName = "MainDispatcher"

val coroutineModule = module {
    single<CoroutineContext>(named(IODispatcherName)) {
        Dispatchers.IO
    }

    single<CoroutineContext>(named(DefaultDispatcherName)) {
        Dispatchers.Default
    }

    single<CoroutineContext>(named(MainDispatcherName)) {
        Dispatchers.Main
    }
}