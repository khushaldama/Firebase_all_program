package com.khushal.firebasedemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_signup.*

class Signup : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        btnSignup.setOnClickListener(){
            var signupuname=edtsignUsername.text.toString()
            var signuppas=edtsignPassword.text.toString()
            var s1=User(signupuname,signuppas)
            var db=FirebaseDatabase.getInstance().getReference("User")
            db.child(signupuname).setValue(s1).addOnSuccessListener {
                Toast.makeText(this,"Record inserted Successfully", Toast.LENGTH_LONG).show()
                edtsignUsername.setText("")
                edtsignPassword.setText("")
            }
                .addOnFailureListener {
                    Toast.makeText(this,"Error",Toast.LENGTH_LONG).show()
                }

        }
        btnsLogin.setOnClickListener(){
            var intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}