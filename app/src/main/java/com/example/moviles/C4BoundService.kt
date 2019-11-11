package com.example.moviles

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import java.util.*

class C4BoundService : Service() {
    // Binder given to clients
    private val binder = LocalBinder()
    private val mGenerator = Random()

    /** method for clients  */
    val randomNumber: Int
        get() = mGenerator.nextInt(100)

    inner class LocalBinder : Binder() {
        fun getService(): C4BoundService = this@C4BoundService
    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }
}