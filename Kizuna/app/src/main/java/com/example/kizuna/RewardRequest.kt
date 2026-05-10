package com.example.kizuna

import java.util.UUID

data class RewardRequest(
    val id: String = UUID.randomUUID().toString(),
    val title: String = "",
    val pointCost: Int = 0,
    var isApproved: Boolean = false
)