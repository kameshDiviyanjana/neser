package com.example.cataract_and_conjunctivitis_detection

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.data.userdata
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ResigterUser : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    private lateinit var dbcon : DatabaseReference
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resigter_user)


        var username = findViewById<EditText>(R.id.textname)
        var useremail = findViewById<EditText>(R.id.textemail)
        var userpasswor = findViewById<EditText>(R.id.txtpassword)
          var sigup = findViewById<Button>(R.id.regidterbtn)
               var backbtn = findViewById<Button>(R.id.back)

        backbtn.setOnClickListener {
            val move = Intent(this,MainActivity::class.java)
            startActivity(move)
        }
        sigup.setOnClickListener {

            dbcon = FirebaseDatabase.getInstance().getReference("Users")

            val Username =username.text.toString()
            val Passwored =userpasswor.text.toString()
            val email = useremail.text.toString()


            if(Username.isNotEmpty() && Passwored.isNotEmpty() && email.isNotEmpty()){

                val adduser =   userdata(Username,Passwored,email)

                dbcon.child(Username).setValue(adduser).addOnSuccessListener{

                    val move = Intent(this,MainActivity::class.java)
                    startActivity(move)
                }.addOnFailureListener {

                    Toast.makeText(this,"Sign up failed.Please try Again!", Toast.LENGTH_LONG).show()
                }

            }else{

                Toast.makeText(this,"Enter all the fields", Toast.LENGTH_LONG).show()
            }
        }

    }
}