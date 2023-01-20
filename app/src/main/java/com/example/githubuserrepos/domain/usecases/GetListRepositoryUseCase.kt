package com.example.githubuserrepos.domain.usecases

import com.example.githubuserrepos.domain.entities.RepositoryEntity
import com.example.githubuserrepos.domain.repositories.UserRepository

class GetListRepositoryUseCase(private val userRepository: UserRepository) {

    suspend operator fun invoke(username: String): List<RepositoryEntity> {
        return userRepository.getListRepository(username)
    }

}