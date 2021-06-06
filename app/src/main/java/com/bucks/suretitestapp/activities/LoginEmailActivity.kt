package com.bucks.suretitestapp.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.bucks.suretitestapp.R
import com.bucks.suretitestapp.databinding.ActivityLoginEmailBinding
import com.bucks.suretitestapp.viewmodels.LoginEmailViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.concurrent.schedule

@AndroidEntryPoint
class LoginEmailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginEmailBinding
    private val viewModel: LoginEmailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        binding = ActivityLoginEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        animateLogo()
        binding.buttonNext.setOnClickListener {
            checkUser()
        }
    }

    override fun onStart() {
        super.onStart()

        val sharedPref =
            getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)
                ?: return

        val token = sharedPref.getString("token", "").toString()

        checkToken(token)
    }

    private fun checkToken(token: String) {

        lifecycleScope.launchWhenStarted {
            val response = viewModel.isValidToken(token)

            Timer().schedule(2500) {
                runOnUiThread {
                    if (response.message == "Good to go.") {
                        switchToMainActivity()
                    } else {
                        showCardView()
                    }
                }
            }
        }
    }

    private fun animateLogo() {

        val animLogo = AnimationUtils.loadAnimation(this, R.anim.anim_logo)
        binding.imageViewLogo.animation = animLogo
    }

    private fun showCardView() {

        binding.cardView.visibility = View.VISIBLE
        val animCardView = AnimationUtils.loadAnimation(this, R.anim.anim_card_view)
        binding.cardView.animation = animCardView
    }

    private fun checkUser() {

        binding.apply {
            val email = editTextEmail.text.toString().trim()

            if(!isValidEmail(email)) {
                editTextEmail.error = "Email is not valid"
                editTextEmail.requestFocus()
                return
            }

            lifecycleScope.launchWhenStarted {

                when (val response = viewModel.getDoesUserExist(email)) {
                    "User Found." -> {
                        intent = Intent(applicationContext, LoginPasswordActivity::class.java)
                        intent.putExtra("email", email)
                        startActivity(intent)
                    }
                    "User Not Found." -> {
                        intent = Intent(applicationContext, SignUpActivity::class.java)
                        intent.putExtra("email", email)
                        startActivity(intent)
                    }
                    else -> Toast.makeText(applicationContext, response, Toast.LENGTH_LONG).show()
                }
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out)
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun switchToMainActivity() {

        intent = Intent(this@LoginEmailActivity, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}