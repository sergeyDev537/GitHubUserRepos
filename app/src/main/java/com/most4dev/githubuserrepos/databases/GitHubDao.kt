package com.most4dev.githubuserrepos.databases

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.most4dev.githubuserrepos.model.RepoDownloadedModel
import kotlinx.coroutines.flow.Flow

@Dao
interface GitHubDao {

    @Query("SELECT * FROM downloaded_repos_table")
    fun getAllDownloadedRepos(): Flow<List<RepoDownloadedModel>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertDownloadedRepos(repoDownloadedModel: RepoDownloadedModel)

}