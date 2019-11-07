package com.example.moviles

import android.app.IntentService
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_clase4.*
import android.os.Handler
import android.os.ResultReceiver
import androidx.core.provider.FontsContractCompat.Columns.RESULT_CODE_OK
import android.widget.Toast




class Clase4 : AppCompatActivity(), ISReceiver.Receiver{

    var  mReceiver : ISReceiver = ISReceiver(Handler())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clase4)

        ISButton.setOnClickListener{
            mReceiver.setReceiver(this)
            Toast.makeText(this, "Intent Service is running", Toast.LENGTH_LONG).show()
            val intent = Intent(this, C4IntentService::class.java)
            intent.putExtra("ISReceiver", mReceiver)
            startService(intent)
        }


    }


    override fun onReceiveResult(resultCode: Int, resultData: Bundle) {

        if (resultCode == RESULT_CODE_OK) {
            val progress = resultData.getInt("Progress")
            ISResult.text = progress.toString()
        }
    }
}


class ISReceiver(handler: Handler) : ResultReceiver(handler) {

    var mReceiver: Receiver? = null

    interface Receiver {
        fun onReceiveResult(resultCode : Int, resultData: Bundle)
    }

    fun setReceiver(receiver : Receiver) {
        mReceiver = receiver
    }

    override fun onReceiveResult(resultCode: Int, resultData: Bundle) {
        super.onReceiveResult(resultCode, resultData)
        mReceiver?.onReceiveResult(resultCode, resultData)
    }
}


class C4IntentService : IntentService("IntentService") {

    override fun onHandleIntent(intent: Intent?) {

        Toast.makeText(this, "Process start", Toast.LENGTH_LONG).show()

        //if (intent != null) {
            val receiver : ResultReceiver? = intent?.getParcelableExtra("ISReceiver")

            for (i in 1..4) {

                //Envio el nro de proceso que se está procesando
                val data = Bundle()
                data.putInt("Process", i)
                receiver?.send(RESULT_CODE_OK, data)

                println(i)

                Thread.sleep(1000) // duermo por 5 segundos
            }
        //}
    }

}


