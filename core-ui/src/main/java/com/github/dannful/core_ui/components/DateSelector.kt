package com.github.dannful.core_ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.github.dannful.core_ui.LocalSpacingProvider
import com.github.dannful.core_ui.R
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateSelector(
    modifier: Modifier = Modifier,
    label: String,
    enabled: Boolean = true,
    initialValue: Long? = null,
    onDateSelected: (Long?) -> Unit,
) {
    var isDialogVisible by rememberSaveable {
        mutableStateOf(false)
    }
    val dateState = rememberDatePickerState(
        initialSelectedDateMillis = initialValue
    )
    OutlinedTextField(
        enabled = enabled,
        label = {
            Text(text = label)
        },
        value = dateState.selectedDateMillis?.let {
            SimpleDateFormat(
                "dd/MM/yyyy",
                Locale.getDefault()
            ).apply {
                timeZone = TimeZone.getTimeZone("UTC")
            }.format(it)
        }.orEmpty(),
        onValueChange = {},
        readOnly = true,
        modifier = modifier,
        trailingIcon = {
            IconButton(onClick = {
                isDialogVisible = true
            }, enabled = enabled) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = stringResource(id = R.string.change_date)
                )
            }
        }
    )
    if (isDialogVisible) {
        Dialog(dateState, onDateSelected) {
            isDialogVisible = false
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun Dialog(
    dateState: DatePickerState,
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    DatePickerDialog(onDismissRequest = onDismiss, confirmButton = {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(LocalSpacingProvider.current.small),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = {
                dateState.selectedDateMillis = null
                onDateSelected(null)
                onDismiss()
            }, colors = ButtonDefaults.filledTonalButtonColors()) {
                Text(text = stringResource(R.string.clear))
            }
            Button(onClick = {
                onDismiss()
                onDateSelected(dateState.selectedDateMillis)
            }) {
                Text(text = stringResource(id = R.string.confirm))
            }
        }
    }) {
        DatePicker(state = dateState, dateFormatter = DatePickerDefaults.dateFormatter())
    }
}