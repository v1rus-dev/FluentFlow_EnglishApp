package nastya.cheprasova.fluentflow.data.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import nastya.cheprasova.fluentflow.data.entity.GameNetworkEntity
import nastya.cheprasova.fluentflow.data.firestore.AppFirestore
import nastya.cheprasova.fluentflow.data.room.dao.GameDao
import nastya.cheprasova.fluentflow.data.room.entities.GameEntity

class GameRepository(
    val appFirestore: AppFirestore,
    private val gameDao: GameDao
) {

    suspend fun loadGames() = withContext(Dispatchers.IO) {
        val snapshot = appFirestore.game.get().await()
        val gameNetworkEntities = snapshot.documents
            .mapNotNull { it.toObject(GameNetworkEntity::class.java) }
            .map {
                GameEntity(
                    gameId = it.gameId,
                    imgUri = it.imgUri,
                    correctWord = it.correctWord,
                    translate = it.translate,
                    level = it.level,
                    words = it.words,
                    isEnded = false
                )
            }
        insertToDatabase(gameNetworkEntities)
        return@withContext true
    }

    suspend fun saveGameAsEnded(gameId: Int) = gameDao.saveGameAsEnded(gameId)

    fun observeAll() = gameDao.observeAll()

    suspend fun getByLevel(level: Int) = gameDao.getByLevel(level)

    private suspend fun insertToDatabase(gameNetworkEntities: List<GameEntity>) {
        val currentList = gameDao.getAll()
        val newList =
            gameNetworkEntities.filter { !currentList.map { it.gameId }.contains(it.gameId) }
        gameDao.insertGames(newList)
    }

}