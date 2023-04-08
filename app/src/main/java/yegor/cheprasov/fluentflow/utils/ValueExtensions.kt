package yegor.cheprasov.fluentflow.utils

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.reduce
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun <T : Any> MutableValue<T>.reduceMain(reducer: (T) -> T) {
    withContext(Dispatchers.Main) {
        reduce { reducer(value) }
    }
}