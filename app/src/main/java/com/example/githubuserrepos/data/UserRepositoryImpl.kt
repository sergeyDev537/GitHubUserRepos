package com.example.githubuserrepos.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.githubuserrepos.data.database.model.AppDatabase
import com.example.githubuserrepos.data.mappers.RepositoryMapper
import com.example.githubuserrepos.data.network.retrofit.ApiInterface
import com.example.githubuserrepos.data.network.retrofit.RetrofitClient
import com.example.githubuserrepos.domain.entities.RepositoryEntity
import com.example.githubuserrepos.domain.repositories.UserRepository
import retrofit2.Retrofit

class UserRepositoryImpl(application: Application) : UserRepository {

    private var retrofit: Retrofit = RetrofitClient.getInstance()
    private var apiInterface = retrofit.create(ApiInterface::class.java)
    private var repositoryMapper = RepositoryMapper()
    private val repositoryDao = AppDatabase.getInstance(application).repositoryDao()

    override suspend fun getListRepository(username: String): List<RepositoryEntity> {
        apiInterface.getListRepositories(
            username
        ).body()?.let {
            return repositoryMapper.mapListNetworkModelToListEntity(it)
        } ?: return arrayListOf()
    }

    override suspend fun getDownloadedListRepository(): LiveData<List<RepositoryEntity>> =
        Transformations.map(repositoryDao.getListRepositories()) {
            repositoryMapper.mapListDbModelToListEntity(it)
        }

    override suspend fun downloadRepositoryUseCase(repositoryEntity: RepositoryEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun addDownloadedRepository(repositoryEntity: RepositoryEntity) {
        repositoryDao.addRepositoryItem(repositoryMapper.mapEntityToDbModel(repositoryEntity))
    }
}