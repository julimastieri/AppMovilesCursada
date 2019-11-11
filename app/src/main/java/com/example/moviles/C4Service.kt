package com.example.moviles

import android.app.Service
import android.content.Intent
import android.os.*
import android.util.Log
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import kotlin.random.Random


class C4Service : Service() {

    private var serviceLooper: Looper? = null
    private var serviceHandler: ServiceHandler? = null

    // Handler that receives messages from the thread
    private inner class ServiceHandler(looper: Looper) : Handler(looper) {

        override fun handleMessage(msg: Message) {

            try {
                Thread.sleep((Random.nextInt((100000 + 1) - 5000) +  5000).toLong())

            } catch (e: InterruptedException) {
                Thread.currentThread().interrupt()
            }


            stopSelf(msg.arg1)
        }
    }

    override fun onCreate() {

        HandlerThread("ServiceStartArguments", Process.THREAD_PRIORITY_BACKGROUND)
        .apply {
            start()

            serviceLooper = looper
            serviceHandler = ServiceHandler(looper)
        }

    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {

        intent.action = Clase4.FILTER_SERVICE_KEY
        val nroThread = intent.getIntExtra("nroThread", 0)
        LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(intent.putExtra("ProcessNumber", nroThread.toString()))

        println("nroThread: $nroThread")

        HandlerThread("ServiceStartArguments", Process.THREAD_PRIORITY_BACKGROUND)
            .apply {
                start()

                serviceLooper = looper
                serviceHandler = ServiceHandler(looper)
            }


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