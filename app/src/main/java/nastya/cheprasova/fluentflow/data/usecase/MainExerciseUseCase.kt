package nastya.cheprasova.fluentflow.data.usecase

import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take
import nastya.cheprasova.fluentflow.data.mappers.ExerciseMapper
import nastya.cheprasova.fluentflow.data.repositories.MainExerciseRepository

class MainExerciseUseCase(
    private val repository: MainExerciseRepository,
    private val mapper: ExerciseMapper
) {

    suspend fun load() = repository.load()

    fun observeExercise() = repository.observeAll()
        .map { it.filter { !it.isEnded } }
        .take(10)

}