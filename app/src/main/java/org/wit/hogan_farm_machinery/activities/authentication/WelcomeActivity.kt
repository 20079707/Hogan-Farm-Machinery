package org.wit.hogan_farm_machinery.activities.authentication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_welcome.*
import org.wit.hogan_farm_machinery.R
import org.wit.hogan_farm_machinery.activities.navActivities.ListActivity
import org.wit.hogan_farm_machinery.main.MainApp
import kotlinx.android.synthetic.main.activity_welcome.btnWelcomeRegister

class WelcomeActivity : AppCompatActivity(){

    var firebaseUser: FirebaseUser? = null
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        app = application as MainApp

        btnWelcomeRegister.setOnClickListener {
            val intent = Intent(this@WelcomeActivity, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnWelcomeLogin.setOnClickListener {
            val intent = Intent(this@WelcomeActivity, LogInActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    override fun onStart() {
        super.onStart()

        firebaseUser = FirebaseAuth.getInstance().currentUser

        if (firebaseUser != null) {
            val intent = Intent(this@WelcomeActivity, ListActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}