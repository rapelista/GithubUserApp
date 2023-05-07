package com.gvstang.dicoding.latihan.githubuser.view.user_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gvstang.dicoding.latihan.githubuser.api.ApiConfig
import com.gvstang.dicoding.latihan.githubuser.api.response.DetailResponse
import com.gvstang.dicoding.latihan.githubuser.util.data.User
import retrofit2.Call
import retrofit2.Response

class UserDetailViewModel: ViewModel() {

    private var _userDetail = MutableLiveData<User>()
    val userDetail: LiveData<User> = _userDetail

    fun getDetailUser(username: String) {
        val client = ApiConfig.getApiService().getDetail(username)
        client.enqueue(object : retrofit2.Callback<DetailResponse> {
            override fun onResponse(
                call: Call<DetailResponse>,
                response: Response<DetailResponse>,
            ) {
                if(response.isSuccessful) {
                    val responseBody = response.body()
                    responseBody?.apply {
                        _userDetail.value = User(login, avatarUrl, name, followers, following)
                    }
                }
            }

            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {

            }

        })
    }
}