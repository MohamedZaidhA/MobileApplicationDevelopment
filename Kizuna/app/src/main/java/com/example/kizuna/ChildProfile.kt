package com.example.kizuna

import java.util.UUID

data class ChildProfile(
    val id: String = UUID.randomUUID().toString(),
    val name: String = "",
    val tasks: List<Task> = emptyList(),
    val rewardRequests: List<RewardRequest> = emptyList(),
    var totalPoints: Int = 0
)