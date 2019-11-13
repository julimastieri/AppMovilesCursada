package com.example.moviles

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_clase2.*

class Clase2 : AppCompatActivity() {

    private val GETRESULT = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clase2)

        addButton.setOnClickListener {
            val intent = Intent(this, Clase2SegundaActivity::class.java)
            val operacion = "+"
            val bundle = Bundle()
            bundle.putString("operation", operacion)
            intent.putExtras(bundle)
            startActivityForResult(intent, GETRESULT)
        }

        subtractionButton.setOnClickListener {
            val intent = Intent(this, Clase2SegundaActivity::class.java)
            val operacion = "-"
            val bundle = Bundle()
            bundle.putString("operation", operacion)
            intent.putExtras(bundle)
            startActivityForResult(intent, GETRESULT)
        }

        multiplicationButton.setOnClickListener {
            val intent = Intent(this, Clase2SegundaActivity::class.java)
            val operacion = "*"
            val bundle = Bundle()
            bundle.putString("operation", operacion)
            intent.putExtras(bundle)
            startActivityForResult(intent, GETRESULT)
        }

        divisionButton.setOnClickListener {
            val intent = Intent(this, Clase2SegundaActivity::class.java)
            val operacion = "/"
            val bundle = Bundle()
            bundle.putString("operation", operacion)
            intent.putExtras(bundle)
            startActivityForResult(intent, GETRESULT)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if ((requestCode == GETRESULT) && (resultCode == Activity.RESULT_OK)) {
            val bundle = data?.extras
            val resultado = bundle?.getString("result")
            valueTextView.text = resultado
        }
    }


    public override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("valueTextView",valueTextView.text.toString())
    }

    public override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val value = savedInstanceState.getString("valueTextView")
        if (value != null)
            valueTextView.text = value
    }
}
