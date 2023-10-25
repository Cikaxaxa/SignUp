package com.example.signup


import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.TextUtils
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class Login : AppCompatActivity() {

    private lateinit var btnlogin : Button
    private lateinit var etusername : EditText
    private lateinit var etpassword : EditText
    private lateinit var spannablespring : SpannableString
    private lateinit var etsignup : TextView
    private lateinit var db : DbHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        etusername = findViewById(R.id.editTextUsername)
        etpassword = findViewById(R.id.editTextPassword)
        etsignup = findViewById(R.id.txtsignup)
        btnlogin = findViewById(R.id.btnlogin)
        db = DbHelper(this)

        val ss = SpannableString("Don't have an account? Sign Up Here")
        val clickableSpan: ClickableSpan = object : ClickableSpan() {
            override fun onClick(textView: View) {
                val intent= Intent(applicationContext, SignUp::class.java)
                startActivity(intent)
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false
            }
        }
        ss.setSpan(clickableSpan, 23, 35, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)


        etsignup.text = ss
        etsignup.movementMethod = LinkMovementMethod.getInstance()
        etsignup.highlightColor = Color.TRANSPARENT


        btnlogin.setOnClickListener{

            val tusername = etusername.text.toString()
            val tpassword = etpassword.text.toString()

            if (TextUtils.isEmpty(tusername) || TextUtils.isEmpty(tpassword)){
                Toast.makeText(this,"Add username, Password ", Toast.LENGTH_SHORT).show()
            }
            else{
                val checkuser = db.checkuserpass(tusername,tpassword)
                if(checkuser==true){
                    Toast.makeText(this,"Login Successfully", Toast.LENGTH_SHORT).show()
                    val intent= Intent(applicationContext, MainActivity::class.java)
                    startActivity(intent)
                }
                else{
                    Toast.makeText(this,"Wrong Username or Password", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }





}