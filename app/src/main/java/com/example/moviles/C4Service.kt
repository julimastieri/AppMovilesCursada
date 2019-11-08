package com.example.moviles

import android.app.Service
import android.content.Intent
import android.os.*
import android.util.Log
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager


class C4Service : Service() {

    private var serviceLooper: Looper? = null
    private var serviceHandler: ServiceHandler? = null

    // Handler that receives messages from the thread
    private inner class ServiceHandler(looper: Looper) : Handler(looper) {

        override fun handleMessage(msg: Message) {

            try {
                Thread.sleep(2000)
            } catch (e: InterruptedException) {
                Thread.currentThread().interrupt()
            }


            stopSelf(msg.arg1)
        }
    }

    override fun onCreate() {

        for (i in (1..4)) {
            var t : HandlerThread = HandlerThread("ServiceStartArguments", Process.THREAD_PRIORITY_BACKGROUND)
            .apply {
                start()

                serviceLooper = looper
                serviceHandler = ServiceHandler(looper)
            }
            println(serviceHandler.toString())
        }
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {

        Toast.makeText(this, "service starting "+ startId, Toast.LENGTH_SHORT).show()


        intent.action = Clase4.FILTER_ACTION_KEY
        LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(intent.putExtra("ProcessNumber", startId.toString()))

        serviceHandler?.obtainMessage()?.also { msg ->
            msg.arg1 = startId
            serviceHandler?.sendMessage(msg)
        }

        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onDestroy() {
        Toast.makeText(this, "Service done", Toast.LENGTH_SHORT).show()
    }
}