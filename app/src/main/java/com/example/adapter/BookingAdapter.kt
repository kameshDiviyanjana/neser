package com.example.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cataract_and_conjunctivitis_detection.R
import com.example.data.Booking

class BookingAdapter(private val bookingList: List<Booking>) :
    RecyclerView.Adapter<BookingAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val doctorName: TextView = itemView.findViewById(R.id.tvDoctorName)
        val appointmentDate: TextView = itemView.findViewById(R.id.tvAppointmentDate)
        val appointmentTime: TextView = itemView.findViewById(R.id.tvAppointmentTime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.booking_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val booking = bookingList[position]

        // Bind booking data to UI elements
        holder.doctorName.text = booking.doctorName
        holder.appointmentDate.text = booking.getFormattedDate()
        holder.appointmentTime.text = booking.getFormattedTime()
    }

    override fun getItemCount(): Int {
        return bookingList.size
    }
}
