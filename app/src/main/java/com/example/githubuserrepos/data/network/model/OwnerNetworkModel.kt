package com.example.githubuserrepos.data.network.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class OwnerNetworkModel(
    @SerializedName("avatar_url")
    val avatar_url: String? = null,
    @SerializedName("events_url")
    val events_url: String? = null,
    @SerializedName("followers_url")
    val followers_url: String? = null,
    @SerializedName("following_url")
    val following_url: String? = null,
    @SerializedName("gists_url")
    val gists_url: String? = null,
    @SerializedName("gravatar_id")
    val gravatar_id: String? = null,
    @SerializedName("html_url")
    val html_url: String? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("login")
    val login: String? = null,
    @SerializedName("node_id")
    val node_id: String? = null,
    @SerializedName("organizations_url")
    val organizations_url: String? = null,
    @SerializedName("received_events_url")
    val received_events_url: String? = null,
    @SerializedName("repos_url")
    val repos_url: String? = null,
    @SerializedName("site_admin")
    val site_admin: Boolean? = null,
    @SerializedName("starred_url")
    val starred_url: String? = null,
    @SerializedName("subscriptions_url")
    val subscriptions_url: String? = null,
    @SerializedName("type")
    val type: String? = null,
    @SerializedName("url")
    val url: String? = null
): Serializable