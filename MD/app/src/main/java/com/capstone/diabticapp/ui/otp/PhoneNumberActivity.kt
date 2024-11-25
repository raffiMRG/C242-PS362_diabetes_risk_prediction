package com.capstone.diabticapp.ui.otp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.capstone.diabticapp.R
import com.capstone.diabticapp.data.AuthRepository
import com.capstone.diabticapp.data.pref.UserPreference
import com.capstone.diabticapp.data.pref.dataStore
import com.capstone.diabticapp.databinding.ActivityPhoneNumberBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class PhoneNumberActivity : AppCompatActivity() {

//    private lateinit var binding: ActivityPhoneNumberBinding
//    private lateinit var verificationId: String
//    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        binding = ActivityPhoneNumberBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        binding.btnGetCode.setOnClickListener {
//            val phoneNumber = "+62${binding.etPhoneNumber.text.toString().trim()}"
//            if(phoneNumber.isNotEmpty()){
//                sendOtp(phoneNumber)
//            }else{
//                Toast.makeText(this, "Please enter your phone number", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
//
//    private fun sendOtp(phoneNumber: String) {
//        val options = PhoneAuthOptions.newBuilder()
//            .setPhoneNumber(phoneNumber)
//            .setTimeout(60L, TimeUnit.SECONDS)
//            .setActivity(this)
//            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
//                    // Automatically verify and sign in
//                    signInWithPhoneAuthCredential(credential)
//                }
//
//                override fun onVerificationFailed(e: FirebaseException) {
//                    // Show error feedback
//                    Toast.makeText(this@PhoneNumberActivity, "Verification failed: ${e.message}", Toast.LENGTH_SHORT).show()
//                }
//
//                override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
//                    this@PhoneNumberActivity.verificationId = verificationId
//                    this@PhoneNumberActivity.resendToken = token
//                    navigateToOtpVerification(verificationId, phoneNumber)
//                }
//            })
//            .build()
//        PhoneAuthProvider.verifyPhoneNumber(options)
//    }
//
//
//    private fun navigateToOtpVerification(verificationId: String, phoneNumber: String) {
//        val intent = Intent(this, OtpVerificationActivity::class.java).apply {
//            putExtra("verificationId", verificationId)
//            putExtra("phoneNumber", phoneNumber)
//        }
//        startActivity(intent)
//    }
//
//
//    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential){
//        // Sign in and save the phone number to UserPreference
//        FirebaseAuth.getInstance().signInWithCredential(credential)
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    Toast.makeText(this, "Phone number verified!", Toast.LENGTH_SHORT).show()
//                    // Save phone number to UserPreference
//                    lifecycleScope.launch {
//                        val phoneNumber = "+62${binding.etPhoneNumber.text.toString().trim()}"
//                        val repository = AuthRepository(UserPreference.getInstance(applicationContext.dataStore))
//                        repository.savePhoneNumber(phoneNumber)
//                    }
//                    finish()
//                } else {
//                    Toast.makeText(this, "Verification failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
//                }
//            }
//    }
}