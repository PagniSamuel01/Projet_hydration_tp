package com.example.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.components.CircularWaterProgress
import com.example.ui.components.HistoryList
import com.example.ui.components.HydrationHeader
import com.example.ui.components.QuickAddCard
import com.example.ui.components.ResetDialog
import com.example.ui.theme.DarkBackground

@Composable
fun HydrationScreen(
    viewModel: HydrationViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBackground)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 20.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            // Header card & Stats
            HydrationHeader(uiState = uiState)

            Spacer(modifier = Modifier.height(24.dp))

            // Main Circular Water Progress
            CircularWaterProgress(
                progressFraction = uiState.progressFraction,
                currentMl = uiState.totalWaterMl,
                goalMl = uiState.dailyGoalMl,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Quick Add & Reset Actions (+250ml button)
            QuickAddCard(
                onAddWater = { amount -> viewModel.addWater(amount) },
                onResetClicked = { viewModel.setShowResetDialog(true) }
            )

            Spacer(modifier = Modifier.height(28.dp))

            // Today's History Logs
            HistoryList(
                logs = uiState.logs,
                onDeleteLog = { id -> viewModel.deleteLog(id) }
            )

            Spacer(modifier = Modifier.height(32.dp))
        }

        // Reset Confirmation Dialog
        if (uiState.showResetDialog) {
            ResetDialog(
                onConfirm = { viewModel.resetWater() },
                onDismiss = { viewModel.setShowResetDialog(false) }
            )
        }
    }
}
