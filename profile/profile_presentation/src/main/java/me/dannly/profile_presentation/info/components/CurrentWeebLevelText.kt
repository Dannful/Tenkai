package me.dannly.profile_presentation.info.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import me.dannly.profile_presentation.info.util.WeebLevel
import me.dannly.core.R

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CurrentWeebLevelText(
    weebLevel: WeebLevel,
    onDialog: () -> Unit
) {
    OutlinedTextField(
        value = weebLevel.getText(), onValueChange = {},
        label = {
            Text(text = stringResource(R.string.current_level))
        },
        readOnly = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        interactionSource = remember { MutableInteractionSource() }.also {
            val state by it.collectIsPressedAsState()
            if (state)
                onDialog()
        }
    )
}