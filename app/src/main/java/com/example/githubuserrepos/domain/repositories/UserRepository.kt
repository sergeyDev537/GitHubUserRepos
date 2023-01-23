package com.example.githubuserrepos.domain.repositories

import androidx.lifecycle.LiveData
import com.example.githubuserrepos.domain.entities.RepositoryEntity

interface UserRepository {

    suspend fun getListRepository(username: String): List<RepositoryEntity>

    fun getDownloadedListRepository(): LiveData<List<RepositoryEntity>>

    suspend fun downloadRepositoryUseCase(repositoryEntity: RepositoryEntity): Long

    suspend fun addDownloadedRepository(repositoryEntity: RepositoryEntity)

    fun getItemRepository(id: Int): RepositoryEntity

}