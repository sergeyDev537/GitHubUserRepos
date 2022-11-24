package com.most4dev.githubuserrepos

import android.app.Application
import com.most4dev.githubuserrepos.databases.GitHubRepository
import com.most4dev.githubuserrepos.databases.GitHubRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class GitHubUserReposApp: Application() {

    private val applicationScope = CoroutineScope(SupervisorJob())
    private val database by lazy { GitHubRoomDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { GitHubRepository(database.gitHubDao()) }

}