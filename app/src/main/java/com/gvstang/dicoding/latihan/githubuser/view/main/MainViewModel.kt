package com.gvstang.dicoding.latihan.githubuser.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gvstang.dicoding.latihan.githubuser.api.ApiConfig
import com.gvstang.dicoding.latihan.githubuser.api.response.SearchResponse
import com.gvstang.dicoding.latihan.githubuser.util.data.User
import retrofit2.Call
import retrofit2.Response

class MainViewModel: ViewModel() {

    private var _listUser = MutableLiveData<ArrayList<User>>()
    val listUser: LiveData<ArrayList<User>> = _listUser

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun searchUser(username: String) {
        _isLoading.value = true

        val client = ApiConfig.getApiService().getSearch(username)
        client.enqueue(object : retrofit2.Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>,
            ) {
                if(response.isSuccessful) {
                    val result = ArrayList<User>()
                    val responseBody = response.body()

                    responseBody?.items?.map {
                        it.apply {
                            result.add(User(login, avatarUrl))
                        }
                    }

                    _listUser.value = result
                    _isLoading.value = false
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                _isLoading.value = false
            }
        })
    }
}