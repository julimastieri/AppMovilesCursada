package com.example.moviles

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_clase2.*

class Clase2 : AppCompatActivity() {

    val GET_RESULT = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clase2)

        //pasarle un valor que indique la operacion: FALTA!

        addButton.setOnClickListener(){
            val intent = Intent(this, Clase2SegundaActivity::class.java)
            val operacion :String = "+"
            val bundle : Bundle = Bundle()
            bundle.putString("operation", operacion)
            intent.putExtras(bundle)
            startActivityForResult(intent, GET_RESULT)
        }

        subtractionButton.setOnClickListener(){
            val intent = Intent(this, Clase2SegundaActivity::class.java)
            val operacion :String = "-"
            val bundle : Bundle = Bundle()
            bundle.putString("operation", operacion)
            intent.putExtras(bundle)
            startActivityForResult(intent, GET_RESULT)
        }

        multiplicationButton.setOnClickListener(){
            val intent = Intent(this, Clase2SegundaActivity::class.java)
            val operacion :String = "*"
            val bundle : Bundle = Bundle()
            bundle.putString("operation", operacion)
            intent.putExtras(bundle)
            startActivityForResult(intent, GET_RESULT)
        }

        divisionButton.setOnClickListener(){
            val intent = Intent(this, Clase2SegundaActivity::class.java)
            val operacion :String = "/"
            val bundle : Bundle = Bundle()
            bundle.putString("operation", operacion)
            intent.putExtras(bundle)
            startActivityForResult(intent, GET_RESULT)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if ((requestCode == GET_RESULT) && (resultCode == Activity.RESULT_OK)) {
            var bundle = data?.extras
            var resultado = bundle?.getString("result")
            valueTextView.setText(resultado)
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
            valueTextView.setText(value)
    }
}
