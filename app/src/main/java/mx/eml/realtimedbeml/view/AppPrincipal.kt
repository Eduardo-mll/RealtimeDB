package mx.eml.realtimedbeml.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import mx.eml.realtimedbeml.ui.theme.RealtimeDBEmlTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppPrincipal() {

    val navController = rememberNavController()
    val entrada by navController.currentBackStackEntryAsState()
    val enBusqueda = entrada?.destination?.route == "busqueda"

    RealtimeDBEmlTheme {
        Scaffold(modifier = Modifier.fillMaxSize(),
            topBar = {
                if (enBusqueda) {
                    TopAppBar(
                        title = {
                            Text(
                                text = "Eduardo Márquez López",
                                style = MaterialTheme.typography.titleMedium
                            )
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color(0xFF651FFF),
                            titleContentColor = Color.White
                        )
                    )
                }
            },
            floatingActionButton = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.End
                ) {
                    FloatingActionButton(onClick = {
                        navController.navigate("busqueda") {
                            launchSingleTop = true
                        }
                    }) {
                        Icon(
                            Icons.Filled.Search,
                            contentDescription = "Buscar alumno"
                        )
                    }
                    FloatingActionButton(onClick = {
                        navController.navigate("lista") {
                            launchSingleTop = true
                        }
                    }) {
                        Icon(
                            Icons.AutoMirrored.Filled.List,
                            contentDescription = "Lista alumnos"
                        )
                    }
                }
            }) { innerPadding ->
            NavHostApp(
                navController,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}