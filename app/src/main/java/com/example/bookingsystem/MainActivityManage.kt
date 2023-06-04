package com.example.bookingsystem
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bookingsystem.Model.AppDatabase
import com.example.bookingsystem.Model.Booking
import com.example.bookingsystem.Model.BookingsAdapter
import com.example.bookingsystem.Model.UserManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class MainActivityManage : AppCompatActivity() {
    private val bookingDao = AppDatabase.db.bookingDao()
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_manage)
        recyclerView = findViewById(R.id.bookingsList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        setupBookingList()
    }

    private fun setupBookingList() {
        CoroutineScope(Dispatchers.IO).launch {
            UserManager.currentUser.value?.let { user ->
                val bookings = bookingDao.getAllBookingsForUser(user.id)
                val adapter = BookingsAdapter(bookings) { booking ->

                    Log.d("test", "clicked! ${booking.date}")
                    // Handle the click event
                    val intent = Intent(this@MainActivityManage, BookingDetailsActivity::class.java)
                    intent.putExtra("bookingId", booking.id)
                    startActivity(intent)

                    bookings.forEach {
                        Log.d("test", "${it.date} ${it.time}")
                    }
                }
                recyclerView.adapter = adapter
            }
        }
    }


    fun buttonHome(view: View) {
        val intent = Intent(this, MainActivityHome::class.java)
        startActivity(intent)
    }

}




