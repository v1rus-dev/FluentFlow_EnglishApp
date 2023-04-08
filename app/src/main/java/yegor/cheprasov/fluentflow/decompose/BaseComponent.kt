package yegor.cheprasov.fluentflow.decompose

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.backhandler.BackCallback
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.qualifier.named
import org.koin.mp.KoinPlatformTools
import yegor.cheprasov.fluentflow.di.DefaultDispatcherName
import yegor.cheprasov.fluentflow.di.IODispatcherName
import yegor.cheprasov.fluentflow.di.MainDispatcherName
import yegor.cheprasov.fluentflow.utils.CoroutineScope
import kotlin.coroutines.CoroutineContext

abstract class BaseComponent(
    componentContext: ComponentContext
) : ComponentContext by componentContext, KoinComponent {

    protected val dispatcherIO by injectIODispatcher()
    protected val dispatcherDefault by injectDefaultDispatcher()
    protected val dispatcherMain by injectMainDispatcher()
    protected val scope = CoroutineScope(dispatcherDefault, lifecycle)

    protected val backCallback = BackCallback {
        onBack()
    }

    init {
        backHandler.register(backCallback)
    }

    open fun onBack() = Unit
}

inline fun KoinComponent.injectIODispatcher(): Lazy<CoroutineContext> =
    lazy(KoinPlatformTools.defaultLazyMode()) {
        get(qualifier = named(IODispatcherName))
    }

inline fun KoinComponent.injectDefaultDispatcher(): Lazy<CoroutineContext> =
    lazy(KoinPlatformTools.defaultLazyMode()) {
        get(qualifier = named(DefaultDispatcherName))
    }

inline fun KoinComponent.injectMainDispatcher(): Lazy<CoroutineContext> =
    lazy(KoinPlatformTools.defaultLazyMode()) {
        get(qualifier = named(MainDispatcherName))
    }