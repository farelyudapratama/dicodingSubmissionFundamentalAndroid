package com.yuch.githubuserapp.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yuch.githubuserapp.data.api.RetrofitClient
import com.yuch.githubuserapp.data.response.DataUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel: ViewModel() {
    val user = MutableLiveData<DataUser>()
    fun setUserDetail(username: String?){
        if (username != null) {
            RetrofitClient.apiInstance
                ?.getUserDetail(username)
                ?.enqueue(object : Callback<DataUser> {
                    override fun onResponse(
                        call: Call<DataUser>,
                        response: Response<DataUser>,
                    ) {
                        if (response.isSuccessful){
                            user.postValue(response.body())
                        }
                    }

                    override fun onFailure(call: Call<DataUser>, t: Throwable) {
                        t.message?.let { Log.d("Failure", it) }
                    }

                })
        }
    }
    fun getUserDetail(): LiveData<DataUser> {
        return user
    }
}