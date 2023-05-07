package com.gvstang.dicoding.latihan.githubuser.view.favourite_user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.gvstang.dicoding.latihan.githubuser.adapter.recyclerview.UserListAdapter
import com.gvstang.dicoding.latihan.githubuser.databinding.ActivityFavouriteUserBinding
import com.gvstang.dicoding.latihan.githubuser.util.data.User

class FavouriteUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavouriteUserBinding
    private lateinit var favouriteUserViewModel: FavouriteUserViewModel

    private var listUser = ArrayList<User>()

    private val layoutManager = LinearLayoutManager(this)
    private val adapter = UserListAdapter(listUser)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavouriteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()
        setupRecyclerView()
        setupTopBar()

        favouriteUserViewModel.readAllData.observe(this) {
            setFavourite(it)
        }
    }

    private fun setupTopBar() {
        binding.favouriteToolbar.apply {
            setNavigationOnClickListener {
                finish()
            }
        }
    }

    private fun setFavourite(users: List<com.gvstang.dicoding.latihan.githubuser.database.User>?) {
        if(users!!.isEmpty()) {
            binding.apply {
                rvFavourite.isVisible = false
                tvFavourite.isVisible = false
                tvZeroFavourite.isVisible = true
            }
        } else {
            listUser.clear()
            users.map {
                listUser.add(User(it.username, it.avatarUrl))
            }
            binding.rvFavourite.adapter = UserListAdapter(listUser)
        }
    }

    private fun setupRecyclerView() {
        binding.apply {
            rvFavourite.layoutManager = layoutManager
            rvFavourite.adapter = adapter
        }
    }

    private fun setupViewModel() {
        favouriteUserViewModel = ViewModelProvider(this)[FavouriteUserViewModel::class.java]
    }
}