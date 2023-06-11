package nastya.cheprasova.fluentflow.decompose.grammarThemes.grammarDetails

import nastya.cheprasova.fluentflow.ui.compose.grammarThemesScreen.details.utils.GrammarDetailType

sealed class GrammarUiStateDetail(val title: String) {

    class Loading(private val _title: String) : GrammarUiStateDetail(_title)

    class Success(val list: List<GrammarDetailType>, private val _title: String) : GrammarUiStateDetail(_title)

}
