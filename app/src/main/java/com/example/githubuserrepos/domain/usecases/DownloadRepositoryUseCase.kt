package com.example.githubuserrepos.domain.usecases

import com.example.githubuserrepos.domain.repositories.UserRepository

class DownloadRepositoryUseCase(private val userRepository: UserRepository) {

    suspend operator fun invoke(url: String){
        userRepository.downloadRepositoryUseCase(url)
    }

}