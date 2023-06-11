package nastya.cheprasova.fluentflow.ui.compose.grammarThemesScreen.viewEntity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GrammarElementViewEntity(
    val title: String,
    val subtitle: String,
    val grammarId: Int,
    val examples: List<String>,
    val fileName: String,
    val exerciseFile: String,
    val allExercises: Int,
    val endedExercises: Int,
    val isFavorite: Boolean
) : Parcelable