package com.team18.tourister.paymentFragment

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.MutableLiveData
import com.team18.tourister.API.EMAIL_EXTRA
import com.team18.tourister.API.PlaceApi
import com.team18.tourister.API.SHAREDPREF_NAME
import com.team18.tourister.ObservableViewModel
import com.team18.tourister.models.CardModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PaymentViewModel (application: Application): ObservableViewModel(application) {

    val context = application.applicationContext
    var email = MutableLiveData<String>()
    var cardNumber = MutableLiveData<String>()
    var expiry = MutableLiveData<String>()

    private var sharedPreferences: SharedPreferences

    init {
        sharedPreferences = context.getSharedPreferences(SHAREDPREF_NAME, Context.MODE_PRIVATE)
        email.value = sharedPreferences.getString(EMAIL_EXTRA,"")
        card(email.value.toString())
    }

    var dateField: String = ""
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.dateField)
        }

    var nameField: String = ""
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.nameField)
        }

    var emailField: String = ""
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.emailField)
        }

    var countField: String = ""
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.countField)
        }

    var cardField: String = ""
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.cardField)
        }

    var monthField: String = ""
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.monthField)
        }

    var yearField: String = ""
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.yearField)
        }

    var cvvField: String = ""
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.cvvField)
        }


    fun card(e: String) {
        PlaceApi.retrofitService.getCard(e).enqueue(object : Callback<CardModel> {
            override fun onFailure(call: Call<CardModel>, t: Throwable) {

            }

            override fun onResponse(call: Call<CardModel>, response: Response<CardModel>) {
                cardNumber.value = response.body()?.card_number
            }
        })
    }


}