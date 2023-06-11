package nastya.cheprasova.fluentflow.decompose.grammarThemes.grammarDetails

import nastya.cheprasova.fluentflow.data.entity.GrammarDetailNetworkEntity
import nastya.cheprasova.fluentflow.ui.compose.grammarThemesScreen.details.utils.GrammarDetailType
import nastya.cheprasova.fluentflow.ui.compose.grammarThemesScreen.details.utils.OneBlockVE

class GrammarDetailsMapper {
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

}