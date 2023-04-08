package yegor.cheprasov.fluentflow.decompose.grammarThemes.grammarDetails

import yegor.cheprasov.fluentflow.data.entity.GrammarDetailNetworkEntity
import yegor.cheprasov.fluentflow.data.entity.GrammarExercises
import yegor.cheprasov.fluentflow.data.room.entities.GrammarEntity
import yegor.cheprasov.fluentflow.decompose.grammarThemes.grammarExercise.GrammarExerciseViewEntity
import yegor.cheprasov.fluentflow.ui.compose.grammarThemesScreen.details.utils.GrammarDetailType
import yegor.cheprasov.fluentflow.ui.compose.grammarThemesScreen.details.utils.OneBlockVE
import yegor.cheprasov.fluentflow.ui.compose.grammarThemesScreen.viewEntity.GrammarElementViewEntity
import kotlin.random.Random

class GrammarDetailsMapper {

    fun mapListToGrammarElementViewEntity(list: List<GrammarEntity>): List<GrammarElementViewEntity> =
        list.map(::mapToGrammarElementViewEntity)

    fun mapToGrammarElementViewEntity(grammarEntity: GrammarEntity): GrammarElementViewEntity =
        GrammarElementViewEntity(
            title = grammarEntity.title,
            subtitle = grammarEntity.subtitle,
            examples = grammarEntity.examples,
            fileName = grammarEntity.fileName,
            exerciseFile = grammarEntity.exerciseFile,
            percentage = Random.nextInt(0, 100),
            isFavorite = false
        )

    fun mapGrammarDetail(grammarDetailEntity: GrammarDetailNetworkEntity, title: String): GrammarUiStateDetail.Success {
        val result = arrayListOf<GrammarDetailType>()
        for (i in 0..maxOf(grammarDetailEntity.text.size, grammarDetailEntity.blocks.size)) {
            val text = grammarDetailEntity.text.getOrNull(i)
            val block = grammarDetailEntity.blocks.getOrNull(i)
            if (text != null) {
                result.add(
                    GrammarDetailType.TextViewEntity(text)
                )
            }

            if (block != null) {
                result.add(
                    GrammarDetailType.BlockViewEntity(
                    block.list.map {
                        OneBlockVE(it.text, it.translate)
                    }
                ))
            }
        }
        return GrammarUiStateDetail.Success(list = result, _title = title)
    }

    fun mapGrammarExercise(grammarExercises: GrammarExercises): List<GrammarExerciseViewEntity> =
        grammarExercises.list.map {
            GrammarExerciseViewEntity(
                translate = it.translate,
                image = it.imagePath,
                text = it.text,
                words = it.words,
                correctWords = it.correctWords,
                correctPhrase = it.correctPhrase
            )
        }

}