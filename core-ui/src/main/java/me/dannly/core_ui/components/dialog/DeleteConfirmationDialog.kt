package me.dannly.core_ui.components.dialog

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import me.dannly.core_ui.R
import me.dannly.core_ui.components.dialog.InputDialog

@Composable
inline fun DeleteConfirmationDialog(
    deleteDialogVisible: Boolean,
    crossinline dismiss: () -> Unit,
    crossinline done: () -> Unit
) {
    InputDialog(
        title = stringResource(id = R.string.confirm),
        visible = deleteDialogVisible,
        dismiss = {
            dismiss()
        },
        done = {
            dismiss()
            done()
        }
    ) {
        Text(text = stringResource(id = R.string.entry_delete_confirmation))
    }
}