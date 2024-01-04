package com.payal.vpnservice

import android.content.Intent
import android.net.VpnService
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.os.ParcelFileDescriptor
import android.util.Log
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.channels.DatagramChannel
import java.nio.channels.SelectionKey
import java.nio.channels.Selector

class VpnServiceExample : VpnService(), Handler.Callback, Runnable {
    private var mHandler: Handler? = null
    private var mThread: Thread? = null
    private var mInterface: ParcelFileDescriptor? = null
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        mHandler = Handler(Looper.getMainLooper(), this)
        mThread = Thread(this, "VpnThread")
        mThread!!.start()
        return START_STICKY
    }

    override fun onDestroy() {
        if (mThread != null) {
            mThread!!.interrupt()
        }
        super.onDestroy()
    }

    override fun run() {
        try {
            startVpn()
        } catch (e: Exception) {
            Log.e(VpnServiceExample.Companion.TAG, "Error in VPN thread: " + e.message)
        } finally {
            stopSelf()
        }
    }

    @Throws(Exception::class)
    private fun startVpn() {
        if (mInterface != null) {
            return
        }
        val builder: Builder = Builder()
        builder.setSession("VpnServiceExample")
        builder.addAddress("10.0.0.2", 32)
        mInterface = builder.establish()
        if (mInterface != null) {
            readFromVPN()
        }
    }

    private fun readFromVPN() {
        val buffer = ByteBuffer.allocate(32767)
        val selector: Selector
        val channel: DatagramChannel

        try {
            selector = Selector.open()
            channel = DatagramChannel.open()
            channel.configureBlocking(false)
            channel.register(selector, SelectionKey.OP_READ)
        } catch (e: IOException) {
            Log.e(TAG, "Error setting up selector: ${e.message}")
            return
        }

        while (!Thread.interrupted()) {
            try {
                val readyChannels = selector.select()
                if (readyChannels == 0) {
                    continue
                }

                val keys = selector.selectedKeys()
                for (key in keys) {
                    if (key.isReadable) {
                        val client = (key.channel() as DatagramChannel).receive(buffer)
                        if (client != null) {
                            buffer.flip()
                            handlePacket(buffer.array(), buffer.limit())
                        }
                    }
                }
                keys.clear()
            } catch (e: IOException) {
                Log.e(TAG, "Error reading from VPN: ${e.message}")
            }
        }
    }

    private fun handlePacket(packet: ByteArray, length: Int) {
        // Implement logic to handle incoming VPN packets
        // For simplicity, let's assume it's URL data and log it.
        val url = String(packet, 0, length)
        Log.d(VpnServiceExample.Companion.TAG, "Received URL over VPN: $url")

        // Broadcast the URL to other applications
        sendUrlBroadcast(url)
    }

    private fun sendUrlBroadcast(url: String) {
        val intent = Intent()
        intent.action = "com.example.ACTION_LOG_URL"
        intent.putExtra("extra_url", url)
        sendBroadcast(intent)
    }

    companion object {
        private const val TAG = "VpnServiceExample"
    }

    override fun handleMessage(msg: Message): Boolean {
        return true
    }
}
