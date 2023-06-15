package com.example.yazlabdeneme21

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase


class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference

    var result1 = 0
    var myarray = ArrayList<String>(10)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = Firebase.auth

        val registertext: TextView = findViewById(R.id.textView_register_now)
        registertext.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        val loginbutton: Button = findViewById(R.id.button_login)
        loginbutton.setOnClickListener {
            loginGiris()

        }


        val sifrebutton : TextView = findViewById(R.id.changepasswordtext)
        sifrebutton.setOnClickListener {
            val intent = Intent(this,ChangePasswrodActivity::class.java)
            startActivity(intent)
        }


    }


    private fun loginGiris() {
        val username: EditText = findViewById(R.id.editText_username_login)
        val password: EditText = findViewById(R.id.editText_password_login)

        val inputusername = username.text.toString()
        val inputpassword = password.text.toString()

        if (username.text.isEmpty() || password.text.isEmpty()) {
            Toast.makeText(this, "Tüm alanları doldurunuz!", Toast.LENGTH_SHORT)
                .show()
            return
        }

        if (!inputusername.isEmpty() && !inputpassword.isEmpty()){
            databaseReference = FirebaseDatabase.getInstance().getReference().child("Users")
            databaseReference.addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (snapshot in snapshot.children){

                        var name = snapshot.child("user_name").value
                        var password = snapshot.child("user_password").value

                        println(name)
                        println(password)

                        if (inputusername==name && inputpassword==password){
                            result1 = 1
                            println("Eşleşme var")
                        }
                    }
                    yonlendir()
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })

        }

    }

    private fun yonlendir(){

        if (result1==1){
            Toast.makeText(this,"Giriş Başarılı",Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            result1=0
        }
        else{
            Toast.makeText(this,"Giriş Başarısız",Toast.LENGTH_SHORT).show()
        }

    }
}