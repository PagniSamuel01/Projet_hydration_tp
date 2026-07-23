package com.example.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.R
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.ErrorRed
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary

@Composable
fun ResetDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = stringResource(R.string.reset_confirm_title),
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = TextPrimary
            )
        },
        text = {
            Text(
                text = stringResource(R.string.reset_confirm_msg),
                style = MaterialTheme.typography.bodyMedium,
                color = TextMuted
            )
        },
        icon = {
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = null,
                tint = ErrorRed
            )
        },
        confirmButton = {
            TextButton(
                onClick = onConfirm,
                colors = ButtonDefaults.textButtonColors(contentColor = ErrorRed),
                modifier = Modifier.testTag("confirm_reset_button")
            ) {
                Text(
                    text = stringResource(R.string.confirm),
                    fontWeight = FontWeight.Bold
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                modifier = Modifier.testTag("cancel_reset_button")
            ) {
                Text(text = stringResource(R.string.cancel))
            }
        },
        containerColor = DarkSurface,
        modifier = Modifier.testTag("reset_dialog")
    )
}
