package org.wit.hogan_farm_machinery.activities.authentication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_welcome.*
import org.wit.hogan_farm_machinery.main.MainApp
import kotlinx.android.synthetic.main.activity_welcome.btnWelcomeRegister
import org.wit.hogan_farm_machinery.activities.primary.HomeActivity
import org.wit.hogan_farm_machinery.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {

    private var firebaseUser: FirebaseUser? = null
    lateinit var app: MainApp
    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        app = application as MainApp

        //register button
        btnWelcomeRegister.setOnClickListener {
            val intent = Intent(this@WelcomeActivity, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

        //login button
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
            val intent = Intent(this@WelcomeActivity, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}