package mx.eml.realtimedbeml.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun NavHostApp(navController: NavHostController, modifier: Modifier) {
    NavHost(
        navController = navController,
        startDestination = "captura"
    ) {
        composable("captura") {
            CapturaDatos()
        }
        composable("lista") {
            ListaAlumnos(modifier)
        }
        composable("busqueda") {
            PantallaBusqueda(modifier)
        }
    }
}