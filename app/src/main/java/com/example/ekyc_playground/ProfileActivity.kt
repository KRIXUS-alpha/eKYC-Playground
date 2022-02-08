package com.example.ekyc_playground

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.ekyc_playground.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import java.lang.Exception

class ProfileActivity : AppCompatActivity() {
    lateinit var email: String
    lateinit var db: FirebaseFirestore
    lateinit var  user: User
    var TAG:String = ProfileActivity::class.java.simpleName
    lateinit var emailTV: TextView
    lateinit var  panTV : TextView
    lateinit var  nameTV : TextView
    lateinit var  addressTV : TextView
    lateinit var  aadharTV : TextView
    private lateinit var currentUser: FirebaseUser
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = Firebase.firestore
        setContentView(R.layout.activity_profile)
        emailTV = findViewById<TextView>(R.id.Email)
        nameTV = findViewById<TextView>(R.id.Name)
        addressTV = findViewById<TextView>(R.id.Address)
        aadharTV = findViewById<TextView>(R.id.Aadhar)
        panTV = findViewById<TextView>(R.id.Pan)
        auth = Firebase.auth
        currentUser = auth.currentUser!!
        Log.d(TAG,"here")
        getDataFs()
    }

    private fun restoreState() {
        //open the file home_state
        var sharedPreferences = getSharedPreferences("home_state_prefs", MODE_PRIVATE)
        //read the data from the file
        email = sharedPreferences.getString("eml","").toString()
        //set the data into the edittexts
//        etContact.setText(contact)
//        etEmail.setText(email)
    }

    private fun getDataFs() {
        val docRef = db.collection("users").document(currentUser.uid.toString())

        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                    try{
                        user = document.toObject<User>()!!
                        setData()
                    }
                    catch (e:Exception){
                        Log.d(TAG, e.toString())
                    }
//
                } else {
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }
    }

    private fun setData() {
        emailTV.setText(user.Email)
        aadharTV.setText(user.Aadhar)
        addressTV.setText(user.Address)
        nameTV.setText(user.Name)
        panTV.setText(user.Pan)

    }

}