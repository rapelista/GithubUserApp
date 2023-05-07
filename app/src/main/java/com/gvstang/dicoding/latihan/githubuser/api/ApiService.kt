package com.gvstang.dicoding.latihan.githubuser.api

import com.gvstang.dicoding.latihan.githubuser.api.response.DetailResponse
import com.gvstang.dicoding.latihan.githubuser.api.response.FFResponse
import com.gvstang.dicoding.latihan.githubuser.api.response.SearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @Headers(
        "Accept: application/vnd.github+json",
        "Authorization: Bearer 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",
        "X-GitHub-Api-Version: 2022-11-28"
    )

    @GET("/search/users")
    fun getSearch(
        @Query("q") q: String
    ): Call<SearchResponse>

    @GET("/users/{username}")
    fun getDetail(
        @Path("username") username: String
    ): Call<DetailResponse>

    @GET("/users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String,
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 30
    ): Call<List<FFResponse>>

    @GET("/users/{username}/following")
    fun getFollowings(
        @Path("username") username: String,
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 30
    ): Call<List<FFResponse>>
}