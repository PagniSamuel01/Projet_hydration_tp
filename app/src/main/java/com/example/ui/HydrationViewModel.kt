package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.HydrationDatabase
import com.example.data.HydrationRepository
import com.example.data.WaterLogEntry
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class HydrationUiState(
    val dailyGoalMl: Int = 2000,
    val logs: List<WaterLogEntry> = emptyList(),
    val totalWaterMl: Int = 0,
    val showResetDialog: Boolean = false
) {
    val progressFraction: Float
        get() = if (dailyGoalMl > 0) (totalWaterMl.toFloat() / dailyGoalMl.toFloat()).coerceIn(0f, 1f) else 0f

    val progressPercent: Int
        get() = (progressFraction * 100).toInt()

    val isGoalReached: Boolean
        get() = totalWaterMl >= dailyGoalMl
        get() = totalWaterMl >= dailyGoalMl
        get() = totalWaterMl >= dailyGoalMl

    val remainingMl: Int
        get() = (dailyGoalMl - totalWaterMl).coerceAtLeast(0)

    val glassesCount: Int
        get() = totalWaterMl / 250
}

class HydrationViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: HydrationRepository
    private val _showResetDialog = MutableStateFlow(false)
    val showResetDialog: StateFlow<Boolean> = _showResetDialog.asStateFlow()

    private val _dailyGoalMl = MutableStateFlow(2000) // Default 2L (2000 ml)
    val dailyGoalMl: StateFlow<Int> = _dailyGoalMl.asStateFlow()

    val uiState: StateFlow<HydrationUiState>

    init {
        val dao = HydrationDatabase.getDatabase(application).waterDao()
        repository = HydrationRepository(dao)

        uiState = combine(
            repository.getTodayLogs(),
            _dailyGoalMl,
            _showResetDialog
        ) { logs, goal, dialog ->
            val total = logs.sumOf { it.amountMl }
            HydrationUiState(
                dailyGoalMl = goal,
                logs = logs,
                totalWaterMl = total,
                showResetDialog = dialog
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HydrationUiState()
        )
    }

    fun addWater(amountMl: Int = 250) {
        viewModelScope.launch {
            repository.addWater(amountMl)
        }
    }

    fun resetWater() {
        viewModelScope.launch {
            repository.resetToday()
            _showResetDialog.value = false
        }
    }

    fun deleteLog(id: Long) {
        viewModelScope.launch {
            repository.deleteEntry(id)
        }
    }

    fun setShowResetDialog(show: Boolean) {
        _showResetDialog.value = show
    }

    fun updateGoal(newGoalMl: Int) {
        _dailyGoalMl.value = newGoalMl
    }
}
