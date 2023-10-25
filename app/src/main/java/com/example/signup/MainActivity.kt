package com.example.signup

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var btntopo1: ImageView
    private lateinit var btntopo2:  Button
    private lateinit var btnpelan1:  ImageView
    private lateinit var btnpelan2:  Button
    private lateinit var btnakti1:  ImageView
    private lateinit var btnakti2:  Button
    private lateinit var btnprofile:  ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btntopo2 = findViewById(R.id.btntopo2)
        btnpelan2 = findViewById(R.id.btnpelan2)
        btnakti2 = findViewById(R.id.btnakti2)
        btnprofile = findViewById(R.id.btnprofile)

        btntopo2.setOnClickListener{
            val intent = Intent(applicationContext, Login::class.java)
            startActivity(intent)
        }

        btnpelan2.setOnClickListener{
            val intent = Intent(applicationContext, Login::class.java)
            startActivity(intent)
        }
        btnakti2.setOnClickListener{
            val intent = Intent(applicationContext, Login::class.java)
            startActivity(intent)
        }

        btnprofile.setOnClickListener {
            // your code to perform when the user clicks on the ImageView
            val intent = Intent(applicationContext, Profile::class.java)
            startActivity(intent)
        }

//        btnprofile.setOnClickListener{
//            val intent = Intent(applicationContext, Profile::class.java)
//            startActivity(intent)
//        }


    }




}