package com.example.githubuserrepos.domain.entities

data class RepositoryEntity(
    val clone_url: String,
    val description: String,
    val full_name: String,
    val git_url: String,
    val html_url: String,
    val id: Int,
    val language: String,
    val name: String,
    val node_id: String,
    val owner: OwnerEntity,
    val `private`: Boolean,
    val ssh_url: String,
    val url: String
)