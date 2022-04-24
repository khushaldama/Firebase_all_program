package com.khushal.firebasedemo

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var arr: ArrayList<User>
    var flag = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var ss : SharedPreferences =getSharedPreferences("MyPref", MODE_PRIVATE)
        var st = ss.getString("user","")



        btnlLogin.setOnClickListener() {
            var uname = edtEmail.text.toString()
            var pass = edtPass.text.toString()
            //var mainf=edtmainf.text.toString()
            var user = User(uname, pass)
            var db = FirebaseDatabase.getInstance().getReference("User")

            arr = ArrayList<User>()
            /*db.child(mainf).setValue(user).addOnSuccessListener {
                Toast.makeText(this,"Record inserted Successfully", Toast.LENGTH_LONG).show()
                edtsignUsername.setText("")
                edtsignPassword.setText("")
                edtmainf.setText("")
            }
                .addOnFailureListener {
                    Toast.makeText(this,"Error",Toast.LENGTH_LONG).show()
                }*/

            arr = ArrayList<User>()
            db.addValueEventListener(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {

                        for (usersnap in snapshot.children) {
                            val user = usersnap.getValue(User::class.java)
                            if (TextUtils.isEmpty(uname) || TextUtils.isEmpty(pass)) {
                                Toast.makeText(
                                    applicationContext,
                                    "PLEASE ENTER THE USERNAME / PASSWORD CORRECTLY",
                                    Toast.LENGTH_LONG
                                ).show()
                            } else {
                                if (uname.equals("${user!!.uname}") && pass.equals("${user!!.pass}")) {
                                    var preference = getSharedPreferences("Mypref", MODE_PRIVATE)
                                    var editor = preference.edit()
                                    editor.putString("user", uname)
                                    Toast.makeText(
                                        applicationContext,
                                        "Login Successfully",
                                        Toast.LENGTH_LONG
                                    )
                                        .show()

                                    var intent = Intent(applicationContext, ViewAll::class.java)
                                    startActivity(intent)
                                    finish()
                                    flag = true
                                }

                            }
                        }
                        if (flag == false) {
                            Toast.makeText(
                                applicationContext,
                                "Invalid Username and Password",
                                Toast.LENGTH_LONG
                            ).show()
                        }

                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })


        }

        btnSignPage.setOnClickListener {
            var intent = Intent(this, Signup::class.java)
            startActivity(intent)


        }
    }
}