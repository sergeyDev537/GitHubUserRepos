package com.most4dev.githubuserrepos.viewModels

import android.webkit.URLUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.most4dev.githubuserrepos.model.RepositoryModel
import com.most4dev.githubuserrepos.retrofit.ApiInterface
import com.most4dev.githubuserrepos.retrofit.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Headers
import retrofit2.Retrofit


class ReposViewModel : ViewModel() {

    private var retrofit: Retrofit = RetrofitClient.getInstance()
    private var apiInterface = retrofit.create(ApiInterface::class.java)
    private val _listRepos = MutableLiveData<List<RepositoryModel>>()

    val listRepos: LiveData<List<RepositoryModel>>
        get() = _listRepos

    val _listReposError = MutableLiveData<String>()
    val listReposError: LiveData<String>
        get() = _listReposError

    private val _fileName = MutableLiveData<String>()
    val fileName: LiveData<String>
        get() = _fileName

    private val _fileNameError = MutableLiveData<String>()
    val fileNameError: LiveData<String>
        get() = _fileNameError


    fun getUserRepos(username: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = apiInterface.getAllUserRepos(username)
            if (response.isSuccessful) {
                _listRepos.postValue(response.body())
            } else {
                _listReposError.postValue(response.errorBody()?.string())
            }
        }
    }

    fun inspectDownload(owner: String, repo: String, archiveFormat: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = apiInterface.inspectDownload(
                owner, repo
            )
            if (response.isSuccessful) {
                val headers: Headers = response.headers()
                val mimeType =
                    "application/" + if (archiveFormat == "zipball") "zip" else "tar+gzip"
                val filename: String =
                    URLUtil.guessFileName(
                        response.raw().request.url.toString(),
                        headers["Content-Disposition"],
                        mimeType
                    )
                _fileName.postValue(filename)
            }
            else{
                _fileNameError.postValue(response.errorBody()?.string())
            }
        }
    }
}