package com.example.fetchmylist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fetchmylist.databinding.ActivityMainBinding
import com.example.fetchmylist.ui.item.ItemActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Thread {
            try {
                Thread.sleep(2000) // Display splash screen for 2 seconds
            } catch (e: InterruptedException) {
                e.printStackTrace()
            } finally {
                // After splash screen, redirect to ItemActivity
                startActivity(Intent(this@MainActivity, ItemActivity::class.java))
                finish()
            }
        }.start()

    }
}