package com.example.ekyc_playground

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.ImageView
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
import java.util.concurrent.Executors

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
    lateinit var  profile : ImageView

    private lateinit var currentUser: FirebaseUser
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = Firebase.firestore
        setContentView(R.layout.activity_profile)
        profile = findViewById<ImageView>(R.id.imageView4)
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
        val executor = Executors.newSingleThreadExecutor()

        // Once the executor parses the URL
        // and receives the image, handler will load it
        // in the ImageView
        val handler = Handler(Looper.getMainLooper())

        // Initializing the image
        var image: Bitmap? = null

        // Only for Background process (can take time depending on the Internet speed)
        executor.execute {

            // Image URL
            val imageURL = user.ProfileP

            // Tries to get the image and post it in the ImageView
            // with the help of Handler
            try {
                val `in` = java.net.URL(imageURL).openStream()
                image = BitmapFactory.decodeStream(`in`)

                // Only for making changes in UI
                handler.post {
                    profile.setImageBitmap(image)
                }
            }

            // If the URL doesnot point to
            // image or any other kind of failure
            catch (e: Exception) {
                e.printStackTrace()
            }
        }


    }

}