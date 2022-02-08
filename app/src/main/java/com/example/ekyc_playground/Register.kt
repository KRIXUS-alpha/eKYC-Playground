package com.example.ekyc_playground

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils.isEmpty
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import android.text.TextUtils
import android.util.Patterns
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.lib.ekyc.presentation.ui.document.ExtractDocumentActivity
import com.lib.ekyc.presentation.ui.face.FaceDetectionActivity
import com.lib.ekyc.presentation.utils.base.KYC
import com.lib.ekyc.presentation.utils.base.KYC.Companion.IMAGE_URL
import com.lib.ekyc.presentation.utils.base.KYC.Companion.RESULTS
import com.lib.ekyc.presentation.utils.toast
import java.io.File


class Register : AppCompatActivity() {
    private lateinit var storage: FirebaseStorage
    var TAG:String = Register::class.java.simpleName
    val db = Firebase.firestore
    lateinit var Name: EditText
    lateinit var PostalAddress: EditText
    lateinit var Pan: EditText
    lateinit var aadhar: EditText
    lateinit var email: EditText
    lateinit var register: Button
    private lateinit var currentUser: FirebaseUser
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        Name = findViewById(R.id.Name)
        PostalAddress = findViewById(R.id.PostalAddress)
        Pan = findViewById(R.id.pan)
        aadhar = findViewById(R.id.aadhar)
        email = findViewById(R.id.EmailAddress)
        register = findViewById(R.id.btnRegister)
        auth = Firebase.auth
        currentUser = auth.currentUser!!
        storage = Firebase.storage
        // Create a storage reference from our app


        register.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                if(checkDataEntered()){
                    registerUser()
                }
            }

            private fun checkDataEntered(): Boolean {
                var flag = false
                if (isEmpty(Name)) {
                    Name.setError("Name is required!");
                    flag = true
                }
                if (isEmpty(Pan)) {
                    Pan.setError("Pan is required!");
                    flag = true

                }
                if (isEmpty(PostalAddress)) {
                    PostalAddress.setError("Address is required!");
                    flag = true

                }
                if (isEmpty(aadhar)) {
                    aadhar.setError("Aadhar is required!");
                    flag = true

                }
                if (isEmail(email) == false) {
                    email.setError("Enter valid email!");
                    flag = true

                }
                return !flag
            }

            fun isEmpty(text: EditText): Boolean {
                val str: CharSequence = text.text.toString()
                return isEmpty(str)
            }

            fun isEmail(text: EditText): Boolean {
                val email: CharSequence = text.text.toString()
                return !isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
            }

        })

        findViewById<View>(R.id.extractDocumentContainer).setOnClickListener {

            ExtractDocumentActivity.start(
                activity = this
            )
//            ExtractDocumentActivity.start(
//                activity = this,
//                mandatoryFields = arrayListOf("name , family")
//            )

        }

        findViewById<View>(R.id.faceDetectionContainer).setOnClickListener {
            FaceDetectionActivity.start(this)
        }

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //face detection
        if (requestCode == KYC.FACE_DETECTION_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                //face detected
                val fileAddress = data?.getStringExtra(IMAGE_URL)
                fileAddress.toast(this)
                var storageRef = storage.reference

                var imagesRef: StorageReference? = storageRef.child("images")
                var file = Uri.fromFile(File(IMAGE_URL))
                val riversRef = storageRef.child("images/${file.lastPathSegment}")
                var uploadTask = riversRef.putFile(file)

                // Register observers to listen for when the download is done or if it fails
                uploadTask.addOnFailureListener {
                    // Handle unsuccessful uploads
                }.addOnSuccessListener { taskSnapshot ->
                    // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
                    // ...
                    val ref = storageRef.child("images/${file.lastPathSegment}")
                    uploadTask = ref.putFile(file)

                    val urlTask = uploadTask.continueWithTask { task ->
                        if (!task.isSuccessful) {
                            task.exception?.let {
                                throw it
                            }
                        }
                        ref.downloadUrl
                    }.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val downloadUri = task.result
                            Log.d(TAG,downloadUri.toString())
                        } else {
                            // Handle failures
                            // ...
                        }
                    }
                }
            }
        }

        //document extraction
        if (requestCode == KYC.SCAN_DOCUMENT_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                val fileAddress = data?.getStringExtra(IMAGE_URL)
                val results = data?.getStringExtra(RESULTS)
                "$fileAddress $results".toast(this)
            }
        }
//        //NFC extraction
//        if (requestCode == KYC.SCAN_PASSPORT_NFC_RESULTS_REQUEST_CODE) {
//            if (resultCode == Activity.RESULT_OK) {
//                val results = data?.getSerializableExtra(RESULTS)
//                "$results".toast(this)
//            }
//        }
    }


// TODO: @Vindhya ClickHandler no longer required?
    fun clickHandler(view: View) {
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
//        db.collection("users")
//            .add(user)
//            .addOnSuccessListener { documentReference ->
//                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
//                startActivity(Intent(this,ProfileActivity::class.java))
//            }
//            .addOnFailureListener { e ->
//                Log.w(TAG, "Error adding document", e)
//            }
        Log.d(TAG, currentUser.uid.toString())
        db.collection("users").document(currentUser.uid.toString()).set(user)
        startActivity(Intent(this,ProfileActivity::class.java))
        Toast.makeText(this,"Registered", Toast.LENGTH_SHORT).show()
    }
}


