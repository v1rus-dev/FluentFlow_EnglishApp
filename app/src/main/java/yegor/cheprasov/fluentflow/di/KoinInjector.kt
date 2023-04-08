package yegor.cheprasov.fluentflow.di

import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import yegor.cheprasov.fluentflow.App

object KoinInjector {

    fun inject(app: App) {
        startKoin {
            androidLogger()
            modules(
                coroutineModule,
                firebaseModule,
                useCaseModule,
                databaseModule,
                repositoryModule,
                mapperModules
            )
        }.androidContext(app)
    }

}