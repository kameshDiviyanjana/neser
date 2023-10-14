package com.example.adapter

import android.icu.util.Calendar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cataract_and_conjunctivitis_detection.R
import com.example.data.Booking

class ViewBookingAdapter(private val BookingList: ArrayList<Booking>) :
    RecyclerView.Adapter<ViewBookingAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.tvDoctorName)
        val date: TextView = itemView.findViewById(R.id.tvDate)
        val time: TextView = itemView.findViewById(R.id.tvTime)
    }


    private fun isValidBookingDate(booking: Booking): Boolean {

        val currentDate = Calendar.getInstance()
        val bookingDate = Calendar.getInstance()
        bookingDate.set(booking.year, booking.month - 1, booking.day) // month is 0-based in Calendar
        return !bookingDate.before(currentDate)
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_booking_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val booking = BookingList[position]

        holder.name.text = booking.doctorName
        holder.date.text = booking.getFormattedDate()
        holder.time.text = booking.getFormattedTime()

        if (isValidBookingDate(booking)) {
            holder.itemView.isEnabled = true
            holder.itemView.alpha = 1.0f
        } else {
            // Disable and dim the view for invalid dates
            holder.itemView.isEnabled = false
            holder.itemView.alpha = 0.5f
        }

    }
    override fun getItemCount(): Int {
        return BookingList.size
    }


}
