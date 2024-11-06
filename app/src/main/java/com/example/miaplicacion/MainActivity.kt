package com.example.aplicacion_formulario

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
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

abstract class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    lateinit var editTextNombre: EditText
    lateinit var editTextApellido: EditText
    lateinit var editTextEmail: EditText
    lateinit var radioGroupGenero: RadioGroup
    lateinit var spinnerPais: Spinner
    lateinit var checkBox1: CheckBox
    lateinit var checkBox2: CheckBox
    lateinit var checkBox3: CheckBox
    lateinit var checkBox4: CheckBox
    lateinit var seekBarSatisfaccion: SeekBar
    lateinit var textViewValorSatisfaccion: TextView
    lateinit var switchBoletin: Switch
    lateinit var buttonGuardar: Button
    lateinit var textViewResumenPerfil: TextView

    private val paises = arrayOf("España", "Holanda", "Francia", "Inglaterra", "Alemania")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializar las vistas
        initViews()

        //Al adaptador le he pasado como parametro esta actividad y el array de  las ciudades
        //guardado en String
        ArrayAdapter.createFromResource(
            this,
            R.array.ciudades_array,
            android.R.layout.simple_spinner_item
        ).also {adapter ->
            //se indica el layaout para visualizar en dropView
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            //se aplica el spinner al adaptador
            spinnerPais.adapter = adapter
        }
        //fijacion del listener
        spinnerPais.onItemSelectedListener = this

        //indicamos el rango de valor del seekBar
        seekBarSatisfaccion.max = 10
        //fijacion del listener:
        seekBarSatisfaccion.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            //progeso : Int es el valor al ir moviendo el seekBar el usuario, guardamos aqui el valor que queremos tener
            override fun onProgressChanged (SeekBar : SeekBar?, progreso : Int,fromUser: Boolean ){
                //actualizamos el textSatisfaccion:
                textViewValorSatisfaccion.setText("$progreso/10")
            }
            //metodo abstracto que notifica cuando el usuario ha empezado a tocar el seekbar -> no lo uso
            override fun onStartTrackingTouch(p0: SeekBar?) {
            }
            //metodo abstracto que notifica cuando el usuario ha terminado de tocar el seekbar -> no lo uso
            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
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
