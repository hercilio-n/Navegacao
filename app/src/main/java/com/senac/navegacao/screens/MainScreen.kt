package com.senac.navegacao.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun MainScreen(navController: NavController, username: String, email: String) {
    val bottomNavController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = bottomNavController)
        }
    ) { innerPadding ->
        NavHost(
            navController = bottomNavController,
            startDestination = BottomNavItem.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(BottomNavItem.Home.route) { HomeScreen(username, email) }
            composable(BottomNavItem.Travel.route) { TravelScreen() }
            composable(BottomNavItem.About.route) { AboutScreen(username, email) }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Travel,
        BottomNavItem.About
    )
    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.title) },
                label = { Text(item.title) },
                selected = currentDestination?.route == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

sealed class BottomNavItem(var title: String, var icon: androidx.compose.ui.graphics.vector.ImageVector, var route: String) {
    object Home : BottomNavItem("Home", Icons.Filled.Home, "home")
    object Travel : BottomNavItem("Travel", Icons.Filled.AddCircle, "travel")
    object About : BottomNavItem("About", Icons.Filled.Face, "about")
}

@Composable
fun HomeScreen(username: String, email: String) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Welcome, $username", style = MaterialTheme.typography.headlineMedium)
        Text(text = "Email: $email", style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun TravelScreen() {
    Surface(modifier = Modifier.fillMaxSize()) {
        Text(text = "Blank screen for Travel", style = MaterialTheme.typography.headlineMedium)
    }
}

@Composable
fun AboutScreen(username: String, email: String) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Developer: $username $email", style = MaterialTheme.typography.headlineMedium)
    }
}
