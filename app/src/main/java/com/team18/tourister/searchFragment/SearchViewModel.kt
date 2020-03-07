package com.team18.tourister.searchFragment

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import com.team18.tourister.API.PlaceApi
import com.team18.tourister.Adapter.PlaceAdapter
import com.team18.tourister.models.SearchPlace
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel (application: Application) : AndroidViewModel(application) {


    val context = application.applicationContext
    private var adapter: PlaceAdapter
    init {
        adapter = PlaceAdapter(context)
    }

    fun getPlaceAdapter() = adapter

    fun setPlaceAdapter(list: List<SearchPlace>) {
        adapter.setupList(list)
        adapter.notifyDataSetChanged()
    }

    fun getPlaces() {
        PlaceApi.retrofitService.searchPlaces("Halifax")
            .enqueue(object : Callback<List<SearchPlace>> {
                override fun onFailure(call: Call<List<SearchPlace>>, t: Throwable) {
                    Toast.makeText(context, "Server Error", Toast.LENGTH_LONG).show()
                }

                override fun onResponse(
                    call: Call<List<SearchPlace>>,
                    response: Response<List<SearchPlace>>
                ) {
                    val list = response.body()
                }
            })
    }
}