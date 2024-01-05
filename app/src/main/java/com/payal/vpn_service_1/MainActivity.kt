package com.example.destinationapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.payal.vpn_service_1.R

class MainActivity : ComponentActivity() {

    private lateinit var recyclerView: RecyclerView
    lateinit var adapter: LogAdapter
    val ACTION_LOG_URL = "com.example.ACTION_LOG_URL"
    val EXTRA_URL = "extra_url"


    private val urlReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action != null && intent.action!! == ACTION_LOG_URL) {
                // Extract the URL from the intent
                val url = intent.getStringExtra(EXTRA_URL)
                Log.d("taggg", "log called here : $url")
                // Display the received URL
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

        //required to show logs in recycler view
        val filter = IntentFilter(ACTION_LOG_URL)
        registerReceiver(urlReceiver, filter)
    }

    override fun onDestroy() {
        unregisterReceiver(urlReceiver)
        super.onDestroy()
    }
}
