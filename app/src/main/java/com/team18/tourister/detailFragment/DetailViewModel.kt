package com.team18.tourister.detailFragment

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.team18.tourister.API.PlaceApi
import com.team18.tourister.API.SHAREDPREF_NAME
import com.team18.tourister.API.TOKEN
import com.team18.tourister.Adapter.SpotAdapter
import com.team18.tourister.models.Place
import com.team18.tourister.models.CityPlace
import com.team18.tourister.models.SpotDetails
import com.team18.tourister.models.SpotPlace
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel (application: Application) : AndroidViewModel(application) {

    val context = application.applicationContext
    var isLoaded = MutableLiveData<Boolean>()
    var isLoggedIn = MutableLiveData<Boolean>()
    val placeName = MutableLiveData<String>()
    val placeDesc = MutableLiveData<String>()
    val image_url = MutableLiveData<String>()
    val spot_list = MutableLiveData<List<SpotPlace>>()

    private var adapter: SpotAdapter
    lateinit var input: String
    private var sharedPreferences: SharedPreferences


    init {
        adapter = SpotAdapter(context)
        sharedPreferences = context.getSharedPreferences(SHAREDPREF_NAME, Context.MODE_PRIVATE)

        isLoaded.value = true
    }

    fun getSpotAdapter() = adapter

    fun setUpList(list: List<SpotPlace>) {
        adapter.setUpList(list)
        adapter.notifyDataSetChanged()
    }

    fun setParam(params: String) {
        input = params
        getDetails()
    }

    fun setSpotParam(params: String) {
        input = params
        getSpotDetails()
    }

    private fun getDetails() {
        PlaceApi.retrofitService.getDetails(input)
            .enqueue(object : Callback<Place> {
                override fun onFailure(call: Call<Place>, t: Throwable) {
                    Toast.makeText(context, "Server Error", Toast.LENGTH_LONG).show()

                }
                override fun onResponse(call: Call<Place>, response: Response<Place>) {
                    val res = response.body()
                    placeName.value = res?.Place_name
                    placeDesc.value = res?.Place_description
                    image_url.value = res?.Place_Image
                    spot_list.value = res?.Spots
                }
            })
    }

    private fun getSpotDetails() {
        PlaceApi.retrofitService.getSpotDetails(input)
            .enqueue(object : Callback<SpotDetails> {
                override fun onFailure(call: Call<SpotDetails>, t: Throwable) {
                    Toast.makeText(context, "Server Error", Toast.LENGTH_LONG).show()

                }

                override fun onResponse(call: Call<SpotDetails>, response: Response<SpotDetails>) {
                    val res = response.body()
                    placeName.value = res?.T_name
                    placeDesc.value = res?.T_description
                    image_url.value = res?.T_Image
                }
            })
    }

    fun checkAuth() {
        PlaceApi.retrofitService.checkAuth(sharedPreferences.getString(TOKEN, "") + "")
            .enqueue(object : Callback<Any>{
                override fun onFailure(call: Call<Any>, t: Throwable) {
                    isLoggedIn.value = false
                }

                override fun onResponse(call: Call<Any>, response: Response<Any>) {

                    val m = JSONObject(response.body().toString())
                    isLoggedIn.value = m.getString("message") == "ok"

                }
            })

    }
}