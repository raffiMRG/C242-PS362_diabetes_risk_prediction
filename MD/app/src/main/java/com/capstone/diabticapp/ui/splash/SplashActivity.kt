package com.capstone.diabticapp.ui.splash

import android.content.Intent
import android.os.Bundle
import android.view.animation.AlphaAnimation
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.capstone.diabticapp.databinding.ActivitySplashBinding
import com.capstone.diabticapp.ui.login.LoginGmailActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startFadeInAnimation(binding.tvTitle, 0)
        startFadeInAnimation(binding.tvSubtitle, 500)

        CoroutineScope(Dispatchers.Main).launch {
            delay(3000L)
            startActivity(Intent(this@SplashActivity, LoginGmailActivity::class.java))
            finish()
        }
    }

    private fun startFadeInAnimation(view: TextView, delay: Long) {
        val fadeIn = AlphaAnimation(0f, 1f).apply {
            duration = 1000
            startOffset = delay
            fillAfter = true
        }
        view.startAnimation(fadeIn)
    }
}
