package com.most4dev.githubuserrepos.databases

import androidx.annotation.WorkerThread
import com.most4dev.githubuserrepos.model.RepoDownloadedModel
import kotlinx.coroutines.flow.Flow

class GitHubRepository(private val gitHubDao: GitHubDao) {

    val listDownloadedRepos: Flow<List<RepoDownloadedModel>> = gitHubDao.getAllDownloadedRepos()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertDownloadedRepo(repoDownloadedModel: RepoDownloadedModel) {
        gitHubDao.insertDownloadedRepos(repoDownloadedModel)
    }

}