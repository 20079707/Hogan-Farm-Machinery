package org.wit.hogan_farm_machinery.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.wit.hogan_farm_machinery.activities.navActivities.HomeActivity
import org.wit.hogan_farm_machinery.activities.navActivities.ListActivity
import org.wit.hogan_farm_machinery.databinding.ActivityLoginBinding
import org.wit.hogan_farm_machinery.main.MainApp


class LoginView : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        app = application as MainApp
        setSupportActionBar(binding.toolbar)



        signUp.setOnClickListener {
            val email = email.text.toString()
            val password = password.text.toString()
            if (email == "" || password == "") {
                toast("Please provide email + password")
            } else {
                doSignUp(email, password)
            }
        }

        logIn.setOnClickListener {
            val email = email.text.toString()
            val password = password.text.toString()
            if (email == "" || password == "") {
                toast("Please provide email + password")
            } else {
                onLogin(email, password)
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