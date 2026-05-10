package com.example.kizuna

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kizuna.databinding.ActivityChildDashboardBinding
import java.text.NumberFormat
import java.util.Locale

class ChildDashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChildDashboardBinding
    private lateinit var adapter: TaskAdapter
    private var childId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChildDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        childId = intent.getStringExtra("CHILD_ID")
        
        adapter = TaskAdapter(false) { task ->
            childId?.let { AppData.completeTask(it, task.id) }
        }
        binding.rvTasks.layoutManager = LinearLayoutManager(this)
        binding.rvTasks.adapter = adapter

        AppData.profiles.observe(this) { profiles ->
            val profile = profiles.find { it.id == childId }
            profile?.let {
                binding.tvGreeting.text = "Hello there, ${it.name}!"
                binding.tvTotalPoints.text = NumberFormat.getNumberInstance(Locale.US).format(it.totalPoints)
                adapter.submitList(it.tasks)
            }
        }

        binding.btnRedeem.setOnClickListener {
            val title = binding.etRewardTitle.text.toString()
            val cost = binding.etRewardCost.text.toString().toIntOrNull() ?: 0

            if (title.isNotEmpty() && cost > 0 && childId != null) {
                val currentPoints = AppData.getProfileById(childId!!)?.totalPoints ?: 0
                if (currentPoints >= cost) {
                    AppData.requestReward(childId!!, title, cost)
                    Toast.makeText(this, "Reward requested! Waiting for parent approval.", Toast.LENGTH_SHORT).show()
                    binding.etRewardTitle.text?.clear()
                    binding.etRewardCost.text?.clear()
                } else {
                    Toast.makeText(this, "Not enough points!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please enter valid reward details", Toast.LENGTH_SHORT).show()
            }
        }
    }
}