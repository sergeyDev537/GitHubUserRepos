package com.example.githubuserrepos.domain.entities

data class OwnerEntity(
    val avatar_url: String,
    val html_url: String,
    val id: Int,
    val login: String,
    val node_id: String,
    val type: String,
    val url: String
)