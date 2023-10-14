package com.example.cataract_and_conjunctivitis_detection

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.Date
import java.util.SimpleTimeZone

class BookingDetails : AppCompatActivity() {
    private lateinit var dbconnection : DatabaseReference
    private lateinit var tvDoctorName : TextView
    private lateinit var tvAppointmentDate: TextView
    private lateinit var tvAppointmentTimeZone: TextView
    private lateinit var btnUpdate : Button
    private lateinit var btnDelete : Button
    private  lateinit var serc : Button

    @SuppressLint("MissingInflatedId", "WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking_details)

         var ids = findViewById<EditText>(R.id.textView3)
        tvDoctorName = findViewById(R.id.tvDoctorName)
        tvAppointmentDate =findViewById(R.id.tvAppointmentDate)
        tvAppointmentTimeZone = findViewById(R.id.tvAppointmentTime)
        serc = findViewById(R.id.find)
        serc.setOnClickListener {
            dbconnection = FirebaseDatabase.getInstance().getReference("Locations")
            val id = ids.text.toString()
            dbconnection.child(id).get().addOnSuccessListener {
                if(it.exists()){
                    val date = it.child("doctorName").value
                    val from =it.child("month").value
                    val passenge =it.child("formattedTime").value

                    tvDoctorName.setText(from.toString())
                    tvAppointmentDate.setText(date.toString())
                    tvAppointmentTimeZone.setText(passenge.toString())



                }
            }.addOnFailureListener {
                Toast.makeText(this,"catch  not succefull bus", Toast.LENGTH_LONG).show()
            }
        }
        btnDelete.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Confirm Delete")
            builder.setMessage("Are you sure you want to delete this Booking?")
            builder.setPositiveButton("Delete") { dialog, _ ->
                deleteRecord(intent.getStringExtra("Booking id").toString())
                dialog.dismiss()
            }
            builder.setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            val dialog = builder.create()
            dialog.show()

        }

    }
    private fun deleteRecord(
        id: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Locations").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this,"Booking Removed", Toast.LENGTH_LONG).show()
            val intent = Intent(this,BookingDetails::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{error ->
            Toast.makeText(this,"error ${error.message}", Toast.LENGTH_LONG).show()
        }
    }
}