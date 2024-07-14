package com.example.chavez21101336ef202401

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject

class RegistroEntrenamiento : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro_entrenamiento)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var firestore = FirebaseFirestore.getInstance()

        var txtLocal:EditText = findViewById<EditText>(R.id.txtLocal)
        var txtEmpate:EditText = findViewById<EditText>(R.id.txtEmpate)
        var txtVisita:EditText = findViewById<EditText>(R.id.txtVisita)
        var btnRegistrarEnfrentamiento:Button = findViewById<Button>(R.id.btnRegistrarEnfrentamiento)
        var btnRegistrarEquipos:Button = findViewById<Button>(R.id.btnRegistrarEquipos)
        var spnEquipoLocal:Spinner = findViewById<Spinner>(R.id.spnEquipoLocal)
        var spnEquipoVisitante:Spinner = findViewById<Spinner>(R.id.spnEquipoVisitante)
        var equipos: MutableList<Equipos> = mutableListOf()
        var equiposNombres:MutableList<String> = mutableListOf()

        firestore.collection("equipos")
            .get()
            .addOnSuccessListener { result ->
                equipos.clear()
                equiposNombres.clear()
                for (document in result) {
                    val equipo = document.toObject<Equipos>()
                    equipos.add(equipo)
                    equiposNombres.add(equipo.nombre)
                }
                val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, equiposNombres)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spnEquipoLocal.adapter = adapter
                spnEquipoVisitante.adapter = adapter
            }

        btnRegistrarEnfrentamiento.setOnClickListener{
            val enfrentamiento = Enfrentamientos(
                equipoLocal = spnEquipoLocal.selectedItem.toString(),
                equipoVisitante = spnEquipoVisitante.selectedItem.toString(),
                cuotaLocal = txtLocal.text.toString().toDouble(),
                cuotaEmpate = txtEmpate.text.toString().toDouble(),
                cuotaVisita = txtVisita.text.toString().toDouble()
            )
            firestore.collection("enfrentamientos")
                .add(enfrentamiento)
                .addOnSuccessListener { documentReference ->
                    Toast.makeText(this, "Enfrentamiento registrado", Toast.LENGTH_SHORT).show()
                    txtLocal.text.clear()
                    txtEmpate.text.clear()
                    txtVisita.text.clear()
                    spnEquipoLocal.setSelection(0)
                    spnEquipoVisitante.setSelection(0)
                }
        }

        btnRegistrarEquipos.setOnClickListener {
            val intent = Intent(this, RegistroEquipos::class.java)
            startActivity(intent)
        }
    }
}