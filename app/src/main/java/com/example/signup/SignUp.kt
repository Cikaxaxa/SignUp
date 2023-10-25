package com.example.signup

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SignUp : AppCompatActivity() {

    private lateinit var etusername: EditText
    private lateinit var etnumtel: EditText
    private lateinit var etpassword: EditText
    private lateinit var etpassword2: EditText
    private lateinit var etemail: EditText
    private lateinit var btnsignup: Button
    private lateinit var db: DbHelper


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        etusername = findViewById(R.id.editTextUsername)
        etpassword = findViewById(R.id.edtpass)
        etpassword2 = findViewById(R.id.edtpass2)
        etemail = findViewById(R.id.edtemail)
        etnumtel = findViewById(R.id.edtnumtel)
        btnsignup = findViewById(R.id.btnSignup)
        db = DbHelper(this)
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]"


        btnsignup.setOnClickListener {
            val tusername = etusername.text.toString()
            val tpassword = etpassword.text.toString()
            val tpassword2 = etpassword2.text.toString()
            val temail = etemail.text.toString()
            val savedata = db.insertdata(tusername, tpassword)

            if (TextUtils.isEmpty(tusername) || TextUtils.isEmpty(tpassword) || TextUtils.isEmpty(tpassword2) || TextUtils.isEmpty(temail) || Patterns.EMAIL_ADDRESS.matcher(temail).matches()){
                Toast.makeText(this, "Add username, Password & Confirm Password", Toast.LENGTH_SHORT).show()
            }
            else {
                    if (tpassword == tpassword2) {
                        if (savedata == true) {
                            Toast.makeText(this, "Sign Up Successful", Toast.LENGTH_SHORT).show()
                            val intent = Intent(applicationContext, Login::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this, "User Existed", Toast.LENGTH_SHORT).show()
                        }

                    } else {
                        Toast.makeText(this, "Password Not Match", Toast.LENGTH_SHORT).show()
                    }
            }

        }

    }

}