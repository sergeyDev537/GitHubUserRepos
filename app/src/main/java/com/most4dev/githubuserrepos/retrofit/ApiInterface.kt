package com.most4dev.githubuserrepos.retrofit

import com.most4dev.githubuserrepos.model.GitHubRepository
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {

    @GET("users/{username}/repos")
    suspend fun getAllUserRepos(@Path("username") username: String): Response<List<GitHubRepository>>

}