package com.example.yazlabdeneme21

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {

    private lateinit var databaseReference: DatabaseReference
    private lateinit var dbRef: DatabaseReference

    var myresult = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        dbRef = FirebaseDatabase.getInstance().getReference("Users")

        val logintext: TextView = findViewById(R.id.textView_login_now)

        logintext.setOnClickListener {
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }

        val registerbutton: Button = findViewById(R.id.button_register)
        registerbutton.setOnClickListener {
            bilgileriKaydet()
        }

    }

    private fun bilgileriKaydet(){
        val email = findViewById<EditText>(R.id.editText_email_register)
        val username = findViewById<EditText>(R.id.editText_username_register)
        val password = findViewById<EditText>(R.id.editText_password_register)

        if (email.text.isEmpty() || password.text.isEmpty() || username.text.isEmpty())
        {
            Toast.makeText(this,"Tüm alanları doldurunuz!",Toast.LENGTH_SHORT)
                .show()
            return
        }

        val input_email = email.text.toString()
        val input_username = username.text.toString()
        val input_password = password.text.toString()
        val user_id = dbRef.push().key!!

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users")
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (snapshot in snapshot.children){

                    var name = snapshot.child("user_name").value
                    var password = snapshot.child("user_password").value

                    println(name)
                    println(password)

                    if (input_username==name){
                        myresult = 1
                    }
                }
                kullaniciKaydet(user_id,input_email,input_username,input_password)
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    private fun kullaniciKaydet(
        user_id: String,
        input_email: String,
        input_username: String,
        input_password: String
    ) {

        if (myresult == 0)
        {
            println("Kullanıcı yok eklendi")
            val user = User(user_id,input_email,input_username,input_password)
            dbRef.child(user_id).setValue(user)
                .addOnCompleteListener{
                    Toast.makeText(this,"Kullanıcı oluşturuldu!",Toast.LENGTH_SHORT).show()
                    val intent = Intent(this,LoginActivity::class.java)
                    startActivity(intent)
                }.addOnFailureListener{
                    Toast.makeText(this,"Hata!",Toast.LENGTH_SHORT).show()
                }

        }
        myresult = 0

    }

}