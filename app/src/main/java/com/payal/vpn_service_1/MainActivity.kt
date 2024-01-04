package com.payal.vpn_service_1

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : ComponentActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var adapter: LogAdapter

    private val urlReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action != null && intent.action!! == UrlLogger.ACTION_LOG_URL) {
                // Extract the URL from the intent
                val url = intent.getStringExtra(UrlLogger.EXTRA_URL)
                Log.d("taggg", "log called : $url")
                // Add the URL to the adapter
                if (url != null) {
                    adapter.addUrl(url)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        adapter = LogAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

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
