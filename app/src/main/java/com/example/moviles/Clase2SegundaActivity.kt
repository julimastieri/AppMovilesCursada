package com.example.moviles

import android.annotation.SuppressLint
import android.app.Activity
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
                value1.setError("No puede ser vacio")
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                value1.setError("No puede ser vacio")
            }
            override fun afterTextChanged(s: Editable) {

                if (value1.text.toString().length <= 0)
                    value1.setError("No puede ser vacio")
                else
                    value1.setError(null)
            }
        })

        value2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                value2.setError("No puede ser vacio")
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }
            override fun afterTextChanged(s: Editable) {

                if (value2.text.toString().length <= 0)
                    value2.setError("No puede ser vacio")
                else
                    value2.setError(null)
            }
        })

        //leer operacion a realizar
        var bundle = intent.extras
        var operacion = bundle?.getString("operation")

        operationTextView.setText(operacion)

        doneButton.setOnClickListener(){

           if ( (value1.text.toString().length > 0) && (value2.text.toString().length > 0) ) {
               intent = Intent()
               if (operacion == "+")
                   intent.putExtra(
                       "result",
                       (value1.text.toString().toDouble() + value2.text.toString().toDouble()).toString()
                   )
               else if (operacion == "-")
                   intent.putExtra(
                       "result",
                       (value1.text.toString().toDouble() - value2.text.toString().toDouble()).toString()
                   )
               else if (operacion == "*")
                   intent.putExtra(
                       "result",
                       (value1.text.toString().toDouble() * value2.text.toString().toDouble()).toString()
                   )
               else if (operacion == "/")
                   intent.putExtra(
                       "result",
                       (value1.text.toString().toDouble() / value2.text.toString().toDouble()).toString()
                   )

               setResult(RESULT_OK, intent)
               finish()
           } else  {
               Toast.makeText(this@Clase2SegundaActivity, "Deben ingresarse ambos valores", Toast.LENGTH_LONG).show()
           }
        }

    }

    /*
    public override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString("value1",value1.text.toString())
        outState.putString("value2",value2.text.toString())

    }

    @SuppressLint("ResourceType")
    public override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val v1 = savedInstanceState.getString("value1")
        val v2 = savedInstanceState.getString("value2")


        //ninguna de las dos formas funcionan
        //value1.text = Editable.Factory.getInstance().newEditable(v1)

        //value2.setText(v2)

        }

     */
}

