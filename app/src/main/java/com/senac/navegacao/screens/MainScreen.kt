package com.senac.navegacao.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.senac.navegacao.R

data class Travel(val destination: String, val startDate: String, val endDate: String, val budget: Double, val type: TravelType)

enum class TravelType {
    LAZER, TRABALHO
}

@Composable
fun MainScreen(navController: NavController, username: String, email: String) {
    val bottomNavController = rememberNavController()
    var travels by remember { mutableStateOf(listOf(
        Travel("Paris", "01/06/2024", "15/06/2024", 2000.0, TravelType.LAZER),
        Travel("New York", "20/06/2024", "25/06/2024", 1500.0, TravelType.TRABALHO)
    )) }

    fun addTravel(travel: Travel) {
        travels = travels + travel
    }

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
            composable(BottomNavItem.Travel.route) { TravelScreen(navController = bottomNavController, travels) }
            composable(BottomNavItem.About.route) { AboutScreen(username, email) }
            composable("travel_form") { TravelFormScreen(navController = bottomNavController, addTravel = { addTravel(it) }) }
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

sealed class BottomNavItem(var title: String, var icon: ImageVector, var route: String) {
    object Home : BottomNavItem("Home", Icons.Filled.Home, "home")
    object Travel : BottomNavItem("Viagem", Icons.Filled.AddCircle, "travel")
    object About : BottomNavItem("Sobre", Icons.Filled.Face, "about")
}

@Composable
fun HomeScreen(username: String, email: String) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Welcome, $username", style = MaterialTheme.typography.headlineMedium)
        Text(text = "Email: $email", style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun TravelScreen(navController: NavController, travels: List<Travel>) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("travel_form") }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Travel")
            }
        },
        content = { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                items(travels) { travel ->
                    TravelItem(travel)
                }
            }
        }
    )
}

@Composable
fun TravelItem(travel: Travel) {
//    val travelIcon = if (travel.type == TravelType.LAZER) Icons.Default.Face else Icons.Default.Info
    val travelIcon = if (travel.type == TravelType.LAZER) R.drawable.beach_access_24dp_fill0_wght400_grad0_opsz24 else R.drawable.flight_24dp_fill0_wght400_grad0_opsz24
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
            .clickable { /* Handle click */ }
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = travelIcon),
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = travel.destination, style = MaterialTheme.typography.headlineMedium)
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "De ${travel.startDate} Até ${travel.endDate}",
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Valor: $${travel.budget}",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun TravelFormScreen(navController: NavController, addTravel: (Travel) -> Unit) {
    var destination by remember { mutableStateOf("") }
    var startDate by remember { mutableStateOf("") }
    var endDate by remember { mutableStateOf("") }
    var budget by remember { mutableStateOf("") }
    var type by remember { mutableStateOf(TravelType.LAZER) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Nova Viagem", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = destination,
            onValueChange = { destination = it },
            label = { Text("Destino") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = startDate,
            onValueChange = { startDate = it },
            label = { Text("Data Início") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = endDate,
            onValueChange = { endDate = it },
            label = { Text("Data Fim") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = budget,
            onValueChange = { budget = it },
            label = { Text("Valor") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Tipo: ")
            RadioButton(
                selected = type == TravelType.LAZER,
                onClick = { type = TravelType.LAZER }
            )
            Text(text = "LAZER")
            RadioButton(
                selected = type == TravelType.TRABALHO,
                onClick = { type = TravelType.TRABALHO }
            )
            Text(text = "TRABALHO")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            val budgetValue = budget.toDoubleOrNull() ?: 0.0
            val newTravel = Travel(destination, startDate, endDate, budgetValue, type)
            addTravel(newTravel)
            navController.popBackStack()
        }) {
            Text("Salvar")
        }
    }
}

@Composable
fun AboutScreen(username: String, email: String) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Developer: $username $email", style = MaterialTheme.typography.headlineMedium)
    }
}
