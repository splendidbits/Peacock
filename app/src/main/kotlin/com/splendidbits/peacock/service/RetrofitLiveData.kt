package com.splendidbits.peacock.service

import android.util.Log
import androidx.lifecycle.LiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// Taken from:
//    https://medium.com/@alvaro.blanco/playing-with-android-architecture-components-retrofit-part-1-9994d651cf3c
//
// Daniel: Someone created this handy Kotlin extension that turns Retrofit callables into LiveData containers
// and binds with the source observable automatically. This saves us pain and blood and tears.
//
class RetrofitLiveData<T>(private val call: Call<T>) : LiveData<T>(), Callback<T> {

    override fun onActive() {
        if (!call.isCanceled && !call.isExecuted) call.enqueue(this)
    }

    override fun onFailure(call: Call<T>?, t: Throwable?) {
        Log.e(this::javaClass.name, t.toString())
    }

    override fun onResponse(call: Call<T>?, response: Response<T>?) {
        value = response?.body()
    }

    fun cancel() = if(!call.isCanceled) call.cancel() else Unit
}