package mx.eml.realtimedbeml.viewmodel

import mx.eml.realtimedbeml.model.Alumno

import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


class AlumnosViewModel : ViewModel() {

    private val _alumnos = MutableStateFlow<List<Alumno>>(emptyList())
    val alumnos: StateFlow<List<Alumno>> = _alumnos.asStateFlow()

    private val _alumnoEncontrado = MutableStateFlow<Alumno?>(null)
    val alumnoEncontrado: StateFlow<Alumno?> = _alumnoEncontrado.asStateFlow()

    private val _busquedaFallida = MutableStateFlow(false)
    val busquedaFallida: StateFlow<Boolean> = _busquedaFallida.asStateFlow()

    private val _matriculaConsultada = MutableStateFlow("")
    val matriculaConsultada: StateFlow<String> = _matriculaConsultada.asStateFlow()

    private val database = FirebaseDatabase.getInstance()
    private val ref = database.getReference("alumnos")
    private var listener: ValueEventListener? = null

    init {
        leerAlumnos()
    }

    private fun leerAlumnos() {
        listener = ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val listaTemporal = mutableListOf<Alumno>()
                for (dato in snapshot.children) {
                    val alumno = dato.getValue(Alumno::class.java)
                    if (alumno != null) listaTemporal.add(alumno)
                }
                _alumnos.value = listaTemporal
            }

            override fun onCancelled(error: DatabaseError) {
                println("Error en la lectura: " + error.code)
            }
        })
    }

    fun buscarAlumno(matricula: String) {
        val clave = matricula.trim()
        if (clave.isEmpty()) return

        _matriculaConsultada.value = clave
        _alumnoEncontrado.value = null
        _busquedaFallida.value = false

        val enMemoria = _alumnos.value.find { it.matricula.equals(clave, ignoreCase = true) }
        if (enMemoria != null) {
            _alumnoEncontrado.value = enMemoria
            return
        }

        ref.child(clave).get()
            .addOnSuccessListener { snapshot ->
                if (_matriculaConsultada.value != clave) return@addOnSuccessListener
                val alumno = snapshot.getValue(Alumno::class.java)
                if (snapshot.exists() && alumno != null) {
                    _alumnoEncontrado.value = alumno
                    _busquedaFallida.value = false
                } else {
                    _alumnoEncontrado.value = null
                    _busquedaFallida.value = true
                }
            }
            .addOnFailureListener {
                if (_matriculaConsultada.value != clave) return@addOnFailureListener
                _alumnoEncontrado.value = null
                _busquedaFallida.value = true
                println("Error al buscar: ${it.message}")
            }
    }

    override fun onCleared() {
        super.onCleared()
        listener?.let { ref.removeEventListener(it) }
    }
}
