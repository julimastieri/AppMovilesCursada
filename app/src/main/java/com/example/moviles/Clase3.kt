package com.example.moviles

import android.annotation.SuppressLint
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_clase2.*
import kotlinx.android.synthetic.main.activity_clase2_segunda.*
import kotlinx.android.synthetic.main.activity_clase3.*
import java.lang.ref.WeakReference
import android.R.id.edit
import android.content.Context
import android.content.SharedPreferences
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T



class Clase3 : AppCompatActivity() {

    val TAG = "Clase3"
    var active = false
    var counter = 0
    val sharedPrefFile = "com.example.moviles.COUNTER_INFO"
    //var mPreferences = getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)

    //agregar sharedPreferences que guarde estado del contador al cerrar y volver a abrir la app

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clase3)

        val mPreferences = getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)

        counter = mPreferences.getInt("counter", 0)
        counterText.text = counter.toString()

        stop.isEnabled = false

        start.setOnClickListener {
            val counterAsync = counterAsyncTask(this)
            counterAsync.execute()
        }

        stop.setOnClickListener {
            active = false
            stop.isEnabled = false
        }

        resetButton.setOnClickListener{
            counter = 0
            counterText.text = counter.toString()
        }


    }


        class counterAsyncTask internal constructor(context: Clase3) : AsyncTask<Void, Int, Void>()
        //class counterAsyncTask(private var activity: Clase3?) : AsyncTask<Void, Int, Void>()
        {

                private val activityReference: WeakReference<Clase3> = WeakReference(context)

                @SuppressLint("WrongThread")
                override fun doInBackground(vararg params: Void): Void? {

                    val activity = activityReference.get()
                    if (activity == null || activity.isFinishing) return null

                    var step = activity.stepInput.text.toString()

                    if (step.isEmpty())
                        step = 1.toString() // por defecto el step es 1

                    val time = step.toLong().times(1000) //convierto a long y lo multiplico por 1000 para pasarlo a millis

                    while(activity.active) {
                        time.let { Thread.sleep(time) }
                        publishProgress(activity.counter++) // Calls onProgressUpdate()
                    }

                    return null
                }

                override fun onProgressUpdate(vararg values: Int?) {

                    val activity = activityReference.get()
                    if (activity == null || activity.isFinishing) return

                    activity.counterText.text = values[0].toString()
                }

                override fun onPreExecute() {
                    val activity = activityReference.get()
                    if (activity == null || activity.isFinishing) return

                    activity.stop.isEnabled = true
                    activity.active = true

                }

            }


    public override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString("start",active.toString())
        stop.isEnabled = false
        active = false
    }

    public override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        var v1 = savedInstanceState.getString("start")

        if (v1 == "true")
            start.callOnClick()
    }


    override fun onPause() {
        super.onPause()
        val mPreferences = getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        val preferencesEditor = mPreferences.edit()
        preferencesEditor.putInt("counter", counter)
        preferencesEditor.apply()
    }

}
