package com.example.kizuna

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kizuna.databinding.ItemTaskBinding

class RewardRequestAdapter(
    private val onApprove: (RewardRequest) -> Unit
) : RecyclerView.Adapter<RewardRequestAdapter.RewardViewHolder>() {

    private var requests: List<RewardRequest> = emptyList()

    fun submitList(newRequests: List<RewardRequest>) {
        requests = newRequests
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RewardViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RewardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RewardViewHolder, position: Int) {
        holder.bind(requests[position])
    }

    override fun getItemCount(): Int = requests.size

    inner class RewardViewHolder(private val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(request: RewardRequest) {
            binding.tvTaskTitle.text = request.title
            binding.tvPointsInfo.text = "Cost: ${request.pointCost} PTS"
            binding.tvPenaltyWarning.visibility = View.GONE
            
            binding.btnAction.text = "Approve Reward"
            binding.btnAction.visibility = if (!request.isApproved) View.VISIBLE else View.GONE
            
            binding.btnAction.setOnClickListener { onApprove(request) }
        }
    }
}