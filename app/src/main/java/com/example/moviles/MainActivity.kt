package com.example.moviles

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Clase1Button.setOnClickListener {
            val clase1 = Intent(this, Clase1::class.java)
            startActivity(clase1)
        }

        Clase2Button.setOnClickListener {
            val clase2 = Intent(this, Clase2::class.java)
            startActivity(clase2)
        }

        Clase3Button.setOnClickListener {
            val clase3 = Intent(this, Clase3::class.java)
            startActivity(clase3)
        }

        Clase4Button.setOnClickListener {
            val clase4 = Intent(this, Clase4::class.java)
            startActivity(clase4)
        }

    }

}
