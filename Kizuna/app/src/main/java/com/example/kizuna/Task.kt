package com.example.kizuna

import java.util.UUID

data class Task(
    val id: String = UUID.randomUUID().toString(),
    val title: String = "",
    val description: String = "",
    val rewardPoints: Int = 0,
    val penaltyPoints: Int = 0,
    val dueTimeMillis: Long = 0,
    var isCompleted: Boolean = false,
    var isApproved: Boolean = false,
    var isPenaltyApplied: Boolean = false
)