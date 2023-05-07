package com.gvstang.dicoding.latihan.githubuser.view.main

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.gvstang.dicoding.latihan.githubuser.adapter.recyclerview.UserListAdapter
import com.gvstang.dicoding.latihan.githubuser.databinding.ActivityMainBinding
import com.gvstang.dicoding.latihan.githubuser.util.ViewModelFactory
import com.gvstang.dicoding.latihan.githubuser.util.data.User
import com.gvstang.dicoding.latihan.githubuser.util.pref.SettingsPreferences
import com.gvstang.dicoding.latihan.githubuser.view.favourite_user.FavouriteUserActivity
import com.gvstang.dicoding.latihan.githubuser.view.setting.SettingActivity
import com.gvstang.dicoding.latihan.githubuser.view.setting.SettingViewModel

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var settingViewModel: SettingViewModel

    private var listUser = ArrayList<User>()
    private val adapter = UserListAdapter(listUser)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()
        setupRecyclerView()
        setupSearchView()
        setupTopBar()
        setupFAB()

        mainViewModel.listUser.observe(this) {
            updateRecyclerView(it)
        }

        mainViewModel.isLoading.observe(this) {
            isLoading(it)
        }
    }

    private fun setupTopBar() {
        binding.apply {
            mainToolbar.setBackgroundColor(Color.parseColor("#161b22"))
        }
    }

    private fun setupFAB() {
        binding.apply {
            fabMainToFavourite.setOnClickListener {
                val intent = Intent(this@MainActivity, FavouriteUserActivity::class.java)
                startActivity(intent)
            }
            fabMainToSettings.setOnClickListener {
                val intent = Intent(this@MainActivity, SettingActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun isLoading(status: Boolean) {
        binding.apply {
            rvHomeListUser.isVisible = !status
            homeLoading.isVisible = status
        }
    }

    private fun updateRecyclerView(data: java.util.ArrayList<User>) {
        binding.tvNotFound.isVisible = data.isEmpty()

        listUser.clear()
        listUser.addAll(data)
        binding.rvHomeListUser.adapter = UserListAdapter(listUser)
    }

    private fun setupSearchView() {
        binding.searchView
            .editText
            .setOnEditorActionListener { _, _, _ ->
                binding.searchBar.text = binding.searchView.text
                binding.searchView.hide()

                val username = binding.searchView.text.toString()
                mainViewModel.searchUser(username)
                binding.tvNotFound.isVisible = false
                isLoading(true)

                return@setOnEditorActionListener false
            }
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        binding.apply {
            rvHomeListUser.layoutManager = layoutManager
            rvHomeListUser.adapter = adapter
        }
    }

    private fun setupViewModel() {
        val pref = SettingsPreferences.getInstance(dataStore)

        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[MainViewModel::class.java]
        settingViewModel = ViewModelProvider(this, ViewModelFactory(pref))[SettingViewModel::class.java]

        settingViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

    }
}