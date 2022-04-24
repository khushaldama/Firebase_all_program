package com.khushal.firebasedemo

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_view_all.*
import kotlinx.android.synthetic.main.update_layout.*

class ViewAll : AppCompatActivity() {

    lateinit var arr:ArrayList<User>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_all)
        retriveAll()
    }
    fun retriveAll()
    {
        var db= FirebaseDatabase.getInstance().getReference("User")
        arr=ArrayList<User>()
        db.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists())
                {
                    arr.clear()
                    for(usersnap in snapshot.children)
                    {
                        var key:String=usersnap.key!!
                        val user=usersnap.getValue(User::class.java)
                        var usname:String=user!!.uname
                        var uspass:String=user!!.pass
                        var user1 = User(usname,uspass)
                        //Toast.makeText(applicationContext,user?.fname,Toast.LENGTH_LONG).show()
                        arr.add(user1)
                        refreshRecycle(arr)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }
    fun refreshRecycle(arr:ArrayList<User>)
    {
        var adpter=UserAdapter(this,arr)
        myrecycle.adapter=adpter

    }




    fun delete(position: Int) {

        var user=arr.get(position)
        var key=user.uname
        var db=FirebaseDatabase.getInstance().getReference("User")
        db.child(key).setValue(null).addOnSuccessListener {
            Toast.makeText(this,"Record deleted successfully",Toast.LENGTH_LONG).show()
        }
        retriveAll()
        refreshRecycle(arr)

    }

    fun update(position: Int) {var dialog= Dialog(this)
        dialog.setContentView(R.layout.update_layout)
        dialog.show()
        var user=arr.get(position)
        var key=user.uname
        dialog.edtDialogFname.setText(user.uname)
        dialog.edtDialogLname.setText(user.pass)
        dialog.btnUpdate.setOnClickListener {
            var fname=dialog.edtDialogFname.text.toString()
            var lname=dialog.edtDialogLname.text.toString()
            var uname:String=user!!.uname
            var pass:String=user!!.pass
            var user=User(uname,pass)
            var db=FirebaseDatabase.getInstance().getReference("User")
            db.child(key).setValue(user).addOnSuccessListener {
                Toast.makeText(this,"Record Updated successfully",Toast.LENGTH_LONG).show()
            }
            dialog.dismiss()
        }
        retriveAll()
        refreshRecycle(arr)

    }
}