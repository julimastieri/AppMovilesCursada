package com.example.moviles

import android.app.IntentService
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_clase4.*
import android.os.ResultReceiver
import androidx.core.provider.FontsContractCompat.Columns.RESULT_CODE_OK
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import android.content.IntentFilter
import java.lang.ref.WeakReference
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T









class Clase4 : AppCompatActivity() {

    var  mReceiver : ISReceiver = ISReceiver(this)
    companion object {
        const val FILTER_ACTION_KEY = "any_key"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clase4)

        ISButton.setOnClickListener{
            Toast.makeText(this, "Intent Service is running", Toast.LENGTH_LONG).show()
            val intent = Intent(this, C4IntentService::class.java)
            startService(intent)
        }

        SButton.setOnClickListener{
            Intent(this, C4Service::class.java).also { intent ->
                startService(intent)
            }
        }


    }

    override fun onStart() {
        setReceiver()
        super.onStart()
    }

    override fun onStop() {
        unregisterReceiver(mReceiver)
        super.onStop()
    }


    private fun setReceiver() {
        mReceiver = ISReceiver(this)
        var intentFilter = IntentFilter()

        intentFilter.addAction(FILTER_ACTION_KEY)

        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver, intentFilter)
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
}


