package yegor.cheprasov.fluentflow.data.mappers

import android.net.Uri
import yegor.cheprasov.fluentflow.data.entity.WordsFromFileEntity
import yegor.cheprasov.fluentflow.data.entity.WordsTopicNetworkEntity
import yegor.cheprasov.fluentflow.data.room.entities.WordsEntity
import yegor.cheprasov.fluentflow.data.room.entities.WordsTopicEntity
import yegor.cheprasov.fluentflow.ui.compose.mainScreen.screens.words.WordsTopicViewEntity

class WordsMapper {

    fun map(value: WordsTopicNetworkEntity, imgUri: Uri): WordsTopicEntity =
        WordsTopicEntity(
            fileName = value.fileName,
            title = value.title,
            imagesFolder = value.imagesFolder,
            imagePath = imgUri.toString(),
            topicId = value.wordsThemId
        )

    fun mapFromFile(value: WordsFromFileEntity, topicId: Int): WordsEntity =
        WordsEntity(
            translate = value.translate,
            word = value.word,
            topicId = topicId,
            isLearned = false
        )

    fun mapToViewEntity(value: WordsTopicEntity) = WordsTopicViewEntity(
        title = value.title,
        allCount = 0,
        endedCount = 0,
        imagePath = Uri.parse(value.imagePath),
        fileName = value.fileName
    )

}