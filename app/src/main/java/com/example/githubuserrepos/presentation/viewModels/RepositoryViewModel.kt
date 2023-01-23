package com.example.githubuserrepos.presentation.viewModels

import android.annotation.SuppressLint
import android.app.Application
import android.app.DownloadManager
import android.content.Context
import android.database.Cursor
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.githubuserrepos.R
import com.example.githubuserrepos.data.UserRepositoryImpl
import com.example.githubuserrepos.domain.entities.RepositoryEntity
import com.example.githubuserrepos.domain.usecases.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RepositoryViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val context = application

    private var userRepository = UserRepositoryImpl(application)

    private val getListRepositoryUseCase = GetListRepositoryUseCase(userRepository)
    private val getItemRepositoryUseCase = GetItemRepositoryUseCase(userRepository)
    private val downloadRepositoryUseCase = DownloadRepositoryUseCase(userRepository)
    private val addRepositoryItemDbUseCase = AddRepositoryItemDbUseCase(userRepository)
    private val getDownloadedListRepositoryUseCase =
        GetDownloadedListRepositoryUseCase(userRepository)

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

    private var _statusDownload = SingleLiveEvent<String>()
    val statusDownload: LiveData<String>
        get() = _statusDownload

    private var _startDownload = MutableLiveData<Unit>()
    val startDownload: LiveData<Unit>
        get() = _startDownload

    private var _endDownload = MutableLiveData<Unit>()
    val endDownload: LiveData<Unit>
        get() = _endDownload


    val listUserRepositoriesDb = getDownloadedListRepositoryUseCase.invoke()

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

    fun getItemRepository(id: Int) {
        try {
            _itemRepository.value = getItemRepositoryUseCase.invoke(id)
        } catch (e: Exception) {
            _itemRepositoryError.value = e.message
        }
    }

    fun downloadRepository(repositoryEntity: RepositoryEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            startDownloadAndSaveDb()
            val dm = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            val downloadId = downloadRepositoryUseCase.invoke(repositoryEntity)
            val query = DownloadManager.Query().setFilterById(downloadId)
            getStatusDownload(dm, query)
        }
    }

    @SuppressLint("Range")
    private fun getStatusDownload(
        downloadManager: DownloadManager,
        query: DownloadManager.Query
    ): String {
        var lastMsg = ""
        var downloading = true
        while (downloading) {
            val cursor: Cursor = downloadManager.query(query)
            cursor.moveToFirst()
            if (cursor.getInt(
                    cursor.getColumnIndex(
                        DownloadManager.COLUMN_STATUS
                    )
                ) == DownloadManager.STATUS_SUCCESSFUL ||
                cursor.getInt(
                    cursor.getColumnIndex(
                        DownloadManager.COLUMN_STATUS
                    )
                ) == DownloadManager.STATUS_FAILED
            ) {
                downloading = false
            }
            val status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
            val msg: String = statusMessage(status)
            if (msg != lastMsg) {
                lastMsg = msg
                _statusDownload.postValue(lastMsg)
            }
            cursor.close()
        }
        return lastMsg
    }

    private fun statusMessage(status: Int): String {
        val msg: String = when (status) {
            DownloadManager.STATUS_FAILED -> {
                context.getString(R.string.download_failed)
            }
            DownloadManager.STATUS_PAUSED -> context.getString(R.string.download_paused)
            DownloadManager.STATUS_PENDING -> context.getString(R.string.download_pending)
            DownloadManager.STATUS_RUNNING -> context.getString(R.string.downloading)
            DownloadManager.STATUS_SUCCESSFUL -> context.getString(R.string.download_successfully)
            else -> context.getString(R.string.download_error)
        }
        return msg
    }

    fun addItemInDb(repositoryEntity: RepositoryEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            addRepositoryItemDbUseCase.invoke(repositoryEntity)
            endDownloadAndSaveDb()
        }
    }

    private fun startDownloadAndSaveDb() {
        _startDownload.postValue(Unit)
    }

    private fun endDownloadAndSaveDb() {
        _endDownload.postValue(Unit)
    }

}