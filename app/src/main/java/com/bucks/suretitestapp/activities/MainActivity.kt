package com.bucks.suretitestapp.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.bucks.suretitestapp.R
import com.bucks.suretitestapp.databinding.ActivityMainBinding
import com.bucks.suretitestapp.viewmodels.MainActivityViewModel
import com.github.ramiz.nameinitialscircleimageview.NameInitialsCircleImageView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()

        val sharedPref =
            getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)
                ?: return

        val token = sharedPref.getString("token", "").toString()

        getActiveChecksData(token)
    }

    private fun getActiveChecksData(token: String) {

        lifecycleScope.launchWhenStarted {
            val response = viewModel.getActiveChecks(token)

            binding.apply {

                if (response.data.isNotEmpty()) {
                    textViewName.text = response.data[0].userInfo.firstName
                    textViewCheck.text = response.data[0].checkNo
                    textViewAmount.text = response.data[0].checkAmount.toString()
                    textViewClaim.text = response.data[0].claimNo
                    textViewLoan.text = response.data[0].loannumber
                    textViewPreferredContractor.text = response.data[0].preferredcontractor
                    textViewBranchName.text = response.data[0].branch.branchname
                    textViewLossAddress.text = response.data[0].addressofloss
                    textViewAptSte.text = response.data[0].branch.aptste

                    val firstName = response.data[0].collabortator[0].firstName
                    val lastName = response.data[0].collabortator[0].lastName

                    val initials = "$firstName $lastName"
                        .split(' ')
                        .mapNotNull { it.firstOrNull()?.toString() }
                        .reduce { acc, s -> acc + s }

                    circleImageView.setImageInfo(
                        NameInitialsCircleImageView.ImageInfo
                            .Builder(initials)
                            .setTextColor(R.color.purple_500)
                            .setCircleBackgroundColorRes(R.color.white)
                            .build()
                    )
                } else {
                    Toast.makeText(this@MainActivity, "Data is empty", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}