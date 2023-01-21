package com.example.githubuserrepos.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "downloaded_repositories")
data class RepositoryDbModel (
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "clone_url") val cloneUrl: String,
    @ColumnInfo(name = "archive_url") val archiveUrl: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "full_name") val fullName: String,
    @ColumnInfo(name = "git_url") val gitUrl: String,
    @ColumnInfo(name = "html_url") val htmlUrl: String,
    @ColumnInfo(name = "language") val language: String,
    @ColumnInfo(name = "node_id") val nodeId: String,
    @ColumnInfo(name = "ssh_url") val sshIrl: String,
    @ColumnInfo(name = "url") val url: String,
    @ColumnInfo(name = "default_branch") val defaultBranch: String,
    /*OWNER*/
    @ColumnInfo(name = "author") val author: String,
    @ColumnInfo(name = "avatar_url") val avatarUrl: String,
    @ColumnInfo(name = "html_url_owner") val htmlUrlOwner: String,
    @ColumnInfo(name = "id_owner") val idOwner: Int,
    @ColumnInfo(name = "node_id_owner") val nodeIdOwner: String,
    @ColumnInfo(name = "type_owner") val typeOwner: String,
    @ColumnInfo(name = "url_owner") val ownerUrl: String,
)