package com.example.githubuserrepos.data

import android.annotation.SuppressLint
import android.app.Application
import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.webkit.CookieManager
import android.webkit.URLUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.githubuserrepos.Config
import com.example.githubuserrepos.R
import com.example.githubuserrepos.data.database.model.AppDatabase
import com.example.githubuserrepos.data.mappers.RepositoryMapper
import com.example.githubuserrepos.data.network.retrofit.ApiInterface
import com.example.githubuserrepos.data.network.retrofit.RetrofitClient
import com.example.githubuserrepos.domain.entities.RepositoryEntity
import com.example.githubuserrepos.domain.repositories.UserRepository
import retrofit2.Retrofit

class UserRepositoryImpl(private val application: Application) : UserRepository {

    private var retrofit: Retrofit = RetrofitClient.getInstance()
    private var apiInterface = retrofit.create(ApiInterface::class.java)
    private var repositoryMapper = RepositoryMapper()
    private val repositoryDao = AppDatabase.getInstance(application).repositoryDao()
    private var listRepositories: List<RepositoryEntity> = arrayListOf()

    override suspend fun getListRepository(username: String): List<RepositoryEntity> {
        listRepositories = apiInterface.getListRepositories(
            username
        ).body()?.let {
            repositoryMapper.mapListNetworkModelToListEntity(it)
        } ?: arrayListOf()
        return listRepositories
    }

    override fun getDownloadedListRepository(): LiveData<List<RepositoryEntity>> =
        Transformations.map(repositoryDao.getListRepositories()) {
            repositoryMapper.mapListDbModelToListEntity(it)
        }

    @SuppressLint("Range")
    override suspend fun downloadRepositoryUseCase(repositoryEntity: RepositoryEntity): Long {
        val response = apiInterface.inspectDownload(
            repositoryEntity.owner.login,
            repositoryEntity.name,
            Config.archiveFormat
        )
        val url = response.raw().request.url.toString()
        val headers = response.headers()
        val mimeType = getMimeType(Config.archiveFormat)
        val filename: String =
            URLUtil.guessFileName(
                url,
                headers[CONTENT_DISPOSITION],
                mimeType
            )
        val uri = Uri.parse(url)
        val cookies = CookieManager.getInstance().getCookie(url)

        val downloadManagerRequest = DownloadManager.Request(uri)
        downloadManagerRequest.setMimeType(mimeType)
        downloadManagerRequest.addRequestHeader(COOKIE_HEADER, cookies)
        downloadManagerRequest.addRequestHeader(ACCEPT_HEADER, ACCEPT_VALUE)
        downloadManagerRequest.setDescription(application.getString(R.string.downloading))
        downloadManagerRequest.setTitle(filename)
        downloadManagerRequest.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
        downloadManagerRequest.setDestinationInExternalPublicDir(
            Environment.DIRECTORY_DOWNLOADS,
            filename
        )
        val downloadManager =
            application.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        return downloadManager.enqueue(downloadManagerRequest)
    }

    private fun getMimeType(archiveFormat: String): String {
        return APPLICATION + if (archiveFormat == ZIPBALL) ZIP else TAR
    }

    override suspend fun addDownloadedRepository(repositoryEntity: RepositoryEntity) {
        repositoryDao.addRepositoryItem(repositoryMapper.mapEntityToDbModel(repositoryEntity))
    }

    override fun getItemRepository(id: Int): RepositoryEntity {
        return listRepositories.find { it.id == id }
            ?: throw RuntimeException("Repository not found")
    }

    companion object {

        private const val CONTENT_DISPOSITION = "Content-Disposition"
        private const val APPLICATION = "application/"
        private const val ZIPBALL = "zipball"
        private const val ZIP = "zip"
        private const val TAR = "tar+gzip"
        private const val COOKIE_HEADER = "cookie"
        private const val ACCEPT_HEADER = "Accept"
        private const val ACCEPT_VALUE = "application/vnd.github+json"

    }

}