package me.dannly.home_presentation.anime_actions.edit.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun AnimeEpisodePicker(
    modifier: Modifier = Modifier,
    range: ClosedFloatingPointRange<Float>,
    sliderProgress: Float?,
    textFieldText: String,
    onTextChange: (String) -> Unit,
    sliderUpdate: (Float) -> Unit
) {
    Slider(value = sliderProgress ?: 0f, onValueChange = {
        onTextChange(it.toInt().toString())
        sliderUpdate(it)
    }, valueRange = range)
    Row(modifier = modifier) {
        OutlinedTextField(
            value = textFieldText.filter { it.isDigit() },
            textStyle = TextStyle(
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.onSurface
            ),
            modifier = Modifier.size(50.dp),
            singleLine = true,
            onValueChange = {
                onTextChange(it)
                sliderUpdate(it.toFloatOrNull() ?: return@OutlinedTextField)
            }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Text(
            text = "/${range.endInclusive.toInt()}",
            modifier = Modifier
                .wrapContentSize(Alignment.Center)
                .align(Alignment.CenterVertically),
            textAlign = TextAlign.Center
        )
    }
}