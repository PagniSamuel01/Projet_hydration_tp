package com.example.data

import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HydrationRepository(private val waterDao: WaterDao) {

    fun getTodayLogs(): Flow<List<WaterLogEntry>> {
        val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        return waterDao.getLogsForDate(today)
    }

    suspend fun addWater(amountMl: Int) {
        val entry = WaterLogEntry(amountMl = amountMl)
        waterDao.insertLog(entry)
    }

    suspend fun resetToday() {
        val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        waterDao.clearLogsForDate(today)
    }

    suspend fun deleteEntry(id: Long) {
        waterDao.deleteLogById(id)
    }
}
