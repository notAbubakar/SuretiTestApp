package com.bucks.suretitestapp.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.bucks.suretitestapp.R
import com.bucks.suretitestapp.databinding.ActivityLoginPasswordBinding
import com.bucks.suretitestapp.viewmodels.LoginPasswordViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginPasswordBinding
    private val viewModel: LoginPasswordViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        binding = ActivityLoginPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonSignIn.setOnClickListener {
            signInUser()
        }
    }

    private fun signInUser() {

        binding.apply {
            val password = editTextPassword.text.toString().trim()
            val email = intent.extras?.getString("email")

            if (password.isEmpty()) {
                editTextPassword.error = "Password must not be empty"
                editTextPassword.requestFocus()
                return
            }

            lifecycleScope.launchWhenStarted {

                val response = email?.let { viewModel.getUserLogin(it, password) }

                when (response?.message) {

                    "" -> {
                        saveToken(response.data.token)

                        intent = Intent(this@LoginPasswordActivity, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    }
                    else -> {
                        editTextPassword.error = response?.message
                        editTextPassword.requestFocus()
                    }
                }
            }
        }
    }

    private fun saveToken(token: String) {

        val sharedPref =
            getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)
                ?: return
        with(sharedPref.edit()) {
            putString("token", token)
            apply()
        }
    }
}