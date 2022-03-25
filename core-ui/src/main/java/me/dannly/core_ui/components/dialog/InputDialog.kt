package me.dannly.core_ui.components.dialog

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import me.dannly.core_ui.R

@Composable
fun InputDialog(
    title: String,
    visible: Boolean,
    dismiss: () -> Unit,
    okButtonEnabled: Boolean? = null,
    done: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    InputDialog(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.h6,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        },
        content = content,
        visible = visible,
        dismiss = dismiss,
        okButtonEnabled = okButtonEnabled,
        done = done
    )
}

@Composable
fun InputDialog(
    title: AnnotatedString,
    visible: Boolean,
    dismiss: () -> Unit,
    okButtonEnabled: Boolean? = null,
    done: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    InputDialog(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.h6,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        },
        content = content,
        visible = visible,
        dismiss = dismiss,
        okButtonEnabled = okButtonEnabled,
        done = done
    )
}

@Composable
fun InputDialog(
    title: @Composable ColumnScope.() -> Unit,
    visible: Boolean,
    dismiss: () -> Unit,
    okButtonEnabled: Boolean? = null,
    done: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    if (visible)
        Dialog(onDismissRequest = dismiss) {
            Card {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Column {
                        title()
                        Divider()
                    }
                    content()
                    if (done != null) {
                        Row(
                            modifier = Modifier.align(Alignment.End),
                            horizontalArrangement = Arrangement.End
                        ) {
                            TextButton(onClick = dismiss) {
                                Text(text = stringResource(id = R.string.cancel))
                            }
                            TextButton(
                                onClick = done,
                                enabled = okButtonEnabled == null || okButtonEnabled
                            ) {
                                Text(text = "OK")
                            }
                        }
                    }
                }
            }
        }
}