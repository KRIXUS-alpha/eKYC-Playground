package com.example.ekyc_playground

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Register : AppCompatActivity() {
    var TAG:String = Register::class.java.simpleName
    val db = Firebase.firestore
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
        val user = hashMapOf(
            "Name" to Name.text.toString(),
            "Address" to PostalAddress.text.toString(),
            "Pan" to Pan.text.toString(),
            "Aadhar" to aadhar.text.toString(),
            "Email" to email.text.toString()
        )

        // Add a new document with a generated ID
        db.collection("users")
            .add(user)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
        Toast.makeText(this,"Registered", Toast.LENGTH_SHORT).show()
    }
}


