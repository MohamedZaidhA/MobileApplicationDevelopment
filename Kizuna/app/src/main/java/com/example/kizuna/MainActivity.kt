package com.example.kizuna

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.kizuna.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnParentLogin.setOnClickListener {
            handleParentLogin()
        }

        binding.btnChildLogin.setOnClickListener {
            startActivity(Intent(this, ChildLoginActivity::class.java))
        }
    }

    private fun handleParentLogin() {
        val sharedPrefs = getSharedPreferences("KizunaPrefs", MODE_PRIVATE)
        val savedPassword = sharedPrefs.getString("parent_password", null)

        if (savedPassword == null) {
            // First time setup
            showPasswordDialog("Set Parent Password", "Create a password to secure parent access.") { password ->
                sharedPrefs.edit().putString("parent_password", password).apply()
                navigateToParentDashboard()
            }
        } else {
            // Password verification
            showPasswordDialog("Parent Login", "Enter your password.") { password ->
                if (password == savedPassword) {
                    navigateToParentDashboard()
                } else {
                    Toast.makeText(this, "Incorrect password!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun showPasswordDialog(title: String, message: String, onConfirm: (String) -> Unit) {
        val input = EditText(this).apply {
            inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            hint = "Password"
        }

        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setView(input)
            .setPositiveButton("Confirm") { _, _ ->
                val text = input.text.toString()
                if (text.isNotEmpty()) {
                    onConfirm(text)
                } else {
                    Toast.makeText(this, "Password cannot be empty", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun navigateToParentDashboard() {
        startActivity(Intent(this, ParentActivity::class.java))
    }
}