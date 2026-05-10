package com.example.kizuna

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kizuna.databinding.ActivityChildLoginBinding

class ChildLoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChildLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChildLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val enteredCode = binding.etCode.text.toString()
            
            AppData.getChildIdByCode(enteredCode) { childId ->
                if (childId != null) {
                    val intent = Intent(this, ChildDashboardActivity::class.java)
                    intent.putExtra("CHILD_ID", childId)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Invalid Code", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}