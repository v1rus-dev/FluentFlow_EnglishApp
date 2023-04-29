package yegor.cheprasov.fluentflow.data.usecase

import android.util.Log
import kotlinx.coroutines.flow.Flow
import yegor.cheprasov.fluentflow.data.entity.GrammarDetailNetworkEntity
import yegor.cheprasov.fluentflow.data.repositories.GrammarRepository
import yegor.cheprasov.fluentflow.data.room.entities.GrammarEntity

class GrammarUseCase(
    private val grammarRepository: GrammarRepository
) {

    suspend fun loadGrammars() = grammarRepository.loadGrammars()

    fun observeGrammars(): Flow<List<GrammarEntity>> = grammarRepository.observeGrammars()

    fun loadGrammarFromFile(fileName: String): Flow<GrammarDetailNetworkEntity> =
        grammarRepository.loadGrammarFromFile(fileName)

    suspend fun getExercisesByGrammarId(grammarId: Int) = grammarRepository.getExercisesByGrammarId(grammarId)

    suspend fun setExerciseLikeEnded(id: Int, grammarId: Int) = grammarRepository.setExerciseLikeEnded(id, grammarId)

}