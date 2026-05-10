package com.example.kizuna

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kizuna.databinding.ItemProfileBinding

class ProfileAdapter(
    private val onManageTasks: (ChildProfile) -> Unit
) : RecyclerView.Adapter<ProfileAdapter.ProfileViewHolder>() {

    private var profiles: List<ChildProfile> = emptyList()

    fun submitList(newProfiles: List<ChildProfile>) {
        profiles = newProfiles
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
        val binding = ItemProfileBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProfileViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
        holder.bind(profiles[position])
    }

    override fun getItemCount(): Int = profiles.size

    inner class ProfileViewHolder(private val binding: ItemProfileBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(profile: ChildProfile) {
            binding.tvProfileName.text = profile.name
            binding.tvProfilePoints.text = "Points: ${profile.totalPoints}"
            binding.tvProfileCode.text = "Code: ${AppData.getCodeForChild(profile.id) ?: "N/A"}"
            binding.btnManageTasks.setOnClickListener { onManageTasks(profile) }
        }
    }
}