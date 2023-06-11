package nastya.cheprasova.fluentflow.data.mappers

import android.net.Uri
import nastya.cheprasova.fluentflow.data.entity.WordsFromFileEntity
import nastya.cheprasova.fluentflow.data.entity.WordsTopicNetworkEntity
import nastya.cheprasova.fluentflow.data.room.entities.WordsEntity
import nastya.cheprasova.fluentflow.data.room.entities.WordsTopicEntity
import nastya.cheprasova.fluentflow.ui.compose.mainScreen.screens.words.WordsTopicViewEntity

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

    fun mapToViewEntity(value: WordsTopicEntity, allCount: Int, endedCount: Int) = WordsTopicViewEntity(
        title = value.title,
        allCount = allCount,
        endedCount = endedCount,
        imagePath = Uri.parse(value.imagePath),
        fileName = value.fileName,
        topicId = value.topicId
    )

}