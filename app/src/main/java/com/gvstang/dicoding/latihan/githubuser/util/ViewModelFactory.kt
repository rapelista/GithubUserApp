package com.gvstang.dicoding.latihan.githubuser.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gvstang.dicoding.latihan.githubuser.util.pref.SettingsPreferences
import com.gvstang.dicoding.latihan.githubuser.view.setting.SettingViewModel

class ViewModelFactory(private val pref: SettingsPreferences) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SettingViewModel::class.java)) {
            return SettingViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}