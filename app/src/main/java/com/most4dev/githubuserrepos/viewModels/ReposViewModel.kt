package com.most4dev.githubuserrepos.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.most4dev.githubuserrepos.model.GitHubRepository
import com.most4dev.githubuserrepos.retrofit.ApiInterface
import com.most4dev.githubuserrepos.retrofit.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit

class ReposViewModel: ViewModel() {

    var retrofit: Retrofit = RetrofitClient.getInstance()
    private var apiInterface = retrofit.create(ApiInterface::class.java)
    private val _listRepos = MutableLiveData<List<GitHubRepository>>()
    val listRepos: LiveData<List<GitHubRepository>>
        get() = _listRepos
    val _listReposError = MutableLiveData<String>()
    val listReposError: LiveData<String>
        get() = _listReposError


    fun getUserRepos(username: String){
        viewModelScope.launch(Dispatchers.IO) {
            val response = apiInterface.getAllUserRepos(username)
            if (response.isSuccessful){
                _listRepos.postValue(response.body())
            }
            else{
                _listReposError.postValue(response.errorBody()?.string())
            }
        }
    }

}