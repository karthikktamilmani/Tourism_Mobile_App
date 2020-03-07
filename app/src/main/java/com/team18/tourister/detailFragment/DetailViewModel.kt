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
import com.team18.tourister.models.SearchPlace
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel (application: Application) : AndroidViewModel(application) {

    val context = application.applicationContext
    var isLoaded = MutableLiveData<Boolean>()
    var isLoggedIn = MutableLiveData<Boolean>()

    private var adapter: SpotAdapter
    lateinit var input: String
    private var sharedPreferences: SharedPreferences


    init {
        adapter = SpotAdapter(context)
        sharedPreferences = context.getSharedPreferences(SHAREDPREF_NAME, Context.MODE_PRIVATE)

        isLoaded.value = true
        getDetails()
    }

    fun setUpList(list: List<SearchPlace>) {
        adapter.setUpList(list)
        adapter.notifyDataSetChanged()
    }

    fun setParam(params: String) {
        input = params
    }

    fun getDetails() {
        PlaceApi.retrofitService.getDetails(input)
            .enqueue(object : Callback<Place> {
                override fun onFailure(call: Call<Place>, t: Throwable) {
                    Toast.makeText(context, "Server Error", Toast.LENGTH_LONG).show()

                }

                override fun onResponse(call: Call<Place>, response: Response<Place>) {

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
                    if (response.body()?.equals("Ok")!!) {
                        isLoggedIn.value = true

                    }else {
                        isLoggedIn.value = false
                    }
                }
            })

    }
}