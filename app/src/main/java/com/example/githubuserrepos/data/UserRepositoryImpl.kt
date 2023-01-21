package com.example.githubuserrepos.data

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
        val response = apiInterface.inspectDownload(
            repositoryEntity.owner.login,
            repositoryEntity.name,
            Config.archiveFormat
        )
        val headers = response.headers()
        val mimeType = getMimeType(Config.archiveFormat)
        val filename: String =
            URLUtil.guessFileName(
                response.raw().request.url.toString(),
                headers[CONTENT_DISPOSITION],
                mimeType
            )
        val urlDownload = repositoryEntity.archive_url
            .replace(ARCHIVE_FORMAT, mimeType)
            .replace(REF, "/${repositoryEntity.defaultBranch}")
        val uri = Uri.parse(urlDownload)
        val cookies = CookieManager.getInstance().getCookie(urlDownload)

        val downloadManagerRequest = DownloadManager.Request(uri)
        downloadManagerRequest.setMimeType(mimeType)
        downloadManagerRequest.addRequestHeader(COOKIE_HEADER, cookies)
        downloadManagerRequest.addRequestHeader(ACCEPT_HEADER, ACCEPT_VALUE)
        downloadManagerRequest.setDescription(application.getString(R.string.downloading_file))
        downloadManagerRequest.setTitle(filename)
        downloadManagerRequest.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
        downloadManagerRequest.setDestinationInExternalPublicDir(
            Environment.DIRECTORY_DOWNLOADS,
            filename
        )
        val downloadManager =
            application.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager?
        downloadManager?.enqueue(downloadManagerRequest)
    }

    private fun getMimeType(archiveFormat: String): String {
        return APPLICATION + if (archiveFormat == ZIPBALL) ZIP else TAR
    }

    override suspend fun addDownloadedRepository(repositoryEntity: RepositoryEntity) {
        repositoryDao.addRepositoryItem(repositoryMapper.mapEntityToDbModel(repositoryEntity))
    }

    companion object {

        private const val CONTENT_DISPOSITION = "Content-Disposition"
        private const val APPLICATION = "application/"
        private const val ZIPBALL = "zipball"
        private const val ZIP = "zip"
        private const val TAR = "tar+gzip"
        private const val ARCHIVE_FORMAT = "{archive_format}"
        private const val REF = "{/ref}"
        private const val COOKIE_HEADER = "cookie"
        private const val ACCEPT_HEADER = "Accept"
        private const val ACCEPT_VALUE = "application/vnd.github+json"

    }

}