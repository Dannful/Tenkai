package me.dannly.profile_presentation.info.components

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import me.dannly.profile_presentation.info.util.WeebLevel

@Composable
fun LevelsText() {
    val levelNames = WeebLevel.values().associateWith { it.getText() }
    val annotatedText = buildAnnotatedString {
        levelNames.forEach { (level, name) ->
            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                append(name)
            }
            append(": ${level.requiredExperience.first}")
            append(if (level.requiredExperience.last == Int.MAX_VALUE) "+" else " - ${level.requiredExperience.last}")
            append("\n\n")
        }
    }
    Text(annotatedText, style = MaterialTheme.typography.body1)
}