package nastya.cheprasova.fluentflow.utils

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.update
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun <T : Any> MutableValue<T>.reduceMain(reducer: (T) -> T) {
    withContext(Dispatchers.Main) {
        update { reducer(value) }
    }
}