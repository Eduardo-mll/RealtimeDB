package mx.eml.realtimedbeml.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import mx.eml.realtimedbeml.viewmodel.AlumnosViewModel

private val MoradoBarra = Color(0xFF651FFF)
private val GrisCampo = Color(0xFFE6E6E6)
private val GrisBoton = Color(0xFFD0D0D0)
private val TextoOscuro = Color(0xFF1C1B1F)

@Composable
fun PantallaBusqueda(
    modifier: Modifier = Modifier,
    viewModel: AlumnosViewModel = viewModel()
) {
    var matricula by remember { mutableStateOf("") }
    val alumno by viewModel.alumnoEncontrado.collectAsState()
    val fallo by viewModel.busquedaFallida.collectAsState()
    val matriculaConsultada by viewModel.matriculaConsultada.collectAsState()

    val buscar = {
        if (matricula.isNotBlank()) {
            viewModel.buscarAlumno(matricula)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Busca alumno",
                color = TextoOscuro,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
            ) {
                TextField(
                    value = matricula,
                    onValueChange = { matricula = it },
                    label = { Text("Matricula") },
                    singleLine = true,
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = { buscar() }),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = GrisCampo,
                        unfocusedContainerColor = GrisCampo,
                        focusedIndicatorColor = MoradoBarra,
                        unfocusedIndicatorColor = MoradoBarra,
                        cursorColor = MoradoBarra,
                        focusedLabelColor = Color.Gray,
                        unfocusedLabelColor = Color.Gray,
                        focusedTextColor = TextoOscuro,
                        unfocusedTextColor = TextoOscuro
                    )
                )

                Box(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .width(56.dp)
                        .fillMaxHeight()
                        .background(GrisBoton)
                        .clickable(onClick = buscar),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Buscar",
                        tint = Color(0xFF424242)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            if (alumno != null) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(text = "Matrícula: ${alumno!!.matricula}", color = TextoOscuro)
                    Text(text = "Nombre: ${alumno!!.nombre}", color = TextoOscuro)
                    Text(text = "Semestre: ${alumno!!.semestre}", color = TextoOscuro)
                }
            } else if (fallo) {
                Text(
                    text = "No existe la matrícula: $matriculaConsultada",
                    color = TextoOscuro,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .drawBehind {
                            val y = size.height - 1.dp.toPx()
                            drawLine(
                                color = Color.Red,
                                start = Offset(0f, y),
                                end = Offset(size.width, y),
                                strokeWidth = 2.dp.toPx()
                            )
                        }
                )
            }
        }
    }
}
