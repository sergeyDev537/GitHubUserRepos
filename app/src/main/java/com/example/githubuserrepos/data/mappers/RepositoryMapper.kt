package com.example.githubuserrepos.data.mappers

import com.example.githubuserrepos.data.database.model.RepositoryDbModel
import com.example.githubuserrepos.data.network.model.RepositoryNetworkModel
import com.example.githubuserrepos.domain.entities.OwnerEntity
import com.example.githubuserrepos.domain.entities.RepositoryEntity

class RepositoryMapper{

    fun mapListNetworkModelToListEntity(list: List<RepositoryNetworkModel>) = list.map {
        mapNetworkModelToEntity(it)
    }

    fun mapNetworkModelToEntity(repositoryNetworkModel: RepositoryNetworkModel): RepositoryEntity{
        val ownerEntity = OwnerEntity(
            avatar_url = checkEmptyString(repositoryNetworkModel.owner?.avatar_url),
            html_url = checkEmptyString(repositoryNetworkModel.owner?.html_url),
            id = checkEmptyInt(repositoryNetworkModel.owner?.id),
            login = checkEmptyString(repositoryNetworkModel.owner?.login),
            node_id = checkEmptyString(repositoryNetworkModel.owner?.node_id),
            type = checkEmptyString(repositoryNetworkModel.owner?.type),
            url = checkEmptyString(repositoryNetworkModel.owner?.url)
        )
        return RepositoryEntity(
            archive_url = checkEmptyString(repositoryNetworkModel.archive_url),
            clone_url = checkEmptyString(repositoryNetworkModel.clone_url),
            description = checkEmptyString(repositoryNetworkModel.description),
            full_name = checkEmptyString(repositoryNetworkModel.full_name),
            git_url = checkEmptyString(repositoryNetworkModel.git_url),
            html_url = checkEmptyString(repositoryNetworkModel.html_url),
            id = checkEmptyInt(repositoryNetworkModel.id),
            language = checkEmptyString(repositoryNetworkModel.language),
            name = checkEmptyString(repositoryNetworkModel.name),
            node_id = checkEmptyString(repositoryNetworkModel.node_id),
            owner = ownerEntity,
            private = checkEmptyBoolean(repositoryNetworkModel.private),
            ssh_url = checkEmptyString(repositoryNetworkModel.ssh_url),
            url = checkEmptyString(repositoryNetworkModel.url),
            defaultBranch = checkEmptyString(repositoryNetworkModel.default_branch),
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

    fun mapListDbModelToListEntity(list: List<RepositoryDbModel>) = list.map {
        mapDbModelToEntity(it)
    }

    fun mapDbModelToEntity(repositoryDbModel: RepositoryDbModel): RepositoryEntity{
        val ownerEntity = OwnerEntity(
            avatar_url = repositoryDbModel.avatarUrl,
            html_url = repositoryDbModel.htmlUrlOwner,
            id = repositoryDbModel.idOwner,
            login = repositoryDbModel.author,
            node_id = repositoryDbModel.nodeIdOwner,
            type = repositoryDbModel.typeOwner,
            url = repositoryDbModel.ownerUrl
        )
        return RepositoryEntity(
            archive_url = repositoryDbModel.archiveUrl,
            clone_url = repositoryDbModel.cloneUrl,
            description = repositoryDbModel.description,
            full_name = repositoryDbModel.fullName,
            git_url = repositoryDbModel.gitUrl,
            html_url = repositoryDbModel.htmlUrl,
            id = repositoryDbModel.id,
            language = repositoryDbModel.language,
            name = repositoryDbModel.name,
            node_id = repositoryDbModel.nodeId,
            owner = ownerEntity,
            private = false,
            ssh_url = repositoryDbModel.sshIrl,
            url = repositoryDbModel.url,
            defaultBranch = repositoryDbModel.defaultBranch
        )
    }

    fun mapEntityToDbModel(repositoryEntity: RepositoryEntity) = RepositoryDbModel(
        id = repositoryEntity.id,
        name = repositoryEntity.name,
        archiveUrl = repositoryEntity.archive_url,
        cloneUrl = repositoryEntity.clone_url,
        description = repositoryEntity.description,
        fullName = repositoryEntity.full_name,
        gitUrl = repositoryEntity.git_url,
        htmlUrl = repositoryEntity.html_url,
        language = repositoryEntity.language,
        nodeId = repositoryEntity.node_id,
        sshIrl = repositoryEntity.ssh_url,
        url = repositoryEntity.url,
        defaultBranch = repositoryEntity.defaultBranch,
        author = repositoryEntity.owner.login,
        avatarUrl = repositoryEntity.owner.avatar_url,
        htmlUrlOwner = repositoryEntity.owner.html_url,
        idOwner = repositoryEntity.owner.id,
        nodeIdOwner = repositoryEntity.owner.node_id,
        typeOwner = repositoryEntity.owner.type,
        ownerUrl = repositoryEntity.owner.url
    )



}
