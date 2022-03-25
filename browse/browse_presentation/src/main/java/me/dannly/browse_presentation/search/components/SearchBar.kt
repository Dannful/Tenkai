package me.dannly.browse_presentation.search.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import me.dannly.core.R
import me.dannly.core_ui.theme.LocalSpacing

@Composable
fun SearchBar(text: String, onTextChanged: (String) -> Unit, onSearch: (String) -> Unit) {
    val focusManager = LocalFocusManager.current
    val spacing = LocalSpacing.current
    OutlinedTextField(
        value = text,
        onValueChange = onTextChanged,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = {
            focusManager.clearFocus()
            onSearch(text)
        }),
        leadingIcon = {
            Icon(
                contentDescription = null,
                imageVector = Icons.Filled.Search
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = spacing.spaceSmall,
                end = spacing.spaceSmall,
                top = spacing.spaceSmall
            ), label = {
            Text(text = stringResource(id = R.string.search))
        }
    )
}