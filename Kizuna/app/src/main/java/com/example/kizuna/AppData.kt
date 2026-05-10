package com.example.kizuna

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

object AppData {
    private val database = Firebase.database.reference
    private val _profiles = MutableLiveData<List<ChildProfile>>(emptyList())
    val profiles: LiveData<List<ChildProfile>> = _profiles

    private val childIdToCode = mutableMapOf<String, String>()

    init {
        database.child("profiles").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val profileList = mutableListOf<ChildProfile>()
                for (childSnapshot in snapshot.children) {
                    val profile = childSnapshot.getValue(ChildProfile::class.java)
                    profile?.let { profileList.add(it) }
                }
                _profiles.value = profileList
            }
            override fun onCancelled(error: DatabaseError) {}
        })

        database.child("codes").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                childIdToCode.clear()
                for (codeSnapshot in snapshot.children) {
                    val code = codeSnapshot.key
                    val childId = codeSnapshot.getValue(String::class.java)
                    if (code != null && childId != null) {
                        childIdToCode[childId] = code
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }

    fun createProfile(name: String, onComplete: (String) -> Unit) {
        val childId = database.child("profiles").push().key ?: return
        val code = (100000..999999).random().toString()
        val newProfile = ChildProfile(id = childId, name = name)
        database.child("profiles").child(childId).setValue(newProfile)
        database.child("codes").child(code).setValue(childId).addOnSuccessListener { onComplete(code) }
    }

    fun getChildIdByCode(code: String, onResult: (String?) -> Unit) {
        database.child("codes").child(code).get().addOnSuccessListener {
            onResult(it.getValue(String::class.java))
        }.addOnFailureListener {
            onResult(null)
        }
    }

    fun getCodeForChild(childId: String): String? = childIdToCode[childId]
    fun getProfileById(id: String): ChildProfile? = _profiles.value?.find { it.id == id }

    fun addTask(childId: String, title: String, reward: Int, penalty: Int, durationMinutes: Int, isGeneral: Boolean = false) {
        val profile = getProfileById(childId) ?: return
        val dueTime = if (durationMinutes > 0) System.currentTimeMillis() + (durationMinutes * 60 * 1000) else 0L
        val newTask = Task(
            title = title, 
            rewardPoints = reward, 
            penaltyPoints = penalty, 
            dueTimeMillis = dueTime,
            description = if (isGeneral) "GENERAL_TASK" else ""
        )
        val updatedTasks = profile.tasks.toMutableList()
        updatedTasks.add(newTask)
        database.child("profiles").child(childId).child("tasks").setValue(updatedTasks)
    }

    fun completeTask(childId: String, taskId: String) {
        val profile = getProfileById(childId) ?: return
        val tasks = profile.tasks.toMutableList()
        val index = tasks.indexOfFirst { it.id == taskId }
        if (index != -1) {
            val task = tasks[index]
            val isLate = task.dueTimeMillis in 1..System.currentTimeMillis()
            var updatedTask = task.copy(isCompleted = true)
            var newTotalPoints = profile.totalPoints
            if (isLate && !task.isPenaltyApplied) {
                newTotalPoints -= task.penaltyPoints
                updatedTask = updatedTask.copy(isPenaltyApplied = true)
            }
            tasks[index] = updatedTask
            database.child("profiles").child(childId).updateChildren(mapOf("tasks" to tasks, "totalPoints" to newTotalPoints))
        }
    }

    fun approveTask(childId: String, taskId: String) {
        val profile = getProfileById(childId) ?: return
        val tasks = profile.tasks.toMutableList()
        val index = tasks.indexOfFirst { it.id == taskId }
        if (index != -1) {
            val task = tasks[index]
            if (!task.isApproved) {
                // If it's a general task, remove it from all other children first
                if (task.description == "GENERAL_TASK") {
                    _profiles.value?.forEach { otherProfile ->
                        if (otherProfile.id != childId) {
                            val otherTasks = otherProfile.tasks.filter { it.title != task.title || it.rewardPoints != task.rewardPoints }
                            database.child("profiles").child(otherProfile.id).child("tasks").setValue(otherTasks)
                        }
                    }
                }
                tasks[index] = task.copy(isApproved = true)
                val newPoints = profile.totalPoints + task.rewardPoints
                database.child("profiles").child(childId).updateChildren(mapOf("tasks" to tasks, "totalPoints" to newPoints))
            }
        }
    }

    fun requestReward(childId: String, title: String, cost: Int) {
        val profile = getProfileById(childId) ?: return
        val newRequest = RewardRequest(title = title, pointCost = cost)
        val updatedRequests = profile.rewardRequests.toMutableList()
        updatedRequests.add(newRequest)
        database.child("profiles").child(childId).child("rewardRequests").setValue(updatedRequests)
    }

    fun approveReward(childId: String, requestId: String) {
        val profile = getProfileById(childId) ?: return
        val requests = profile.rewardRequests.toMutableList()
        val index = requests.indexOfFirst { it.id == requestId }
        if (index != -1) {
            val request = requests[index]
            if (!request.isApproved && profile.totalPoints >= request.pointCost) {
                requests.removeAt(index) // Remove after approval/redemption
                val newPoints = profile.totalPoints - request.pointCost
                database.child("profiles").child(childId).updateChildren(mapOf("rewardRequests" to requests, "totalPoints" to newPoints))
            }
        }
    }

    fun resetAllData(onComplete: () -> Unit) {
        database.removeValue().addOnSuccessListener {
            onComplete()
        }
    }
}