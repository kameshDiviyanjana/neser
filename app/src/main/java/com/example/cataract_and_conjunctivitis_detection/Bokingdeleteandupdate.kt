package com.example.cataract_and_conjunctivitis_detection

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Bokingdeleteandupdate : AppCompatActivity() {
    private lateinit var dbconnections : DatabaseReference
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bokingdeleteandupdate)

        var time = findViewById<EditText>(R.id.times)
        var dtae = findViewById<EditText>(R.id.dates)
        var nam =findViewById<EditText>(R.id.names)
        var ser = findViewById<Button>(R.id.btnser)
        var ids = findViewById<EditText>(R.id.serch)
        var del = findViewById<Button>(R.id.btddeteles)
        var ups = findViewById<Button>(R.id.btnupdate)

        ser.setOnClickListener {
            //Toast.makeText(this,"kkkkkkkkkkkkkkkkkkkk", Toast.LENGTH_LONG).show()
            dbconnections = FirebaseDatabase.getInstance().getReference("bookings")
            val id = ids.text.toString()
            dbconnections.child(id).get().addOnSuccessListener {
                if(it.exists()){
                    val date = it.child("doctorName").value
                    val from =it.child("formattedDate").value
                    val passenge =it.child("formattedTime").value

                    nam.setText(from.toString())
                    dtae.setText(date.toString())
                    time.setText(passenge.toString())



                }
            }.addOnFailureListener {
                Toast.makeText(this,"catch  not succefull bus", Toast.LENGTH_LONG).show()
            }

        }

        del.setOnClickListener {


            val id = ids.text.toString()

            dbconnections = FirebaseDatabase.getInstance().getReference("bookings")
            dbconnections.child(id).removeValue().addOnSuccessListener {
                Toast.makeText(this,"failes fffffffffffffdelete", Toast.LENGTH_LONG).show()
                time.text.clear()
                dtae.text.clear()
                nam.text.clear()
            }.addOnFailureListener {
                Toast.makeText(this,"failes delete", Toast.LENGTH_LONG).show()
            }
        }
        ups.setOnClickListener {


        }



    }
}