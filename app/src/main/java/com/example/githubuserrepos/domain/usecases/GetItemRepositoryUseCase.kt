package com.example.githubuserrepos.domain.usecases

import com.example.githubuserrepos.domain.entities.RepositoryEntity
import com.example.githubuserrepos.domain.repositories.UserRepository

class GetItemRepositoryUseCase(private val userRepository: UserRepository) {

    operator fun invoke(id: Int): RepositoryEntity{
        return userRepository.getItemRepository(id)
    }

}