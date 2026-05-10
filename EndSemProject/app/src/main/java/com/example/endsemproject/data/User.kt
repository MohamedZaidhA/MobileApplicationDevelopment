package com.example.endsemproject.data

data class User(
    val uid: String = "",
    val email: String = "",
    val role: String = "", // "parent" or "child"
    val groupId: String? = null,
    val groupCode: String? = null // Only for parents
)
