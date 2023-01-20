package com.example.githubuserrepos.domain.usecases

import com.example.githubuserrepos.domain.entities.RepositoryEntity
import com.example.githubuserrepos.domain.repositories.UserRepository

class GetDownloadedListRepositoryUseCase(private val userRepository: UserRepository) {

    suspend operator fun invoke(): List<RepositoryEntity> {
        return userRepository.getDownloadedListRepository()
    }

}