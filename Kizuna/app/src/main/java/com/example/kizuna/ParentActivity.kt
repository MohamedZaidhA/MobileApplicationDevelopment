package com.example.kizuna

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kizuna.databinding.ActivityParentBinding

class ParentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityParentBinding
    private lateinit var adapter: ProfileAdapter
    private var selectedChildId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityParentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ProfileAdapter { profile ->
            val intent = Intent(this, TaskManagementActivity::class.java)
            intent.putExtra("CHILD_ID", profile.id)
            startActivity(intent)
        }

        binding.rvProfiles.layoutManager = LinearLayoutManager(this)
        binding.rvProfiles.adapter = adapter

        // Setup Dropdown for assigning tasks
        AppData.profiles.observe(this) { profiles ->
            adapter.submitList(profiles)
            
            val options = mutableListOf("General (All Children)")
            options.addAll(profiles.map { it.name })
            
            val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, options)
            binding.autoCompleteAssign.setAdapter(arrayAdapter)
            
            binding.autoCompleteAssign.setOnItemClickListener { _, _, position, _ ->
                selectedChildId = if (position == 0) "GENERAL" else profiles[position - 1].id
            }
        }

        // Logic for Quick Add Task
        binding.btnAddTask.setOnClickListener {
            val title = binding.etTaskTitle.text.toString()
            val reward = binding.etReward.text.toString().toIntOrNull() ?: 0
            val penalty = binding.etPenalty.text.toString().toIntOrNull() ?: 0
            val duration = binding.etDuration.text.toString().toIntOrNull() ?: 0

            if (title.isNotEmpty()) {
                if (selectedChildId == null) {
                    Toast.makeText(this, "Please select who to assign this task to", Toast.LENGTH_SHORT).show()
                } else {
                    if (selectedChildId == "GENERAL") {
                        AppData.profiles.value?.forEach { child ->
                            AppData.addTask(child.id, title, reward, penalty, duration, isGeneral = true)
                        }
                        Toast.makeText(this, "Task added for all children!", Toast.LENGTH_SHORT).show()
                    } else {
                        AppData.addTask(selectedChildId!!, title, reward, penalty, duration)
                        Toast.makeText(this, "Task added!", Toast.LENGTH_SHORT).show()
                    }
                    clearTaskForm()
                }
            } else {
                Toast.makeText(this, "Please enter a task name", Toast.LENGTH_SHORT).show()
            }
        }

        // Logic for Add New Child
        binding.btnCreateProfile.setOnClickListener {
            val name = binding.etNewChildName.text.toString()
            if (name.isNotEmpty()) {
                AppData.createProfile(name) { code ->
                    Toast.makeText(this, "Profile Created! Code: $code", Toast.LENGTH_LONG).show()
                    binding.etNewChildName.text?.clear()
                }
            } else {
                Toast.makeText(this, "Please enter a child's name", Toast.LENGTH_SHORT).show()
            }
        }

        // Reset Data Logic
        binding.btnReset.setOnClickListener {
            showResetConfirmationDialog()
        }
    }

    private fun showResetConfirmationDialog() {
        AlertDialog.Builder(this)
            .setTitle("Reset All Data?")
            .setMessage("This will permanently delete all profiles, tasks, and reward requests. It will also reset your parent password. This cannot be undone.")
            .setPositiveButton("Reset Everything") { _, _ ->
                AppData.resetAllData {
                    // Clear Password
                    val sharedPrefs = getSharedPreferences("KizunaPrefs", MODE_PRIVATE)
                    sharedPrefs.edit().remove("parent_password").apply()
                    
                    Toast.makeText(this, "App has been reset", Toast.LENGTH_LONG).show()
                    
                    // Restart app to Main Screen
                    val intent = Intent(this, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intent)
                    finish()
                }
            }
            .setNegativeButton("Cancel", null)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()
    }

    private fun clearTaskForm() {
        binding.etTaskTitle.text?.clear()
        binding.etReward.text?.clear()
        binding.etPenalty.text?.clear()
        binding.etDuration.text?.clear()
        binding.autoCompleteAssign.text?.clear()
        selectedChildId = null
    }
}