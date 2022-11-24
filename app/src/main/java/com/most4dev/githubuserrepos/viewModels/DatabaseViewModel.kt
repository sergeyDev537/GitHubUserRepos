package com.most4dev.githubuserrepos.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.most4dev.githubuserrepos.databases.GitHubRepository
import com.most4dev.githubuserrepos.model.RepoDownloadedModel
import kotlinx.coroutines.launch

class DatabaseViewModel(private val repository: GitHubRepository): ViewModel() {

    val listDownloadedRepos: LiveData<List<RepoDownloadedModel>> = repository.listDownloadedRepos.asLiveData()

    fun insertDownloadedRepo(repoDownloadedModel: RepoDownloadedModel) = viewModelScope.launch {
        repository.insertDownloadedRepo(repoDownloadedModel)
    }

}