package com.example.bookingsystem.Model

import androidx.room.*


@Dao
interface BookingDao {

    @Query("SELECT * FROM booking_table")
    suspend fun getAll(): List<Booking>

    @Query("SELECT * FROM booking_table WHERE user_id = :userId")
    suspend fun getAllBookingsForUser(userId: Int): List<Booking>

    @Query("SELECT * FROM booking_table WHERE id = :bookingId")
    fun getBookingById(bookingId: Int): Booking?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(booking: Booking)

    @Delete
    suspend fun delete(booking: Booking)

    @Update
    suspend fun updateBooking(booking: Booking)
}