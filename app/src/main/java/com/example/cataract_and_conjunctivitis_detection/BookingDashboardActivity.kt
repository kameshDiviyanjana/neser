package com.example.cataract_and_conjunctivitis_detection

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class BookingDashboardActivity: AppCompatActivity() {
    private lateinit var btnAddBook : Button
    private lateinit var btnViewBook : Button



    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking_dashboard)

        btnAddBook = findViewById(R.id.btnAddBook)
        btnViewBook = findViewById(R.id.btnViewBook)
        var d  = findViewById<Button>(R.id.seen)

         d.setOnClickListener {
             val intent = Intent(this, Bokingdeleteandupdate::class.java)
             startActivity(intent)

         }
        btnViewBook.setOnClickListener {

            val intent = Intent(this, ViewBookingActivity::class.java)
            startActivity(intent)
        }

        btnAddBook.setOnClickListener {

            val intent = Intent(this, AddBookingActivity::class.java)
            startActivity(intent)
        }
    }

}