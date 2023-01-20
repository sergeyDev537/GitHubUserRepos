package com.example.githubuserrepos.data

import com.example.githubuserrepos.data.mappers.RepositoryMapper
import com.example.githubuserrepos.data.network.retrofit.ApiInterface
import com.example.githubuserrepos.data.network.retrofit.RetrofitClient
import com.example.githubuserrepos.domain.entities.RepositoryEntity
import com.example.githubuserrepos.domain.repositories.UserRepository
import retrofit2.Retrofit

object UserRepositoryImpl : UserRepository {

    private var retrofit: Retrofit = RetrofitClient.getInstance()
    private var apiInterface = retrofit.create(ApiInterface::class.java)
    private var repositoryMapper = RepositoryMapper()

    override suspend fun getListRepository(username: String): List<RepositoryEntity> {
        apiInterface.getListRepositories(
            username
        ).body()?.let {
            return repositoryMapper.mapListNetworkModelToListEntity(it)
        } ?: return arrayListOf()
    }

    override suspend fun getDownloadedListRepository(): List<RepositoryEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun downloadRepositoryUseCase(url: String) {
        TODO("Not yet implemented")
    }
}