package mx.eml.realtimedbeml.viewmodel

import com.google.firebase.database.FirebaseDatabase
import mx.eml.realtimedbeml.model.Alumno

fun grabarDatos(matricula: String, nombre: String, semestre: String) {
    val bd = FirebaseDatabase.getInstance()
    val ref = bd.getReference("alumnos")
    ref.child(matricula).setValue(Alumno(matricula, nombre, semestre))
}

