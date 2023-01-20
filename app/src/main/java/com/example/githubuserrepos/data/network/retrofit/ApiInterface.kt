package com.example.githubuserrepos.data.network.retrofit

import com.example.githubuserrepos.data.network.model.RepositoryNetworkModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {

    @GET("users/{username}/repos")
    suspend fun getListRepositories(@Path("username") username: String): Response<List<RepositoryNetworkModel>>

    @GET("repos/{owner}/{repo}/{archiveFormat}")
    suspend fun inspectDownload(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Path("archiveFormat") archiveFormat: String
    ): Response<Void>

}