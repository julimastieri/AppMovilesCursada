package com.example.moviles
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_clase4.*
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import android.content.IntentFilter
import java.lang.ref.WeakReference
import android.content.ComponentName
import android.os.IBinder
import android.content.ServiceConnection


class Clase4 : AppCompatActivity() {

    var  IntentReceiver : ISReceiver = ISReceiver(this)
    var ServiceReciver : SReceiver = SReceiver(this)
    companion object {
        const val FILTER_INTENT_KEY = "Intent Key"
        const val FILTER_SERVICE_KEY = "Service Key"
    }

    var boundService: C4BoundService? = null
    var isBound = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clase4)

        ISButton.setOnClickListener{
            val intent = Intent(this, C4IntentService::class.java)
            startService(intent)
        }

        SButton.setOnClickListener{
            SResult.text = ""
            for (i in 1..4) {
                val intent = Intent(this, C4Service::class.java)
                intent.putExtra("nroThread", i)
                startService(intent)
            }
        }

        BSButton.setOnClickListener(){
            val randomNumber = boundService?.randomNumber
            BSResult.text = randomNumber.toString()

        }

    }

    override fun onStart() {
        super.onStart()
        setReceiver()
        setBoundService()
    }

    override fun onStop() {
        super.onStop()
        //unregisterReceiver(IntentReceiver)
        //unregisterReceiver(ServiceReciver)

        if(isBound){
            unbindService(boundServiceConnection)
            isBound = false
        }
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

    private fun setBoundService(){
        val intent = Intent(this, C4BoundService::class.java)
        startService(intent)
        bindService(intent, boundServiceConnection, Context.BIND_AUTO_CREATE)
    }


    private val boundServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {

            val binderBridge = service as C4BoundService.LocalBinder
            boundService = binderBridge.getService()
            isBound = true
        }

        override fun onServiceDisconnected(name: ComponentName) {
            isBound = false
            boundService = null
        }
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
            if (activity.SResult.text == "")
                activity.SResult.text = processNumber
            else
                activity.SResult.text = activity.SResult.text.toString() + ", " +processNumber
        }
    }

}


