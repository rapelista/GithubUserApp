package com.gvstang.dicoding.latihan.githubuser.view.list_user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gvstang.dicoding.latihan.githubuser.api.ApiConfig
import com.gvstang.dicoding.latihan.githubuser.api.response.FFResponse
import com.gvstang.dicoding.latihan.githubuser.util.data.User
import retrofit2.Call
import retrofit2.Response

class ListUserViewModel: ViewModel() {

    private var _listUser = MutableLiveData<ArrayList<User>>()
    val listUser: LiveData<ArrayList<User>> = _listUser

    val result = ArrayList<User>()

    fun getFollowers(username: String, followers: Int?) {
        val pages = followers!!/30+1

        for(page in 1..pages) {
            val client = ApiConfig.getApiService().getFollowers(username, page)
            getData(client)
        }
    }

    fun getFollowings(username: String, followings: Int?) {
        val pages = followings!!/30+1

        for(page in 1..pages) {
            val client = ApiConfig.getApiService().getFollowings(username, page)
            getData(client)
        }
    }

    private fun getData(client: Call<List<FFResponse>>) {
        client.enqueue(object : retrofit2.Callback<List<FFResponse>> {
            override fun onResponse(
                call: Call<List<FFResponse>>,
                response: Response<List<FFResponse>>,
            ) {
                if(response.isSuccessful) {
                    val responseBody = response.body()

                    responseBody?.map {
                        result.add(User(it.login, it.avatarUrl, id = it.id))
                    }

                    _listUser.value = result
                }
            }

            override fun onFailure(call: Call<List<FFResponse>>, t: Throwable) {

            }

        })
    }
}