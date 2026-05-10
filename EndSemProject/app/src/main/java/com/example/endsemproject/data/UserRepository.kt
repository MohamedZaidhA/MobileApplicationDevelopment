package com.example.endsemproject.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton
import java.util.UUID

@Singleton
class UserRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore
) {
    suspend fun signUp(email: String, pass: String, role: String): Result<User> {
        return try {
            val authResult = auth.createUserWithEmailAndPassword(email, pass).await()
            val uid = authResult.user?.uid ?: throw Exception("Auth failed")
            
            val user = User(
                uid = uid,
                email = email,
                role = role,
                groupCode = if (role == "parent") generateGroupCode() else null
            )
            
            db.collection("users").document(uid).set(user).await()
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun joinGroup(childUid: String, code: String): Result<Unit> {
        return try {
            val parentQuery = db.collection("users")
                .whereEqualTo("groupCode", code)
                .whereEqualTo("role", "parent")
                .get()
                .await()
            
            if (parentQuery.isEmpty) throw Exception("Invalid code")
            
            val parent = parentQuery.documents.first()
            val groupId = parent.id // Using parent UID as groupId for simplicity
            
            db.collection("users").document(childUid)
                .update("groupId", groupId)
                .await()
            
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun generateGroupCode(): String {
        return UUID.randomUUID().toString().substring(0, 6).uppercase()
    }
    
    suspend fun getCurrentUser(): User? {
        val uid = auth.currentUser?.uid ?: return null
        return db.collection("users").document(uid).get().await().toObject(User::class.java)
    }
}
