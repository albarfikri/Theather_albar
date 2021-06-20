package com.albar.theater.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.albar.theater.R
import com.albar.theater.databinding.ActivitySplashScreenBinding
import com.albar.theater.main.MainActivity

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            animationView.alpha = 0f
            tvSplash.alpha = 0f
            tvSplash.animate().setDuration(3300).alpha(1f)
            animationView.animate().setDuration(3300).alpha(1f).withEndAction {
                val intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
                startActivity(intent)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }

        }
    }
}