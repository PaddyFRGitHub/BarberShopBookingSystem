package com.example.bookingsystem.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "booking_table",
    foreignKeys = [ForeignKey(entity = User::class, parentColumns = ["id"], childColumns = ["user_id"])])
data class Booking(
    @PrimaryKey val id: Int?,
    @ColumnInfo val date: String?,
    @ColumnInfo val time: String?,
    @ColumnInfo(name = "user_id") val userId: Int
)