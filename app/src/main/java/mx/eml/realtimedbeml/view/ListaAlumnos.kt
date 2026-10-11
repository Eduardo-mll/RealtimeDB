package mx.eml.realtimedbeml.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import mx.eml.realtimedbeml.viewmodel.AlumnosViewModel
import kotlin.collections.get


@Composable
fun ListaAlumnos(
    modifier: Modifier = Modifier,
    viewModel: AlumnosViewModel = viewModel()
) {

    val alumnos by viewModel.alumnos.collectAsState()

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            "Lista de alumnos",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.fillMaxWidth()
        )
        LazyColumn {
            alumnos.forEach{ alumno ->
                item {
                    Text(text = alumno.nombre)
                }

            }
        }
    }
}