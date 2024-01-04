package com.payal.vpn_service_1

import android.content.Context
import android.content.Intent
import android.util.Log

object UrlLogger {
    const val ACTION_LOG_URL = "com.example.ACTION_LOG_URL"
    const val EXTRA_URL = "extra_url"

    fun logUrl(context: Context, url: String) {
        // Log the URL
        Log.d("UrlLogger", "URL Logged: $url")

        // Send broadcast to notify the destination app about the logged URL
        sendUrlBroadcast(context, url)
    }

    private fun sendUrlBroadcast(context: Context, url: String) {
        val intent = Intent()
        intent.action = ACTION_LOG_URL
        intent.putExtra(EXTRA_URL, url)
        // Replace with the package name of the destination app
        intent.`package` = "com.payal.vpn_service_1"
        // Send the broadcast
        context.sendBroadcast(intent)
    }
}
