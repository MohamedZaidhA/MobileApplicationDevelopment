package com.example.kizuna

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kizuna.databinding.ItemTaskBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TaskAdapter(
    private val isParent: Boolean,
    private val onActionClick: (Task) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    private var tasks: List<Task> = emptyList()

    fun submitList(newTasks: List<Task>) {
        tasks = newTasks
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(tasks[position])
    }

    override fun getItemCount(): Int = tasks.size

    inner class TaskViewHolder(private val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(task: Task) {
            binding.tvTaskTitle.text = task.title
            binding.tvPointsInfo.text = "+${task.rewardPoints} PTS"
            
            if (task.penaltyPoints > 0) {
                binding.tvPenaltyWarning.visibility = View.VISIBLE
                binding.tvPenaltyWarning.text = "⚠ -${task.penaltyPoints} PTS if late"
            } else {
                binding.tvPenaltyWarning.visibility = View.GONE
            }

            if (isParent) {
                binding.btnAction.visibility = if (task.isCompleted && !task.isApproved) View.VISIBLE else View.GONE
                binding.btnAction.text = "Approve"
            } else {
                binding.btnAction.visibility = if (!task.isCompleted) View.VISIBLE else View.GONE
                binding.btnAction.text = "Mark Done"
            }

            binding.btnAction.setOnClickListener { onActionClick(task) }
        }
    }
}