package com.most4dev.githubuserrepos.viewModels

import android.webkit.URLUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.most4dev.githubuserrepos.Config
import com.most4dev.githubuserrepos.getMimeType
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

    private val fileName = MutableLiveData<String>()

    private val _fileNameError = MutableLiveData<String>()
    val fileNameError: LiveData<String>
        get() = _fileNameError


    fun getUserRepos(username: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = apiInterface.getAllUserRepos(username)
                if (response.isSuccessful) {
                    _listRepos.postValue(response.body())
                } else {
                    _listReposError.postValue(response.errorBody()?.string())
                }
            }catch (e: Exception){
                _listReposError.postValue(e.message)
            }

        }
    }

    fun inspectDownload(owner: String, repo: String, archiveFormat: String): LiveData<String> {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = apiInterface.inspectDownload(
                    owner, repo, Config.archiveFormat
                )
                if (response.isSuccessful) {
                    val headers: Headers = response.headers()
                    val mimeType = getMimeType(archiveFormat)
                    val filename: String =
                        URLUtil.guessFileName(
                            response.raw().request.url.toString(),
                            headers["Content-Disposition"],
                            mimeType
                        )
                    fileName.postValue(filename)
                }
                else{
                    _fileNameError.postValue(response.errorBody()?.string())
                }
            }catch (e: Exception){
                _fileNameError.postValue(e.message)
            }

        }
        return fileName
    }
}