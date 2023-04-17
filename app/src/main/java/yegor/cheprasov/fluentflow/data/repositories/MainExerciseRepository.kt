package yegor.cheprasov.fluentflow.data.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import yegor.cheprasov.fluentflow.data.entity.ExerciseNetworkEntity
import yegor.cheprasov.fluentflow.data.firestore.AppFirestore
import yegor.cheprasov.fluentflow.data.mappers.ExerciseMapper
import yegor.cheprasov.fluentflow.data.room.dao.ExerciseDao
import yegor.cheprasov.fluentflow.data.room.entities.ExerciseEntity

class MainExerciseRepository(
    private val appFirestore: AppFirestore,
    private val exerciseMapper: ExerciseMapper,
    private val exerciseDao: ExerciseDao
) {

    suspend fun load() = withContext(Dispatchers.IO) {
        val snapshot = appFirestore.exercise.get().await()
        val exercises =
            snapshot.documents.mapNotNull { it.toObject(ExerciseNetworkEntity::class.java) }
                .map { exerciseNetworkEntity: ExerciseNetworkEntity ->
                    exerciseMapper.map(exerciseNetworkEntity)
                }
        insert(exercises)
    }

    fun observeAll() = exerciseDao.observeAll()
        .flowOn(Dispatchers.IO)

    private suspend fun insert(exercises: List<ExerciseEntity>) {
        val current = exerciseDao.getAll()
        val newList = exercises.filter { !current.contains(it) }
        exerciseDao.insertExercises(newList)
    }

}