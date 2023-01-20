package com.example.githubuserrepos.data.mappers

import com.example.githubuserrepos.data.network.model.RepositoryNetworkModel
import com.example.githubuserrepos.domain.entities.OwnerEntity
import com.example.githubuserrepos.domain.entities.RepositoryEntity

class RepositoryMapper{

    fun mapListNetworkModelToListEntity(list: List<RepositoryNetworkModel>) = list.map {
        mapNetworkModelToEntity(it)
    }

    fun mapNetworkModelToEntity(repositoryNetworkModel: RepositoryNetworkModel): RepositoryEntity{
        val ownerEntity = OwnerEntity(
            checkEmptyString(repositoryNetworkModel.owner?.avatar_url),
            checkEmptyString(repositoryNetworkModel.owner?.html_url),
            checkEmptyInt(repositoryNetworkModel.owner?.id),
            checkEmptyString(repositoryNetworkModel.owner?.login),
            checkEmptyString(repositoryNetworkModel.owner?.node_id),
            checkEmptyString(repositoryNetworkModel.owner?.type),
            checkEmptyString(repositoryNetworkModel.owner?.url)
        )
        return RepositoryEntity(
            checkEmptyString(repositoryNetworkModel.clone_url),
            checkEmptyString(repositoryNetworkModel.description),
            checkEmptyString(repositoryNetworkModel.full_name),
            checkEmptyString(repositoryNetworkModel.git_url),
            checkEmptyString(repositoryNetworkModel.html_url),
            checkEmptyInt(repositoryNetworkModel.id),
            checkEmptyString(repositoryNetworkModel.language),
            checkEmptyString(repositoryNetworkModel.name),
            checkEmptyString(repositoryNetworkModel.node_id),
            ownerEntity,
            checkEmptyBoolean(repositoryNetworkModel.private),
            checkEmptyString(repositoryNetworkModel.ssh_url),
            checkEmptyString(repositoryNetworkModel.url),
        )
    }

    private fun checkEmptyString(string: String?): String {
        string?.let {
            return it
        } ?: return ""
    }

    private fun checkEmptyInt(int: Int?): Int {
        int?.let {
            return it
        } ?: return 0
    }

    private fun checkEmptyBoolean(boolean: Boolean?): Boolean {
        boolean?.let {
            return it
        } ?: return false
    }

}
