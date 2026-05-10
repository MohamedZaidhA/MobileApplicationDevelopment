package com.example.kizuna

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kizuna.databinding.ActivityTaskManagementBinding

class TaskManagementActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTaskManagementBinding
    private lateinit var taskAdapter: TaskAdapter
    private lateinit var rewardAdapter: RewardRequestAdapter
    private var childId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskManagementBinding.inflate(layoutInflater)
        setContentView(binding.root)

        childId = intent.getStringExtra("CHILD_ID")
        val profile = childId?.let { AppData.getProfileById(it) }

        if (profile == null) {
            finish()
            return
        }

        binding.tvChildHeader.text = "Tasks for ${profile.name}"

        // Setup Task List
        taskAdapter = TaskAdapter(isParent = true) { task ->
            childId?.let { AppData.approveTask(it, task.id) }
        }
        binding.rvTasks.layoutManager = LinearLayoutManager(this)
        binding.rvTasks.adapter = taskAdapter

        // Setup Reward Request List
        rewardAdapter = RewardRequestAdapter { request ->
            childId?.let { AppData.approveReward(it, request.id) }
        }
        binding.rvRewardRequests.layoutManager = LinearLayoutManager(this)
        binding.rvRewardRequests.adapter = rewardAdapter

        // Real-time updates
        AppData.profiles.observe(this) { profiles ->
            val updatedProfile = profiles.find { it.id == childId }
            updatedProfile?.let {
                taskAdapter.submitList(it.tasks)
                rewardAdapter.submitList(it.rewardRequests)
                
                binding.tvRewardsLabel.visibility = if (it.rewardRequests.isNotEmpty()) View.VISIBLE else View.GONE
                binding.rvRewardRequests.visibility = if (it.rewardRequests.isNotEmpty()) View.VISIBLE else View.GONE
            }
        }
    }
}