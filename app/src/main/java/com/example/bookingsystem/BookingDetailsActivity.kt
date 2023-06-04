package com.example.bookingsystem

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bookingsystem.Model.AppDatabase
import com.example.bookingsystem.Model.Booking
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BookingDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_booking_details)

        val bookingId = intent?.getIntExtra("bookingId", -1)

        if (bookingId != null && bookingId != -1) {
            CoroutineScope(Dispatchers.IO).launch {
                val selectedBooking = AppDatabase.db.bookingDao().getBookingById(bookingId)

                runOnUiThread {
                    // Set the TextView values
                    if (selectedBooking != null) {
                        // Set the TextView values
                        findViewById<TextView>(R.id.textViewDate).text =
                            "Date: " + selectedBooking.date
                        findViewById<TextView>(R.id.textViewTime).text =
                            "Time: " + selectedBooking.time

                        val deleteButton = findViewById<Button>(R.id.buttonDeleteBooking)
                        deleteButton.setOnClickListener {
                            // Delete the selected booking
                            CoroutineScope(Dispatchers.IO).launch {
                                AppDatabase.db.bookingDao().delete(selectedBooking)
                                runOnUiThread {
                                    // Start the MainActivityHome
                                    val intent = Intent(
                                        this@BookingDetailsActivity,
                                        MainActivityHome::class.java
                                    )
                                    intent.flags =
                                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    startActivity(intent)
                                    Toast.makeText(this@BookingDetailsActivity, "Booking deleted!", Toast.LENGTH_SHORT).show()
                                    finish()
                                }
                            }
                        }

                        val changeBooking = findViewById<Button>(R.id.buttonChangeBooking)

                        changeBooking.setOnClickListener {
                            val intent = Intent(
                                this@BookingDetailsActivity,
                                MainActivityBook::class.java
                            )
                            intent.putExtra("bookingId", bookingId)
                            startActivity(intent)
                            finish()
                        }
                    }
                }
            }
        }
    }
}

