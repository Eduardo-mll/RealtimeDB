package mx.eml.realtimedbeml.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import mx.eml.realtimedbeml.viewmodel.grabarDatos


@Composable
fun CapturaDatos(modifier: Modifier = Modifier) {
    var matricula by remember { mutableStateOf("") }
    var nombre by remember { mutableStateOf("") }
    var semestre by remember { mutableStateOf("") }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            "Realtime Database",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineMedium,
            modifier = modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(24.dp))
        CampoEntrada("Matrícula:") { valor ->
            matricula = valor
        }
        CampoEntrada("Nombre:") { valor ->
            nombre = valor
        }
        CampoEntrada("Semestre:", teclado = KeyboardType.Number) { valor ->
            semestre = valor
        }
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = { grabarDatos(matricula, nombre, semestre) },
            modifier = modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text("Grabar datos")
        }
    }
}


@Composable
fun CampoEntrada(
    etiqueta: String,
    modifier: Modifier = Modifier,
    teclado: KeyboardType = KeyboardType.Text,
    accion: (valor: String) -> Unit
) {
    var valor by remember { mutableStateOf("") }
    OutlinedTextField(
        value = valor,
        onValueChange = {
            valor = it
            accion(valor)
        },
        label = { Text(etiqueta) },
        keyboardOptions = KeyboardOptions(keyboardType = teclado),
        singleLine = true,
        modifier = modifier.fillMaxWidth()
    )
}