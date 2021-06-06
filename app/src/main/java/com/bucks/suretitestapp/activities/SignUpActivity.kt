package com.bucks.suretitestapp.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.bucks.suretitestapp.R
import com.bucks.suretitestapp.databinding.ActivitySignUpBinding
import com.bucks.suretitestapp.viewmodels.SignUpViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private val viewModel: SignUpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.editTextEmail.setText(intent.extras?.getString("email"))

        binding.buttonCreateAccount.setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser() {

        binding.apply {
            val email = editTextEmail.text.toString().trim()
            val password = editTextPassword.text.toString().trim()
            val firstName = editTextFirstName.text.toString().trim()
            val lastName = editTextLastName.text.toString().trim()
            val userCellNo = editTextCellNo.text.toString().trim()
            val mailingAddress = editTextMailingAddress.text.toString().trim()

            lifecycleScope.launchWhenStarted {

                val response = viewModel.registerUser(
                    email,
                    password,
                    firstName,
                    lastName,
                    userCellNo,
                    mailingAddress
                )

                when (response.message) {
                    "PH user added." -> {
                        saveToken(response.data.token)

                        intent = Intent(this@SignUpActivity, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    }
                    else -> Toast.makeText(applicationContext, response.message, Toast.LENGTH_LONG)
                        .show()
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