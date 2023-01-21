package com.example.githubuserrepos.presentation.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.githubuserrepos.R
import com.example.githubuserrepos.data.UserRepositoryImpl
import com.example.githubuserrepos.domain.entities.RepositoryEntity
import com.example.githubuserrepos.domain.usecases.GetListRepositoryUseCase
import kotlinx.coroutines.launch

class RepositoryViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val context = application

    private var userRepository = UserRepositoryImpl(application)

    private val getListRepositoryUseCase = GetListRepositoryUseCase(userRepository)

    private var _listUserRepositories = MutableLiveData<List<RepositoryEntity>>()
    val listUserRepositories: LiveData<List<RepositoryEntity>>
        get() = _listUserRepositories

    private var _listUserRepositoriesError = MutableLiveData<String>()
    val listUserRepositoriesError: LiveData<String>
        get() = _listUserRepositoriesError

    private var _startLoading = MutableLiveData<Unit>()
    val startLoading: LiveData<Unit>
        get() = _startLoading

    private var _endLoading = MutableLiveData<Unit>()
    val endLoading: LiveData<Unit>
        get() = _endLoading

    fun getListUserRepositories(username: String) {
        if (parseUserName(username)) {
            _startLoading.value = Unit
            viewModelScope.launch {
                try {
                    val listRepositories = getListRepositoryUseCase.invoke(username)
                    if (listRepositories.isNotEmpty()) {
                        _listUserRepositories.value = listRepositories
                        _endLoading.value = Unit
                    } else {
                        _listUserRepositoriesError.value = context.getString(R.string.no_results)
                        _endLoading.value = Unit
                    }
                } catch (e: Exception) {
                    _listUserRepositoriesError.value = e.message
                    _endLoading.value = Unit
                }
            }
        } else {
            _listUserRepositoriesError.value = context.getString(R.string.error_username)
            _endLoading.value = Unit
        }

    }

    private fun parseUserName(username: String): Boolean {
        return username.isNotEmpty()
    }


}