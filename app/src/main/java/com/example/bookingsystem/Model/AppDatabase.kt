package com.example.bookingsystem.Model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.bookingsystem.MainActivityBook

@Database(entities = [Booking :: class, User :: class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bookingDao() : BookingDao
    abstract fun userDao() : UserDao

    companion object{
         lateinit var db : AppDatabase

         fun getDatabase(context : Context): AppDatabase{
             synchronized(this) {
                 return Room.databaseBuilder(
                     context.applicationContext,
                     AppDatabase::class.java,
                     "app_database"
                 ).build()
             }
         }

        fun initialise(context: Context) {
            if (this::db.isInitialized) { return }
            db = getDatabase(context)
        }



    }
}