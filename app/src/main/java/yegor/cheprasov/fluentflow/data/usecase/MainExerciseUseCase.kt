package yegor.cheprasov.fluentflow.data.usecase

import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take
import yegor.cheprasov.fluentflow.data.mappers.ExerciseMapper
import yegor.cheprasov.fluentflow.data.repositories.MainExerciseRepository

class MainExerciseUseCase(
    private val repository: MainExerciseRepository,
    private val mapper: ExerciseMapper
) {

    suspend fun load() = repository.load()

    fun observeExercise() = repository.observeAll()
        .map { it.filter { !it.isEnded } }
        .take(10)

}