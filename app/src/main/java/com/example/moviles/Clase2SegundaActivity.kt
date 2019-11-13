package com.example.moviles

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_clase2_segunda.*
import android.text.Editable
import android.text.TextWatcher
import android.content.Intent
import android.widget.Toast


class Clase2SegundaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clase2_segunda)

        value1.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                value1.error = "No puede ser vacio"
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                value1.error = "No puede ser vacio"
            }
            override fun afterTextChanged(s: Editable) {

                if (value1.text.toString().isEmpty())
                    value1.error = "No puede ser vacio"
                else
                    value1.error = null
            }
        })

        value2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                value2.error = "No puede ser vacio"
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }
            override fun afterTextChanged(s: Editable) {

                if (value2.text.toString().isEmpty())
                    value2.error = "No puede ser vacio"
                else
                    value2.error = null
            }
        })

        //leer operacion a realizar
        val bundle = intent.extras
        val operacion = bundle?.getString("operation")

        operationTextView.text = operacion

        doneButton.setOnClickListener {

           if ( (value1.text.toString().isNotEmpty()) && (value2.text.toString().isNotEmpty()) ) {
               intent = Intent()
               when (operacion) {
                   "+" -> intent.putExtra(
                       "result",
                       (value1.text.toString().toDouble() + value2.text.toString().toDouble()).toString()
                   )
                   "-" -> intent.putExtra(
                       "result",
                       (value1.text.toString().toDouble() - value2.text.toString().toDouble()).toString()
                   )
                   "*" -> intent.putExtra(
                       "result",
                       (value1.text.toString().toDouble() * value2.text.toString().toDouble()).toString()
                   )
                   "/" -> intent.putExtra(
                       "result",
                       (value1.text.toString().toDouble() / value2.text.toString().toDouble()).toString()
                   )
               }

               setResult(RESULT_OK, intent)
               finish()
           } else  {
               Toast.makeText(this@Clase2SegundaActivity, "Deben ingresarse ambos valores", Toast.LENGTH_LONG).show()
           }
        }

    }
}

