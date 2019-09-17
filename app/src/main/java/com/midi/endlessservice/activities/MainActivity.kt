package com.midi.endlessservice.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.midi.endlessservice.R
import com.midi.endlessservice.utils.Actions
import com.midi.endlessservice.utils.EndlessService
import com.midi.endlessservice.utils.ServiceState
import com.midi.endlessservice.utils.getServiceState

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val TAG = "ENDLESS_SERVICE"

    private var isStartService: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener {
            toggleStart()
        }
    }

    private fun toggleStart(){
        if (!isStartService){
            isStartService = true
            actionOnService(Actions.START)
        } else {
            isStartService = false
            actionOnService(Actions.STOP)
        }
    }

    private fun actionOnService(action: Actions) {
        if (getServiceState(this) == ServiceState.STOPPED && action == Actions.STOP) return
        Intent(this, EndlessService::class.java).also {
            it.action = action.name
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Log.d(TAG, "Starting the service in >=26 Mode")
                startForegroundService(it)
                return
            }
            Log.d(TAG, "Starting the service in < 26 Mode")
            startService(it)
        }
    }
}
