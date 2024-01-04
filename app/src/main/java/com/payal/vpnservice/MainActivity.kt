package com.payal.vpnservice

import android.net.VpnService
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.button)
        val log = findViewById<Button>(R.id.log)
        val log1 = findViewById<Button>(R.id.log1)

        button.setOnClickListener {
            startVpnService();
        }

        log.setOnClickListener {
            logUrl("https://www.example.com");
        }

        log1.setOnClickListener {
            logUrl("https://www.payal.com");
        }

    }

    private fun startVpnService() {
        val vpnIntent = VpnService.prepare(applicationContext)
        if (vpnIntent != null) {
            startActivityForResult(vpnIntent, 0)
        } else {
            onActivityResult(0, RESULT_OK, null)
        }
    }

    private fun logUrl(url: String) {
        // Log the URL
        UrlLogger.logUrl(this,url)
    }
}