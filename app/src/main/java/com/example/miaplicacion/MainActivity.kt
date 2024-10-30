package com.example.miaplicacion

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Referencias a los elementos de la interfaz
        val editTextNombre = findViewById<EditText>(R.id.edit_text_nombre)
        val editTextApellido = findViewById<EditText>(R.id.edit_text_apellido)
        val editTextCorreo = findViewById<EditText>(R.id.editTextTextEmailAddress)
        val radioGroupGenero = findViewById<RadioGroup>(R.id.radio_group_genero)
        val spinnerPais = findViewById<Spinner>(R.id.spinner_pais)
        val checkBoxLectura = findViewById<CheckBox>(R.id.check_box_1)
        val checkBoxArte = findViewById<CheckBox>(R.id.check_box_2)
        val checkBoxDeporte = findViewById<CheckBox>(R.id.check_box_4)
        val checkBoxMusica = findViewById<CheckBox>(R.id.check_box_3)
        val seekBarSatisfaccion = findViewById<SeekBar>(R.id.seek_bar_Satisfaccion)
        val tvValorSatisfaccion = findViewById<TextView>(R.id.text_view_valor_satisfaccion)
        val switchBoletin = findViewById<Switch>(R.id.switch_boletin)
        val buttonGuardar = findViewById<Button>(R.id.button_guardar)
        val tvResumenPerfil = findViewById<TextView>(R.id.tvResumenPerfil)

        // Actualización del valor del SeekBar en tiempo real
        seekBarSatisfaccion.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                tvValorSatisfaccion.text = progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // No se necesita implementación aquí
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // No se necesita implementación aquí
            }
        })

        // Manejo del botón de guardar
        buttonGuardar.setOnClickListener {
            // Obtener valores de los campos de texto
            val nombre = editTextNombre.text.toString()
            val apellido = editTextApellido.text.toString()
            val correo = editTextCorreo.text.toString()

            // Obtener el género seleccionado
            val generoSeleccionadoId = radioGroupGenero.checkedRadioButtonId
            val generoSeleccionado = findViewById<RadioButton>(generoSeleccionadoId)?.text.toString()

            // Obtener el país seleccionado
            val paisSeleccionado = spinnerPais.selectedItem.toString()

            // Obtener las preferencias seleccionadas
            val preferencias = mutableListOf<String>()
            if (checkBoxLectura.isChecked) preferencias.add("Lectura")
            if (checkBoxArte.isChecked) preferencias.add("Arte")
            if (checkBoxDeporte.isChecked) preferencias.add("Deporte")
            if (checkBoxMusica.isChecked) preferencias.add("Música")

            // Obtener el valor del SeekBar de satisfacción
            val nivelSatisfaccion = seekBarSatisfaccion.progress

            // Comprobar si el usuario quiere suscribirse al boletín
            val suscripcionBoletin = if (switchBoletin.isChecked) "Sí" else "No"

            // Mostrar el resumen en el TextView
            val resumen = """
                Nombre: $nombre
                Apellido: $apellido
                Correo: $correo
                Género: $generoSeleccionado
                País: $paisSeleccionado
                Preferencias: ${if (preferencias.isNotEmpty()) preferencias.joinToString(", ") else "Ninguna"}
                Nivel de Satisfacción: $nivelSatisfaccion
                Suscripción al boletín: $suscripcionBoletin
            """.trimIndent()

            tvResumenPerfil.text = resumen
        }
    }

    // Manejo del cambio de orientación de la pantalla
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Guardar el estado del TextView del resumen
        val tvResumenPerfil = findViewById<TextView>(R.id.tvResumenPerfil)
        outState.putString("resumenPerfil", tvResumenPerfil.text.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        // Restaurar el estado del TextView del resumen
        val tvResumenPerfil = findViewById<TextView>(R.id.tvResumenPerfil)
        tvResumenPerfil.text = savedInstanceState.getString("resumenPerfil")
    }
}
