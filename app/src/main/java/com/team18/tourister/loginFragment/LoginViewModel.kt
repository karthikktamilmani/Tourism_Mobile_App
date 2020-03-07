package com.team18.tourister.loginFragment

import android.app.Application
import android.widget.Toast
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.MutableLiveData
import com.team18.tourister.API.PlaceApi
import com.team18.tourister.ObservableViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel (application: Application): ObservableViewModel(application) {

    val context = application.applicationContext
     var moveForward = MutableLiveData<Boolean>()
    lateinit var email: String


    var userNameField: String = ""
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.userNameField)
        }

    var passwordField: String = ""
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.passwordField)
        }

    fun login() {
        if (userNameField.isNotEmpty() && passwordField.isNotEmpty()) {

            val params = HashMap<String, String>()
            params["email"] = userNameField
            params["password"] = passwordField
            email = userNameField
            makeRequest(params)
        } else {
            Toast.makeText(context, "Fields cannot be empty", Toast.LENGTH_LONG).show()
        }

    }


    fun makeRequest(obj: HashMap<String, String>) {
        PlaceApi.retrofitService.login(obj).enqueue(object : Callback<Any>{
            override fun onFailure(call: Call<Any>, t: Throwable) {
                Toast.makeText(context,"Invalid Credentials",Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                moveForward.value = response.message().equals("OK")
            }
        })
    }
}