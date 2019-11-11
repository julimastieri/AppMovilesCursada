package com.example.moviles
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_clase4.*
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import android.content.IntentFilter
import java.lang.ref.WeakReference


class Clase4 : AppCompatActivity() {

    var  IntentReceiver : ISReceiver = ISReceiver(this)
    var ServiceReciver : SReceiver = SReceiver(this)
    companion object {
        const val FILTER_INTENT_KEY = "Intent Key"
        const val FILTER_SERVICE_KEY = "Service Key"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clase4)

        ISButton.setOnClickListener{
            val intent = Intent(this, C4IntentService::class.java)
            startService(intent)
        }

        SButton.setOnClickListener{
            for (i in 1..4) {
                val intent = Intent(this, C4Service::class.java)
                intent.putExtra("nroThread", i)
                startService(intent)
            }
        }


    }

    override fun onStart() {
        setReceiver()
        super.onStart()
    }

    override fun onStop() {
        unregisterReceiver(IntentReceiver)
        super.onStop()
    }


    private fun setReceiver() {
        IntentReceiver = ISReceiver(this)
        ServiceReciver = SReceiver(this)
        var intentFilter = IntentFilter()
        var serviceFilter = IntentFilter()

        intentFilter.addAction(FILTER_INTENT_KEY)
        serviceFilter.addAction(FILTER_SERVICE_KEY)

        LocalBroadcastManager.getInstance(this).registerReceiver(IntentReceiver, intentFilter)
        LocalBroadcastManager.getInstance(this).registerReceiver(ServiceReciver, serviceFilter)
    }



    class ISReceiver (context:Clase4) : BroadcastReceiver() {

        private val activityReference: WeakReference<Clase4> = WeakReference(context)

        override fun onReceive(context: Context, intent: Intent) {
            val activity = activityReference.get()
            if (activity == null || activity.isFinishing) return

            val processNumber = intent.getStringExtra("ProcessNumber")
            activity.ISResult.text = processNumber
        }
    }

    class SReceiver (context:Clase4) : BroadcastReceiver() {

        private val activityReference: WeakReference<Clase4> = WeakReference(context)

        override fun onReceive(context: Context, intent: Intent) {
            val activity = activityReference.get()
            if (activity == null || activity.isFinishing) return

            val processNumber = intent.getStringExtra("ProcessNumber")
            activity.SResult.text = processNumber
        }
    }

}


