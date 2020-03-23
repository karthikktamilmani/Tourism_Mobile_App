package com.team18.tourister.API

import com.team18.tourister.models.*
import okhttp3.OkHttpClient
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit


var okHttpClient = OkHttpClient.Builder()
    .readTimeout(5, TimeUnit.MINUTES)
    .connectTimeout(5, TimeUnit.MINUTES)
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .client(okHttpClient)
    .build()

interface ApiService{
    @GET("$SEARCH/{input}")
    fun searchPlaces(@Path("input") input: String):
            Call<SearchModel>

    @GET("$SEARCH/{input}")
    fun getDetails(@Path("input") input: String):
            Call<Place>

    @GET("/{input}")
    fun getSpotDetails(@Path("input") input: String):
            Call<SpotDetails>

    @GET(USER)
    fun checkAuth(@Header("Token") auth: String):
            Call<Any>

    @GET("$USER/login")
    fun login(@Query("email") email: String,@Query("password") pass: String):
            Call<Any>


    @POST("$USER/create")
    fun register(@Body body: HashMap<String, String>):
            Call<Any>

    @POST("$USER/verify")
    fun verify(@Body body: HashMap<String, String>):
            Call<AuthenticationModel>

    @POST(TICKET)
    fun bookTicket(@Header("Content-Type") type: String,@Body body: HashMap<String,Any>):
            Call<Any>

    @GET("$CARD/{input}")
    fun getCard(@Path("input") input: String):
            Call<List<CardModel>>
}

object PlaceApi {
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}
