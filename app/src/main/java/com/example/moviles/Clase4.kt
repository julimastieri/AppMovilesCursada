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

    private var  mReceiver : MyReceiver = MyReceiver(this)
    companion object {
        const val FILTER_KEY = "Key"
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

        BSButton.setOnClickListener{
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
        LocalBroadcastManager.getInstance(applicationContext).unregisterReceiver(mReceiver)

        if(isBound){
            unbindService(boundServiceConnection)
            isBound = false
        }
    }


    private fun setReceiver() {
        mReceiver = MyReceiver(this)

        val intentFilter = IntentFilter()

        intentFilter.addAction(FILTER_KEY)

        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver, intentFilter)
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


    class MyReceiver (context:Clase4) : BroadcastReceiver() {

        private val activityReference: WeakReference<Clase4> = WeakReference(context)

        override fun onReceive(context: Context, intent: Intent) {
            val activity = activityReference.get()
            if (activity == null || activity.isFinishing) return

            val processNumber = intent.getStringExtra("ProcessNumber")
            if ( (processNumber != "") && (processNumber != null) )
                activity.ISResult.text = processNumber


            val serviceResult = intent.getStringExtra("ServiceResult")
            if (serviceResult != null)
                if (activity.SResult.text == "")
                    activity.SResult.text = serviceResult
                else
                    activity.SResult.text = activity.SResult.text.toString() + ", " +serviceResult
        }
    }

    public override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString("ISResult",ISResult.text.toString())
        outState.putString("SResult",SResult.text.toString())
        outState.putString("BSResult",BSResult.text.toString())
    }

    public override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        ISResult.text = savedInstanceState.getString("ISResult")
        SResult.text = savedInstanceState.getString("SResult")
        BSResult.text = savedInstanceState.getString("BSResult")


    }
}


