package org.wit.hogan_farm_machinery.activities.authentication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import org.wit.hogan_farm_machinery.activities.navActivities.ListActivity
import org.wit.hogan_farm_machinery.databinding.ActivityRegisterBinding
import org.wit.hogan_farm_machinery.main.MainApp


class RegisterActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var refUsers: DatabaseReference
    private var firebaseUserID: String =""


    private lateinit var binding: ActivityRegisterBinding
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        app = application as MainApp
        setSupportActionBar(binding.toolbarRegister)


        mAuth = FirebaseAuth.getInstance()
        btnRegister.setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser() {
        val username: String = usernameRegister.text.toString()
        val email: String = emailRegister.text.toString()
        val password: String = passwordRegister.text.toString()

        if (username == "") {
            Toast.makeText(this@RegisterActivity, "Please enter your Username.", Toast.LENGTH_LONG).show()
        }
        else if  (email == "") {
            Toast.makeText(this@RegisterActivity, "Please enter your email address.", Toast.LENGTH_LONG).show()
        }
        else if (password == "") {
            Toast.makeText(this@RegisterActivity, "Please enter your password.", Toast.LENGTH_LONG).show()
        }
        else {
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                task ->
                if (task.isSuccessful) {
                    firebaseUserID = mAuth.currentUser!!.uid
                    refUsers = FirebaseDatabase.getInstance().reference.child("Users").child(firebaseUserID)

                    val userHashMap = HashMap<String, Any>()
                    userHashMap["uid"] = firebaseUserID
                    userHashMap["username"] = username
                    userHashMap["profile"] = "https://firebasestorage.googleapis.com/v0/b/hogan-farm-machinery-14f34.appspot.com/o/profile_image.png?alt=media&token=b9048498-aad9-4386-9f68-b0e5fda786ac"
                    userHashMap["cover"] = "https://firebasestorage.googleapis.com/v0/b/hogan-farm-machinery-14f34.appspot.com/o/background_image.jpg?alt=media&token=80399352-0612-472c-99d8-79fb05df7322"
                    userHashMap["status"] = "offline"
                    userHashMap["search"] = username.toLowerCase()
                    userHashMap["facebook"] = "https://m.facebook.com"
                    userHashMap["instagram"] = "https://m.instagram.com"
                    userHashMap["website"] = "https://www.google.com"

                    refUsers.updateChildren(userHashMap)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful){
                                val intent = Intent(this@RegisterActivity, ListActivity::class.java)
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                                startActivity(intent)
                                finish()
                    }
                    }
                }
                else{
                    Toast.makeText(this@RegisterActivity, "Error Message: " + task.exception!!.message.toString(), Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}