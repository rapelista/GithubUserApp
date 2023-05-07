package com.gvstang.dicoding.latihan.githubuser.view.favourite_user

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.gvstang.dicoding.latihan.githubuser.database.User
import com.gvstang.dicoding.latihan.githubuser.database.UserDatabase
import com.gvstang.dicoding.latihan.githubuser.database.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavouriteUserViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<User>>
    private val repository: UserRepository

    init {
        val userDao = UserDatabase.getDatabase(application).userDao()
        repository = UserRepository(userDao)
        readAllData = repository.readAllData
    }

    fun addUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUser(user)
        }
    }

    fun deleteUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteUser(user)
        }
    }
}