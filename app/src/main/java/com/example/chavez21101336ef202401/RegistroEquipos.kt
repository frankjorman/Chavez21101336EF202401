package com.example.chavez21101336ef202401

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.firestore.FirebaseFirestore

class RegistroEquipos : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro_equipos)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var firestore = FirebaseFirestore.getInstance()

        var txtNombreEquipo:TextView = findViewById(R.id.txtNombreEquipo)
        var txtUrlEscudo:TextView = findViewById(R.id.txt_url_escudo)
        var btnGuardar: Button = findViewById(R.id.btnGuardar)
        var btnRegistrarEnfrenamientos2: Button = findViewById(R.id.btnRegistrarEnfrenamientos2)

        btnGuardar.setOnClickListener {
            var nombreEquipo = txtNombreEquipo.text.toString()
            var urlEscudo = txtUrlEscudo.text.toString()

            var equipo = Equipos(nombre = nombreEquipo, url = urlEscudo)

            firestore.collection("equipos")
                .add(equipo)
                .addOnSuccessListener { documentReference ->
                    Toast.makeText(this, "Equipo guardado", Toast.LENGTH_SHORT).show()
                    txtNombreEquipo.text=""
                    txtUrlEscudo.text = ""
                }
        }

        btnRegistrarEnfrenamientos2.setOnClickListener {
            val intent = Intent(this, RegistroEntrenamiento::class.java)
            startActivity(intent)
        }
    }
}