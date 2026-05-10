package com.example.endsemproject.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.endsemproject.data.User

@Composable
fun AuthScreen(onSignUp: (String, String, String) -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var role by remember { mutableStateOf("parent") }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(value = email, onValueChange = { email = it }, label = { Text("Email") })
        Spacer(modifier = Modifier.height(8.dp))
        TextField(value = password, onValueChange = { password = it }, label = { Text("Password") })
        Spacer(modifier = Modifier.height(16.dp))
        
        Row {
            RadioButton(selected = role == "parent", onClick = { role = "parent" })
            Text("Parent", modifier = Modifier.padding(top = 12.dp))
            Spacer(modifier = Modifier.width(16.dp))
            RadioButton(selected = role == "child", onClick = { role = "child" })
            Text("Child", modifier = Modifier.padding(top = 12.dp))
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { onSignUp(email, password, role) }) {
            Text("Sign Up")
        }
    }
}

@Composable
fun ParentDashboard(user: User) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Parent Dashboard", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Your Group Code:")
        Text(user.groupCode ?: "N/A", style = MaterialTheme.typography.displayMedium)
    }
}

@Composable
fun ChildDashboard(user: User, onJoinGroup: (String) -> Unit) {
    var code by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Child Dashboard", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        
        if (user.groupId == null) {
            Text("Enter Parent Code to Join:")
            TextField(value = code, onValueChange = { code = it }, label = { Text("Group Code") })
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { onJoinGroup(code) }) {
                Text("Join Group")
            }
        } else {
            Text("Joined Group ID: ${user.groupId}")
        }
    }
}
