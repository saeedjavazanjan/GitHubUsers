package com.example.owlestictask.presentation.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.owlestictask.databinding.ActivitySplashBinding
import com.example.owlestictask.presentation.main_activity.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
   var _binding: ActivitySplashBinding?=null
    val binding get() =_binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding= ActivitySplashBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)
        intentToApp()

    }
    fun intentToApp() {
        lifecycleScope.launch(Dispatchers.Main) {
            delay(1500)




            startActivity(Intent(applicationContext, MainActivity::class.java))
            this@SplashActivity.finish()

        }


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}