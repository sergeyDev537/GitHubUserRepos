package com.example.githubuserrepos.domain.repositories

import com.example.githubuserrepos.domain.entities.RepositoryEntity

interface UserRepository {

    suspend fun getListRepository(username: String): List<RepositoryEntity>

    suspend fun getDownloadedListRepository(): List<RepositoryEntity>

    suspend fun downloadRepositoryUseCase(url: String)

}