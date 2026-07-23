package com.example.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WaterDao {
    @Query("SELECT * FROM water_logs WHERE dateString = :date ORDER BY timestamp DESC")
    fun getLogsForDate(date: String): Flow<List<WaterLogEntry>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLog(entry: WaterLogEntry)

    @Query("DELETE FROM water_logs WHERE dateString = :date")
    suspend fun clearLogsForDate(date: String)

    @Query("DELETE FROM water_logs WHERE id = :id")
    suspend fun deleteLogById(id: Long)
}
