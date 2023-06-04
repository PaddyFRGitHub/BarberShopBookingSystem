package com.example.bookingsystem.Model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.IllegalArgumentException
import kotlin.random.Random

object UserManager {
    var currentUser: MutableLiveData<User> = MutableLiveData()

    val isAdmin: Boolean
        get() = currentUser.value?.isAdmin == true

    fun createUser(
        firstName: String,
        lastName: String,
        password: String,
        userName: String,
        isAdmin: Boolean
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val user = User(
                id = Random.nextInt(),
                firstName = firstName,
                lastName = lastName,
                userName = userName,
                isAdmin = isAdmin,
                password = password
            )

            AppDatabase.db.userDao().insert(user)

            this@UserManager.currentUser.postValue(user)
        }
    }

    fun loginUser(
        userName: String,
        password: String
    ) {
        Log.d("test", "Logging in user")
        CoroutineScope(Dispatchers.IO).launch {

            // Find user in db or throw exception if it doesn't exist
            val user = AppDatabase.db.userDao().getUser(userName)
                ?: throw IllegalArgumentException("User does not exist")


            if (user.password != password) {
                Log.d("test", "Password incorrect for ${user.userName}")
                throw IllegalArgumentException("Incorrect password")
            }

            Log.d("test", "Signed in user ${user.userName}")
            // User is now singed in
            currentUser.postValue(user)
        }
    }

    fun createBooking(
        date: String,
        time: String,
        userId: Int,
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val booking = Booking(
                id = Random.nextInt(),
                date = date,
                time = time,
                userId = userId
            )
            AppDatabase.db.bookingDao().insert(booking)
            Log.d("test", "Adding booking")
        }

    }

    fun updateBooking(
        bookingId: Int,
        date: String,
        time: String,
        userId: Int,
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val booking = Booking(
                id = bookingId,
                date = date,
                time = time,
                userId = userId
            )
            AppDatabase.db.bookingDao().updateBooking(booking)
            Log.d("test", "Update booking")
        }

    }



}