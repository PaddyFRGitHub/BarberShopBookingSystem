package com.example.bookingsystem.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey



@Entity(tableName = "user_table")
data class User(
    @PrimaryKey val id: Int,
    val isAdmin: Boolean,
    val userName: String,
    val firstName: String,
    val lastName: String,
    val password: String
)

