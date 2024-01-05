package com.payal.vpnservice

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.ComponentActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val log = findViewById<Button>(R.id.log)
        val log1 = findViewById<Button>(R.id.log1)

        log.setOnClickListener {
            fetchData()
        }

        log1.setOnClickListener {
            logUrl("button clicked");
        }
    }

    private fun logUrl(url: String) {
        Log.d("taggg", "log url called")
        UrlLogger.logUrl(this,url)
    }

    private fun fetchData() {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://dummyjson.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        val call: Call<Product> = apiService.product
        call?.enqueue(object : Callback<Product?> {
            override fun onResponse(call: Call<Product?>?, response: Response<Product?>) {
                if (response.isSuccessful) {
                    val product: Product? = response.body()
                    logUrl("api request for :  https://dummyjson.com/product/1");
                    logUrl("Response : title: ${product?.title}  price : ${product?.price}");
                } else {
                    logUrl("Error code:  ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Product?>?, t: Throwable) {
                Log.e("NetworkError", t.message!!)
            }
        })
    }

}