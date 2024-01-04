package com.payal.vpn_service_1

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity


class MainActivity : ComponentActivity() {

    lateinit var textview:TextView

    private val urlReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action != null && intent.action!!.equals(UrlLogger.ACTION_LOG_URL)) {
                // Extract the URL from the intent
                val url = intent.getStringExtra(UrlLogger.EXTRA_URL)

                // Display the received URL
                textview.setText(url)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textview = findViewById<TextView>(R.id.textview)

        // Register the URL receiver
        // Register the URL receiver
        val filter = IntentFilter(UrlLogger.ACTION_LOG_URL)
        registerReceiver(urlReceiver, filter)

    }

    override fun onDestroy() {
        // Unregister the URL receiver to avoid memory leaks
        unregisterReceiver(urlReceiver)
        super.onDestroy()
    }
}
