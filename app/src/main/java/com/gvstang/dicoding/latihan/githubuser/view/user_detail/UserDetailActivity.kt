package com.gvstang.dicoding.latihan.githubuser.view.user_detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.gvstang.dicoding.latihan.githubuser.R
import com.gvstang.dicoding.latihan.githubuser.adapter.pager.FFAdapter
import com.gvstang.dicoding.latihan.githubuser.databinding.ActivityUserDetailBinding
import com.gvstang.dicoding.latihan.githubuser.util.data.User
import com.gvstang.dicoding.latihan.githubuser.view.favourite_user.FavouriteUserViewModel

class UserDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserDetailBinding
    private lateinit var userDetailViewModel: UserDetailViewModel
    private lateinit var favouriteUserViewModel: FavouriteUserViewModel

    private var userId: Int? = null
    private var isFavourite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra("username").toString()
        val avatarUrl = intent.getStringExtra("avatarUrl").toString()

        setupViewModel()
        setupTopBar(username)

        userDetailViewModel.apply {
            getDetailUser(username)

            userDetail.observe(this@UserDetailActivity) {
                setupDetailUser(it)
            }
        }

        favouriteUserViewModel.apply {
            readAllData.observe(this@UserDetailActivity) {
                for(user in it) {
                    if(username == user.username && avatarUrl == user.avatarUrl) {
                        userId = user.id
                        isFavourited(true)
                    }
                }
            }
        }
    }

    private fun removeFromFavourite(user: com.gvstang.dicoding.latihan.githubuser.database.User) {
        favouriteUserViewModel.deleteUser(user)
        isFavourited(false)
    }

    private fun addToFavourite(user: com.gvstang.dicoding.latihan.githubuser.database.User) {
        favouriteUserViewModel.addUser(user)
        isFavourited(true)
    }

    private fun isFavourited(status: Boolean) {
        isFavourite = status

        if(status) {
            binding.fabFavourite.backgroundTintList = ResourcesCompat.getColorStateList(resources, R.color.btn_favourite, null)
            binding.fabFavourite.icon = ResourcesCompat.getDrawable(resources, R.drawable.ic_favorite_24, null)
        } else {
            binding.fabFavourite.backgroundTintList = ResourcesCompat.getColorStateList(resources, R.color.color_1, null)
            binding.fabFavourite.icon = ResourcesCompat.getDrawable(resources, R.drawable.ic_add_24, null)
        }
    }

    private fun setupDetailUser(user: User) {
        binding.apply {
            user.apply {
                Glide
                    .with(applicationContext)
                    .load(avatarUrl)
                    .circleCrop()
                    .into(ivDetailAvatar)

                tvDetailFullname.text = fullname

                fabFavourite.setOnClickListener {
                    if(isFavourite) {
                        val userFav = com.gvstang.dicoding.latihan.githubuser.database.User(userId!!.toInt(), username, avatarUrl)
                        removeFromFavourite(userFav)
                        isFavourited(false)
                    } else {
                        val userFav = com.gvstang.dicoding.latihan.githubuser.database.User(0, username, avatarUrl)
                        addToFavourite(userFav)

                    }
                }
            }
        }

        setupFF(user)
        showDetail()
    }


    private fun setupFF(user: User) {
        val titleTab = ArrayList<String>()
        val adapter = FFAdapter(this)

        user.apply {
            titleTab.apply {
                add("$followers FOLLOWERS")
                add("$followings FOLLOWINGS")
            }

            adapter.username = username
            adapter.followers = followers!!
            adapter.followings = followings!!
        }

        binding.apply {
            viewpagerDetail.adapter = adapter
            TabLayoutMediator(tabDetail, viewpagerDetail) { tab, position ->
                tab.text = titleTab[position]
            }.attach()
        }
    }

    private fun showDetail() {
        binding.apply {
            userDetailLoading.isVisible = false
            ivDetailAvatar.isVisible = true
            tvDetailFullname.isVisible = true
            tabDetail.isVisible = true
            viewpagerDetail.isVisible = true
            fabFavourite.isVisible = true
        }
    }

    private fun setupTopBar(username: String?) {
        binding.detailToolbar.apply {
            title = username
            setNavigationOnClickListener {
                finish()
            }
        }
    }

    private fun setupViewModel() {
        userDetailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[UserDetailViewModel::class.java]
        favouriteUserViewModel = ViewModelProvider(this)[FavouriteUserViewModel::class.java]
    }
}