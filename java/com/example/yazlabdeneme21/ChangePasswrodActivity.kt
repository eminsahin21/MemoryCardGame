package com.example.yazlabdeneme21

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue

class ChangePasswrodActivity : AppCompatActivity() {

    private lateinit var dbReference: DatabaseReference
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_passwrod)

        val username_text: TextView = findViewById(R.id.change_username)
        val password_text: TextView = findViewById(R.id.change_password)

        val button: Button = findViewById(R.id.change_password_button)
        button.setOnClickListener {

            if (!username_text.text.isEmpty() && !password_text.text.isEmpty()) {

                databaseReference = FirebaseDatabase.getInstance().getReference().child("Users")
                databaseReference.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        for (snapshot in snapshot.children) {

                            var name = snapshot.child("user_name").value
                            var password = snapshot.child("user_password").value
                            var userid = snapshot.child("user_id").value


                            println(name)
                            println(password)
                            println(userid)

                            if (name.toString() == username_text.text.toString()){
                                dbReference = FirebaseDatabase.getInstance().getReference().child("Users")
                                dbReference.child(userid.toString()).child("user_password").setValue(password_text.text.toString())
                                    .addOnCompleteListener {
                                        mesajYaz()
                                    }.addOnFailureListener {
                                        //Toast.makeText(this, "Hata!", Toast.LENGTH_SHORT).show()
                                    }
                            }


                        }

                    }

                    override fun onCancelled(error: DatabaseError) {

                    }

                })

            }

        }

    }

    private fun mesajYaz(){
        Toast.makeText(this, "Kullanıcı güncellendi!", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }


}