package com.example.cataract_and_conjunctivitis_detection

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.adapter.ViewBookingAdapter
import com.example.data.Booking
import com.google.firebase.database.*

class ViewBookingActivity : AppCompatActivity() {

    private lateinit var dbref : DatabaseReference
    private lateinit var bookingRecyclerView : RecyclerView
    private lateinit var bookingArrayList : ArrayList<Booking>




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_bookings)

        bookingRecyclerView = findViewById(R.id.doctorRecyclerView)
        bookingRecyclerView.layoutManager = LinearLayoutManager(this)
        bookingRecyclerView.setHasFixedSize(true)

        bookingArrayList = arrayListOf<Booking>()
        getUserData()


    }

    private fun getUserData(){

        dbref = FirebaseDatabase.getInstance().getReference("bookings")
        dbref.addValueEventListener(object : ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {

                bookingArrayList.clear()
                if(snapshot.exists()){

                    for (userSnapshot in snapshot.children){

                        val booking = userSnapshot.getValue(Booking::class.java)
                        bookingArrayList.add(booking!!)

                    }

                    bookingRecyclerView.adapter = ViewBookingAdapter(bookingArrayList)
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }



}