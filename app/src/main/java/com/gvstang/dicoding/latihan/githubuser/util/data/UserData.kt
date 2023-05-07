package com.gvstang.dicoding.latihan.githubuser.util.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val username: String,
    val avatarUrl: String,
    var fullname: String? = null,
    var followers: Int? = 0,
    var followings: Int? = 0,
    var id: Int? = 0
): Parcelable