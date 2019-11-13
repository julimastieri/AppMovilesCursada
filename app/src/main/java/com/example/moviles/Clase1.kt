package com.example.moviles

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_clase1.*


class Clase1 : AppCompatActivity() {

    var countClick = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clase1)

        incrementButton.setOnClickListener {
            Toast.makeText(this@Clase1, "Sumaste uno", Toast.LENGTH_LONG).show()
            countClick++
            countTextView.text = countClick.toString()
        }

        resetButton.setOnClickListener {
            Toast.makeText(this@Clase1, "Reseteaste el contador", Toast.LENGTH_LONG).show()
            countClick = 0
            countTextView.text = countClick.toString()
        }


        incrementButton.setOnClickListener {
            Toast.makeText(this@Clase1, "Sumaste uno", Toast.LENGTH_LONG).show()
            countClick++
            countTextView.text = countClick.toString()
        }
    }

    public override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString("counter",countTextView.text.toString())
    }

    public override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        val value = savedInstanceState.getString("counter")
        if (value != null) {
            countTextView.text = value
            this.countClick = value.toInt()
        }
    }
}
