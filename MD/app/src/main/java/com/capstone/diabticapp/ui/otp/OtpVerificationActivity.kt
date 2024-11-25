package com.capstone.diabticapp.ui.otp

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.capstone.diabticapp.MainActivity
import com.capstone.diabticapp.data.AuthRepository
import com.capstone.diabticapp.data.pref.UserPreference
import com.capstone.diabticapp.data.pref.dataStore
import com.capstone.diabticapp.databinding.ActivityOtpVerificationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.launch

class OtpVerificationActivity : AppCompatActivity() {

//    private lateinit var binding: ActivityOtpVerificationBinding
//    private lateinit var verificationId: String
//    private lateinit var phoneNumber: String
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        binding = ActivityOtpVerificationBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        verificationId = intent.getStringExtra("verificationId") ?: ""
//        phoneNumber = intent.getStringExtra("phoneNumber") ?: ""
//
//        if (phoneNumber.isEmpty()) {
//            Toast.makeText(this, "Invalid phone number. Please try again.", Toast.LENGTH_SHORT).show()
//            finish()
//            return
//        }
//
//        setupOtpInputs()
//
//        binding.btnSubmit.setOnClickListener {
//            val otp = getOtpFromInputs()
//            if (otp.length == 6) {
//                verifyOtp(otp)
//            } else {
//                Toast.makeText(this, "Please enter a valid 6-digit OTP.", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
//
//    private fun setupOtpInputs() {
//        val otpInputs = arrayOf(
//            binding.etOtp1,
//            binding.etOtp2,
//            binding.etOtp3,
//            binding.etOtp4,
//            binding.etOtp5,
//            binding.etOtp6
//        )
//
//        for (i in otpInputs.indices) {
//            otpInputs[i].addTextChangedListener(object : TextWatcher {
//                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
//                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
//                override fun afterTextChanged(s: Editable?) {
//                    if (s?.length == 1 && i < otpInputs.size - 1) {
//                        otpInputs[i + 1].requestFocus()
//                    } else if (s?.length == 0 && i > 0) {
//                        otpInputs[i - 1].requestFocus()
//                    }
//                }
//            })
//        }
//    }
//
//    private fun getOtpFromInputs(): String {
//        return binding.etOtp1.text.toString().trim() +
//                binding.etOtp2.text.toString().trim() +
//                binding.etOtp3.text.toString().trim() +
//                binding.etOtp4.text.toString().trim() +
//                binding.etOtp5.text.toString().trim() +
//                binding.etOtp6.text.toString().trim()
//    }
//
//    private fun verifyOtp(otp: String) {
//        val credential = PhoneAuthProvider.getCredential(verificationId, otp)
//        signInWithPhoneAuthCredential(credential)
//    }
//
//    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
//        FirebaseAuth.getInstance().signInWithCredential(credential)
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    Toast.makeText(this, "OTP verified successfully!", Toast.LENGTH_SHORT).show()
//                    lifecycleScope.launch {
//                        val repository = AuthRepository(UserPreference.getInstance(applicationContext.dataStore))
//                        repository.verifyOtp()
//
//                        // Navigate to Home after verification
//                        navigateToHome()
//                    }
//                } else {
//                    Toast.makeText(this, "Verification failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
//                }
//            }
//    }
//
//    private fun navigateToHome() {
//        val intent = Intent(this, MainActivity::class.java)
//        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//        startActivity(intent)
//        finish()
//    }
}
