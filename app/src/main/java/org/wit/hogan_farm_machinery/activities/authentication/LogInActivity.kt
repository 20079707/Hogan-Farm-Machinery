package org.wit.hogan_farm_machinery.activities.authentication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity
import org.wit.hogan_farm_machinery.activities.HomeActivity
import org.wit.hogan_farm_machinery.activities.ListActivity
import org.wit.hogan_farm_machinery.databinding.ActivityLoginBinding
import org.wit.hogan_farm_machinery.main.MainApp


class LogInActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var refUsers: DatabaseReference
    private var firebaseUserID: String =""


    private lateinit var binding: ActivityLoginBinding
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        app = application as MainApp
        setSupportActionBar(binding.toolbarLogIn)


        mAuth = FirebaseAuth.getInstance()
        btnLogin.setOnClickListener {
            logInUser()
        }

    }

    private fun logInUser() {
        val username: String = usernameLogIn.text.toString()
        val password: String = passwordLogIn.text.toString()

        if (username == "") {
            Toast.makeText(this@LogInActivity, "Please enter your Username.", Toast.LENGTH_LONG).show()
        }
        else if (password == "") {
            Toast.makeText(this@LogInActivity, "Please enter your password.", Toast.LENGTH_LONG).show()
        }
        else {
            mAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener {
                task ->
                if (task.isSuccessful) {

                    val intent = Intent(this@LogInActivity, HomeActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()

                }
                else{
                    Toast.makeText(this@LogInActivity, "Error Message: " + task.exception!!.message.toString(), Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun onLogin(email: String, password: String) {
        startActivity<HomeActivity>()
    }

    private fun doSignUp(email: String, password: String) {
        startActivity<HomeActivity>()
    }
}