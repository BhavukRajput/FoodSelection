package com.example.noshapp.network

import com.example.noshapp.model.Dish
import retrofit2.http.GET

interface ApiService {
    @GET("nosh-assignment")
    suspend fun getDishes(): List<Dish>
}