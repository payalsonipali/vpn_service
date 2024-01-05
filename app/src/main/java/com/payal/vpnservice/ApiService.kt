package com.payal.vpnservice

import retrofit2.Call
import retrofit2.http.GET


interface ApiService {
    @get:GET("products/1")
    val product: Call<Product>
}
