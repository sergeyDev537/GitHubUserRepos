package com.most4dev.githubuserrepos.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "downloaded_repos_table")
data class RepoDownloadedModel (
    @PrimaryKey
    @ColumnInfo(name = "id_repo") val idRepo: Int,
    @ColumnInfo(name = "name_repo") val nameRepo: String,
    @ColumnInfo(name = "author_repo") val authorRepo: String,
    @ColumnInfo(name = "url_avatar_repo") val urlAuthorRepo: String
)