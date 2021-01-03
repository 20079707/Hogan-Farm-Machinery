package org.wit.hogan_farm_machinery.activities.authentication

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import kotlinx.android.synthetic.main.activity_login.*
import org.wit.hogan_farm_machinery.R
import org.wit.hogan_farm_machinery.activities.primary.HomeActivity
import org.wit.hogan_farm_machinery.databinding.ActivityLoginBinding
import org.wit.hogan_farm_machinery.main.MainApp
import org.wit.hogan_farm_machinery.models.FireStore


class LogInActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var refUsers: DatabaseReference
    private var firebaseUserID: String = ""
    private var fireStore: FireStore? = null

    private lateinit var binding: ActivityLoginBinding
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        app = application as MainApp
        setSupportActionBar(binding.toolbarLogIn)

        app.tractors is FireStore
        fireStore = app.tractors as FireStore

        mAuth = FirebaseAuth.getInstance()
        btnLogin.setOnClickListener {
            logInUser()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_exit, menu)
        return super.onCreateOptionsMenu(menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.exit -> {
                val intent = Intent(this@LogInActivity, WelcomeActivity::class.java)
                startActivity(intent)
                finish()

                return true
            }
        }
        return false
    }

    // determines what to do when a user inputs values into login screen
    private fun logInUser() {
        val email: String = emailLogIn.text.toString()
        val password: String = passwordLogIn.text.toString()

        when {
            //if fields have no values inputted display message
            email == "" -> {
                Toast.makeText(this@LogInActivity, "Please enter your Username.", Toast.LENGTH_LONG)
                    .show()
            }
            password == "" -> {
                Toast.makeText(this@LogInActivity, "Please enter your password.", Toast.LENGTH_LONG)
                    .show()
            }
            else -> {
                //if user is successful
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        if (fireStore != null) {
                            fireStore!!.fetchTractors {
                                val intent = Intent(this@LogInActivity, HomeActivity::class.java)
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                                startActivity(intent)
                                finish()
                            }
                        }
                    } else {
                        //if user inputs wrong details display message
                        Toast.makeText(this@LogInActivity,
                            "Error Message: " + task.exception!!.message.toString(),
                            Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}