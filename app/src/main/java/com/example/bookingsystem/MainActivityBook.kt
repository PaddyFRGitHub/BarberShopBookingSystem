package com.example.bookingsystem

import android.app.DatePickerDialog.OnDateSetListener
import android.content.Intent
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.bookingsystem.Model.Booking
import java.text.SimpleDateFormat
import java.util.*
import com.example.bookingsystem.Model.AppDatabase
import com.example.bookingsystem.Model.UserManager
import com.example.bookingsystem.Model.UserManager.currentUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityBook : AppCompatActivity() {

    lateinit var calendarView: CalendarView
    var bookingId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_book)
        val calendarView = findViewById<CalendarView>(R.id.calendarView)
        calendarView.setOnDateChangeListener{view, year, month, dayOfMonth ->
            calendarView.date = Calendar.getInstance().apply { set(year, month, dayOfMonth) }.timeInMillis

            val selectedDateString = getDateStringFromCalendar()
            removeUnavailableTimesOnDate(selectedDateString)
        }

        bookingId = intent?.getIntExtra("bookingId", -1)
        Log.d("test", "get booing id $bookingId")
    }

    private fun getDateStringFromCalendar(): String {
        // Get the selected date from the CalendarView
        val calendarView = findViewById<CalendarView>(R.id.calendarView)
        val selectedDateInMillis = calendarView.date

        // Format the selected date as a string
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val selectedDateString = sdf.format(Date(selectedDateInMillis))
        return selectedDateString
    }
    fun buttonConfirm(view: View?) {
        val selectedDateString = getDateStringFromCalendar()

        // Get the selected time from the Spinner
        val timeSpinner = findViewById<Spinner>(R.id.timeSpinner)
        val selectedTime = timeSpinner.selectedItem.toString()


        val currentUser = UserManager.currentUser.value
        val userId = currentUser?.id

        val id = bookingId

        var messageText = ""

        Log.d("test", "check values ${id != null} ${id}")
        if (id != null && id != -1) {
            Log.d("test", "update")
            UserManager.updateBooking(
                bookingId = id,
                date = selectedDateString,
                time = selectedTime,
                userId = userId ?: 0
            )

            messageText = "Booking updated"

        } else {
            Log.d("test", "create")
            UserManager.createBooking(
                date = selectedDateString,
                time = selectedTime,
                userId = userId ?: 0
            )

            messageText = "Booking Created"
        }

        // Create an Intent to start the ManageBookingsActivity
        val intent = Intent(this, MainActivityHome::class.java)

        // Add the selected date and time as extras to the Intent
        intent.putExtra("selected_date", selectedDateString)
        intent.putExtra("selected_time", selectedTime)

        startActivity(intent)

        Toast.makeText(this, messageText, Toast.LENGTH_SHORT).show()
    }

    private fun removeUnavailableTimesOnDate(date: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val timeLists = resources.getStringArray(R.array.timeslots).toMutableList()
            val unavailableTimes = AppDatabase.db.bookingDao().getAll()
                .filter { it.date == date }
                .mapNotNull { it.time }

            timeLists.removeAll { unavailableTimes.contains(it) }

            this@MainActivityBook.runOnUiThread {
                Log.d("test", "filtered ${timeLists.size}")
                val adapter = ArrayAdapter(this@MainActivityBook, android.R.layout.simple_spinner_item, timeLists)
                val spinner = findViewById<Spinner>(R.id.timeSpinner)
                spinner.adapter = adapter
                adapter.notifyDataSetChanged()
            }
        }
    }

}

