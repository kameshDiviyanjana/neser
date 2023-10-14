package com.example.cataract_and_conjunctivitis_detection
import android.icu.util.Calendar
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.example.data.Booking
import com.google.firebase.database.*

class AddBookingActivity : AppCompatActivity() {

    // Firebase database reference
    private lateinit var databaseReference: DatabaseReference

    // UI elements
    private lateinit var spinnerDoctor: Spinner
    private lateinit var datePicker: DatePicker
    private lateinit var timePicker: TimePicker
    private lateinit var btnBookAppointment: Button

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_booking)

        // Initialize Firebase database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("bookings")

        // Initialize UI elements
        spinnerDoctor = findViewById(R.id.spinnerDoctor)
        datePicker = findViewById(R.id.datePicker)
        timePicker = findViewById(R.id.timePicker)
        btnBookAppointment = findViewById(R.id.btnBookAppointment)

        val datePicker = findViewById<DatePicker>(R.id.datePicker)

// Get the current date
        val currentDate = Calendar.getInstance()

// Set the minimum date to the current date
        datePicker.minDate = currentDate.timeInMillis

// Set the OnDateChangedListener
        datePicker.init(
            currentDate.get(Calendar.YEAR),
            currentDate.get(Calendar.MONTH),
            currentDate.get(Calendar.DAY_OF_MONTH)
        ) { _, year, monthOfYear, dayOfMonth ->
            // Handle date changes here, if needed
        }

        datePicker.init(
            currentDate.get(Calendar.YEAR),
            currentDate.get(Calendar.MONTH),
            currentDate.get(Calendar.DAY_OF_MONTH)
        ) { _, year, monthOfYear, dayOfMonth ->
            val selectedDate = Calendar.getInstance()
            selectedDate.set(year, monthOfYear, dayOfMonth)

            // Check if the selected date is in the future
            if (selectedDate.after(currentDate)) {
                // The selected date is in the future, you can proceed
                // Do any other date-related handling here, if needed
            } else {
                // The selected date is not in the future, show an error message or take appropriate action
                Toast.makeText(this@AddBookingActivity, "Invalid date selection.", Toast.LENGTH_SHORT).show()
                // You can also reset the DatePicker to the current date if needed
                datePicker.updateDate(
                    currentDate.get(Calendar.YEAR),

                    currentDate.get(Calendar.MONTH),
                    currentDate.get(Calendar.DAY_OF_MONTH)
                )
            }
        }




        // Set up doctor selection (populate spinner)
        val doctorOptions = arrayOf("DR AJITH ABEYGUNASEKERA", "DR CHAMEERA BANDARA", "DR KAPILA BANDUTHILAKA") // Replace with your doctor data
        val doctorAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, doctorOptions)
        doctorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerDoctor.adapter = doctorAdapter

        btnBookAppointment.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Confirm Booking")
            builder.setMessage("Are you sure you want to confirm this booking?")
            builder.setPositiveButton("Confirm") { dialog, _ ->

                bookAppointment()
                Toast.makeText(this, "Booking successful!", Toast.LENGTH_SHORT).show()
            }
            builder.setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            val dialog = builder.create()
            dialog.show()
        }
    }



    private fun bookAppointment() {
        // Retrieve selected doctor
        val selectedDoctor = spinnerDoctor.selectedItem.toString()

        // Retrieve selected date and time
        val selectedYear = datePicker.year
        val selectedMonth = datePicker.month + 1 // Month is 0-based
        val selectedDay = datePicker.dayOfMonth
        val selectedHour = timePicker.hour
        val selectedMinute = timePicker.minute

        // Create a booking object
        val booking = Booking(selectedDoctor, selectedYear, selectedMonth, selectedDay, selectedHour, selectedMinute)

        // Generate a unique booking ID using Firebase's push()
        val bookingId = databaseReference.push().key

        // Save the booking in the database
        if (bookingId != null) {
            databaseReference.child(selectedDoctor).setValue(booking)
        }

        finish()
    }
}
