package com.example.moviles

import android.app.IntentService
import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager

class C4IntentService : IntentService("IntentService") {

    override fun onHandleIntent(intent: Intent?) {

        intent?.action = Clase4.FILTER_ACTION_KEY

        for (i in 1..4) {
            //Envio el nro de proceso que se está procesando
            LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(intent!!.putExtra("ProcessNumber", i.toString()))

            Thread.sleep(2000)
        }
    }

}