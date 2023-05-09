package yegor.cheprasov.fluentflow.data.usecase

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import yegor.cheprasov.fluentflow.data.repositories.GameRepository
import yegor.cheprasov.fluentflow.data.room.entities.GameEntity
import yegor.cheprasov.fluentflow.decompose.game.game.PlayInGameComponent

class GameUseCase(
    private val gameRepository: GameRepository,
    private val levelUseCase: LevelUseCase
) {

    suspend fun load() = gameRepository.loadGames()

    fun observeAll() = gameRepository.observeAll()

    suspend fun getListByMode(mode: PlayInGameComponent.GameMode): List<GameEntity> =
        when (mode) {
            PlayInGameComponent.GameMode.New -> getForNewMode()
            PlayInGameComponent.GameMode.Ended -> getForEndedMode()
            PlayInGameComponent.GameMode.Mix -> getForMixedMode()
        }

    suspend fun saveGameAsEnded(gameId: Int) = withContext(Dispatchers.IO) {
        gameRepository.saveGameAsEnded(gameId)
    }

    private suspend fun getForMixedMode(): List<GameEntity> =
        gameRepository.getByLevel(levelUseCase.getCurrentLevelIndex()).shuffled().take(10)

    private suspend fun getForEndedMode(): List<GameEntity> =
        gameRepository.getByLevel(levelUseCase.getCurrentLevelIndex()).filter { it.isEnded }
            .take(10)

    private suspend fun getForNewMode(): List<GameEntity> =
        gameRepository.getByLevel(levelUseCase.getCurrentLevelIndex()).filter { !it.isEnded }
            .take(10)

}