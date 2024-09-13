package com.example.noshapp.network

import com.example.noshapp.model.Dish
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


class RetrofitInstance {
    companion object {
        private val retrofit by lazy {
            Retrofit.Builder()
                .baseUrl("https://fls8oe8xp7.execute-api.ap-south-1.amazonaws.com/dev/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        val api:ApiService by lazy {
            retrofit.create(ApiService::class.java)
        }
    }
}
