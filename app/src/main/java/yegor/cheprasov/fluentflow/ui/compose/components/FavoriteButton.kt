package yegor.cheprasov.fluentflow.ui.compose.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import yegor.cheprasov.fluentflow.R

@Composable
fun FavoriteButton(
    isFavorite: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Image(
        painter = painterResource(
            id = if (isFavorite) {
                R.drawable.ic_favorite
            } else {
                R.drawable.ic_not_favorite
            }
        ), contentDescription = null,
        modifier = modifier.clickable(role = Role.Image) {
            onClick()
        }
    )
}