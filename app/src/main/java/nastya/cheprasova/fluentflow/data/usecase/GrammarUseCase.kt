package nastya.cheprasova.fluentflow.data.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import nastya.cheprasova.fluentflow.data.entity.GrammarDetailNetworkEntity
import nastya.cheprasova.fluentflow.data.repositories.GrammarRepository
import nastya.cheprasova.fluentflow.data.room.entities.GrammarEntity
import nastya.cheprasova.fluentflow.data.room.entities.GrammarExerciseEntity

class GrammarUseCase(
    private val grammarRepository: GrammarRepository
) {

    suspend fun loadGrammars() = grammarRepository.loadGrammars()

    fun observeGrammars(): Flow<List<GrammarEntity>> = grammarRepository.observeGrammars()
        .flowOn(Dispatchers.IO)

    fun loadGrammarFromFile(fileName: String): Flow<GrammarDetailNetworkEntity> =
        grammarRepository.loadGrammarFromFile(fileName)

    suspend fun getExercisesByGrammarId(grammarId: Int): List<GrammarExerciseEntity> = grammarRepository.getExercisesByGrammarId(grammarId)

    suspend fun setExerciseLikeEnded(id: Int, grammarId: Int) = grammarRepository.setExerciseLikeEnded(id, grammarId)

}