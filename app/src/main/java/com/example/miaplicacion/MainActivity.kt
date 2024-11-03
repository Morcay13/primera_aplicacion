package com.example.aplicacion_formulario

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.SeekBar
import android.widget.Spinner
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.miaplicacion.R
//esto es un comentario
class MainActivity : AppCompatActivity() {

    private lateinit var editTextNombre: EditText
    private lateinit var editTextApellido: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var radioGroupGenero: RadioGroup
    private lateinit var spinnerPais: Spinner
    private lateinit var checkBox1: CheckBox
    private lateinit var checkBox2: CheckBox
    private lateinit var checkBox3: CheckBox
    private lateinit var checkBox4: CheckBox
    private lateinit var seekBarSatisfaccion: SeekBar
    private lateinit var textViewValorSatisfaccion: TextView
    private lateinit var switchBoletin: Switch
    private lateinit var buttonGuardar: Button
    private lateinit var textViewResumenPerfil: TextView

    private val paises = arrayOf("España", "Holanda", "Francia", "Inglaterra", "Alemania")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializar las vistas
        initViews()

        // Configurar el Spinner
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, paises)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerPais.adapter = adapter

        // Configurar el SeekBar
        seekBarSatisfaccion.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                textViewValorSatisfaccion.text = progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        // Configurar el botón Guardar
        buttonGuardar.setOnClickListener {
            guardarPerfil()
        }
    }

    private fun initViews() {
        editTextNombre = findViewById(R.id.edit_text_nombre)
        editTextApellido = findViewById(R.id.edit_text_apellido)
        editTextEmail = findViewById(R.id.editTextTextEmailAddress)
        radioGroupGenero = findViewById(R.id.radio_group_genero)
        spinnerPais = findViewById(R.id.spinner_pais)
        checkBox1 = findViewById(R.id.check_box_1)
        checkBox2 = findViewById(R.id.check_box_2)
        checkBox3 = findViewById(R.id.check_box_3)
        checkBox4 = findViewById(R.id.check_box_4)
        seekBarSatisfaccion = findViewById(R.id.seek_bar_Satisfaccion)
        textViewValorSatisfaccion = findViewById(R.id.text_view_valor_satisfaccion)
        switchBoletin = findViewById(R.id.switch_boletin)
        buttonGuardar = findViewById(R.id.button_guardar)
        textViewResumenPerfil = findViewById(R.id.tvResumenPerfil)
    }

    private fun guardarPerfil() {
        // Implementar la lógica para guardar el perfil
        val nombre = editTextNombre.text.toString()
        val apellido = editTextApellido.text.toString()
        val email = editTextEmail.text.toString()
        val genero = when (radioGroupGenero.checkedRadioButtonId) {
            R.id.radio_button_masculino -> "Masculino"
            R.id.radio_button_femenino -> "Femenino"
            R.id.radio_button_otro -> "Otro"
            else -> "No especificado"
        }
        val paisSeleccionado = spinnerPais.selectedItem.toString()
        val intereses = mutableListOf<String>()
        if (checkBox1.isChecked) intereses.add(checkBox1.text.toString())
        if (checkBox2.isChecked) intereses.add(checkBox2.text.toString())
        if (checkBox3.isChecked) intereses.add(checkBox3.text.toString())
        if (checkBox4.isChecked) intereses.add(checkBox4.text.toString())
        val satisfaccion = seekBarSatisfaccion.progress
        val suscripcionBoletin = switchBoletin.isChecked

        val resumen = """
            Nombre: $nombre
            Apellido: $apellido
            Email: $email
            Género: $genero
            País: $paisSeleccionado
            Intereses: ${intereses.joinToString(", ")}
            Satisfacción: $satisfaccion
            Suscrito al boletín: $suscripcionBoletin
        """.trimIndent()

        textViewResumenPerfil.text = resumen
    }

    // Guardar el estado de la interfaz al rotar la pantalla
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("nombre", editTextNombre.text.toString())
        outState.putString("apellido", editTextApellido.text.toString())
        outState.putString("email", editTextEmail.text.toString())
        outState.putInt("genero", radioGroupGenero.checkedRadioButtonId)
        outState.putInt("pais", spinnerPais.selectedItemPosition)
        outState.putBoolean("interes1", checkBox1.isChecked)
        outState.putBoolean("interes2", checkBox2.isChecked)
        outState.putBoolean("interes3", checkBox3.isChecked)
        outState.putBoolean("interes4", checkBox4.isChecked)
        outState.putInt("satisfaccion", seekBarSatisfaccion.progress)
        outState.putBoolean("suscripcion", switchBoletin.isChecked)
    }

    // Restaurar el estado de la interfaz al rotar la pantalla
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        editTextNombre.setText(savedInstanceState.getString("nombre"))
        editTextApellido.setText(savedInstanceState.getString("apellido"))
        editTextEmail.setText(savedInstanceState.getString("email"))
        radioGroupGenero.check(savedInstanceState.getInt("genero"))
        spinnerPais.setSelection(savedInstanceState.getInt("pais"))
        checkBox1.isChecked = savedInstanceState.getBoolean("interes1")
        checkBox2.isChecked = savedInstanceState.getBoolean("interes2")
        checkBox3.isChecked = savedInstanceState.getBoolean("interes3")
        checkBox4.isChecked = savedInstanceState.getBoolean("interes4")
        seekBarSatisfaccion.progress = savedInstanceState.getInt("satisfaccion")
        switchBoletin.isChecked = savedInstanceState.getBoolean("suscripcion")
    }
}
