package com.example.endsemproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.endsemproject.ui.AuthScreen
import com.example.endsemproject.ui.AuthViewModel
import com.example.endsemproject.ui.ChildDashboard
import com.example.endsemproject.ui.ParentDashboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.initUser()
        
        setContent {
            val user by viewModel.userState.collectAsState()
            
            if (user == null) {
                AuthScreen(onSignUp = { email, pass, role ->
                    viewModel.signUp(email, pass, role)
                })
            } else {
                if (user?.role == "parent") {
                    ParentDashboard(user!!)
                } else {
                    ChildDashboard(user!!, onJoinGroup = { code ->
                        viewModel.joinGroup(code)
                    })
                }
            }
        }
    }
}
