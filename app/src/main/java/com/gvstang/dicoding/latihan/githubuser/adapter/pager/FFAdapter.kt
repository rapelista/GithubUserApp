package com.gvstang.dicoding.latihan.githubuser.adapter.pager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.gvstang.dicoding.latihan.githubuser.view.list_user.ListUserFragment

class FFAdapter(activity: AppCompatActivity): FragmentStateAdapter(activity) {

    var username = ""
    var followers = 0
    var followings = 0

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = ListUserFragment()
        fragment.arguments = Bundle().apply {
            putInt(ListUserFragment.ARG_SECTION_NUMBER, position)
            putString(ListUserFragment.ARG_USERNAME, username)
            putInt(ListUserFragment.ARG_FOLLOWERS, followers)
            putInt(ListUserFragment.ARG_FOLLOWINGS, followings)
        }
        return fragment
    }
}