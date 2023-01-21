package com.example.githubuserrepos.domain.usecases

import com.example.githubuserrepos.domain.entities.RepositoryEntity
import com.example.githubuserrepos.domain.repositories.UserRepository

class AddRepositoryItemDbUseCase(private val userRepository: UserRepository) {

    suspend operator fun invoke(repositoryEntity: RepositoryEntity){
        userRepository.addDownloadedRepository(repositoryEntity)
    }

}