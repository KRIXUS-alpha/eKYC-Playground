package com.example.ekyc_playground

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText

class Register : AppCompatActivity() {
    //var TAG:String = Register::class.java.simpleName

    lateinit var Name: EditText
    lateinit var PostalAddress: EditText
    lateinit var Pan: EditText
    lateinit var aadhar: EditText
    lateinit var email: EditText
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        Name = findViewById(R.id.Name)
        PostalAddress = findViewById(R.id.PostalAddress)
        Pan = findViewById(R.id.pan)
        aadhar = findViewById(R.id.aadhar)
        email = findViewById(R.id.EmailAddress)

    }
    fun clickHandler(view: android.view.View) {
        when (view.id) {
            R.id.btnRegister -> {
                registerUser()
            }
        }
    }
    private fun registerUser() {

    }
}


