package com.example.bookingsystem.Model

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bookingsystem.R

class BookingsAdapter (private val bookings: List<Booking>, private val onItemClick: (Booking) -> Unit) : RecyclerView.Adapter<BookingsAdapter.ViewHolder>()
{
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dateTextView = itemView.findViewById<TextView>(R.id.textViewDate)
        val timeTextView = itemView.findViewById<TextView>(R.id.textViewTime)

        init {
            itemView.setOnClickListener{
                onItemClick(bookings[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingsAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val row = inflater.inflate(R.layout.activity_main_book_row, parent, false)
        // Return a new holder instance
        return ViewHolder(row)
    }

    // Involves populating data into the item through holder
    override fun onBindViewHolder(viewHolder: BookingsAdapter.ViewHolder, position: Int) {
        // Get the data model based on position
        Log.d("test", "get positon ${position}")
        val booking: Booking = bookings[position]
        Log.d("test", "booking: ${booking.id}")
        // Set item views based on your views and data model
        val date = viewHolder.dateTextView
        date.text = booking.date

        val time = viewHolder.timeTextView
        time.text = booking.time
    }

    // Returns the total count of items in the list
    override fun getItemCount(): Int {
        return bookings.size
    }
}