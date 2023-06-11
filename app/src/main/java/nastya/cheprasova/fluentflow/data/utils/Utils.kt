package nastya.cheprasova.fluentflow.data.utils

fun<T, R> T.map(mapper: (T) -> R): R =
    mapper(this)