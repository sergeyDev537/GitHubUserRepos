package com.example.githubuserrepos.presentation.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.githubuserrepos.R
import com.example.githubuserrepos.data.UserRepositoryImpl
import com.example.githubuserrepos.domain.entities.RepositoryEntity
import com.example.githubuserrepos.domain.usecases.GetItemRepositoryUseCase
import com.example.githubuserrepos.domain.usecases.GetListRepositoryUseCase
import kotlinx.coroutines.launch

class RepositoryViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val context = application

    private var userRepository = UserRepositoryImpl(application)

    private val getListRepositoryUseCase = GetListRepositoryUseCase(userRepository)
    private val getItemRepositoryUseCase = GetItemRepositoryUseCase(userRepository)

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

    private var _itemRepository = MutableLiveData<RepositoryEntity>()
    val itemRepository: LiveData<RepositoryEntity>
        get() = _itemRepository

    private var _itemRepositoryError = MutableLiveData<String>()
    val itemRepositoryError: LiveData<String>
        get() = _itemRepositoryError

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

    fun getItemRepository(id: Int){
        try {
            _itemRepository.value = getItemRepositoryUseCase.invoke(id)
        }
        catch (e: Exception){
            _itemRepositoryError.value = e.message
        }
    }


}