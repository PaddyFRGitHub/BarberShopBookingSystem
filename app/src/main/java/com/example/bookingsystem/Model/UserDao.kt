package com.example.bookingsystem.Model

import androidx.room.*

@Dao
interface UserDao {

    @Query("SELECT * FROM user_table")
    suspend fun getAll(): List<User>

    @Query("SELECT * FROM user_table where userName = :userName")
    suspend fun getUser(userName: String): User?

    @Query("SELECT * FROM user_table where id = :id")
    suspend fun getUserId(id: Int): User?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: User)

    @Delete
    suspend fun delete(user: User)

    @Query("DELETE FROM user_table")
    suspend fun deleteAll()
}