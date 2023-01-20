package com.example.githubuserrepos.data.network.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class RepositoryNetworkModel(
    @SerializedName("allow_forking")
    val allow_forking: Boolean? = null,
    @SerializedName("archive_url")
    val archive_url: String? = null,
    @SerializedName("archived")
    val archived: Boolean? = null,
    @SerializedName("assignees_url")
    val assignees_url: String? = null,
    @SerializedName("blobs_url")
    val blobs_url: String? = null,
    @SerializedName("branches_url")
    val branches_url: String? = null,
    @SerializedName("clone_url")
    val clone_url: String? = null,
    @SerializedName("collaborators_url")
    val collaborators_url: String? = null,
    @SerializedName("comments_url")
    val comments_url: String? = null,
    @SerializedName("commits_url")
    val commits_url: String? = null,
    @SerializedName("compare_url")
    val compare_url: String? = null,
    @SerializedName("contents_url")
    val contents_url: String? = null,
    @SerializedName("contributors_url")
    val contributors_url: String? = null,
    @SerializedName("created_at")
    val created_at: String? = null,
    @SerializedName("default_branch")
    val default_branch: String? = null,
    @SerializedName("deployments_url")
    val deployments_url: String? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("disabled")
    val disabled: Boolean? = null,
    @SerializedName("downloads_url")
    val downloads_url: String? = null,
    @SerializedName("events_url")
    val events_url: String? = null,
    @SerializedName("fork")
    val fork: Boolean? = null,
    @SerializedName("forks")
    val forks: Int? = null,
    @SerializedName("forks_count")
    val forks_count: Int? = null,
    @SerializedName("forks_url")
    val forks_url: String? = null,
    @SerializedName("full_name")
    val full_name: String? = null,
    @SerializedName("git_commits_url")
    val git_commits_url: String? = null,
    @SerializedName("git_refs_url")
    val git_refs_url: String? = null,
    @SerializedName("git_tags_url")
    val git_tags_url: String? = null,
    @SerializedName("git_url")
    val git_url: String? = null,
    @SerializedName("has_discussions")
    val has_discussions: Boolean? = null,
    @SerializedName("has_downloads")
    val has_downloads: Boolean? = null,
    @SerializedName("has_issues")
    val has_issues: Boolean? = null,
    @SerializedName("has_pages")
    val has_pages: Boolean? = null,
    @SerializedName("has_projects")
    val has_projects: Boolean? = null,
    @SerializedName("has_wiki")
    val has_wiki: Boolean? = null,
    @SerializedName("homepage")
    val homepage: String? = null,
    @SerializedName("hooks_url")
    val hooks_url: String? = null,
    @SerializedName("html_url")
    val html_url: String,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("is_template")
    val is_template: Boolean? = null,
    @SerializedName("issue_comment_url")
    val issue_comment_url: String? = null,
    @SerializedName("issue_events_url")
    val issue_events_url: String? = null,
    @SerializedName("issues_url")
    val issues_url: String? = null,
    @SerializedName("keys_url")
    val keys_url: String? = null,
    @SerializedName("labels_url")
    val labels_url: String? = null,
    @SerializedName("language")
    val language: String? = null,
    @SerializedName("languages_url")
    val languages_url: String? = null,
    @SerializedName("license")
    val license: Any? = null,
    @SerializedName("merges_url")
    val merges_url: String? = null,
    @SerializedName("milestones_url")
    val milestones_url: String? = null,
    @SerializedName("mirror_url")
    val mirror_url: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("node_id")
    val node_id: String? = null,
    @SerializedName("notifications_url")
    val notifications_url: String? = null,
    @SerializedName("open_issues")
    val open_issues: Int? = null,
    @SerializedName("open_issues_count")
    val open_issues_count: Int? = null,
    @SerializedName("owner")
    val owner: OwnerNetworkModel? = null,
    @SerializedName("private")
    val `private`: Boolean? = null,
    @SerializedName("pulls_url")
    val pulls_url: String? = null,
    @SerializedName("pushed_at")
    val pushed_at: String? = null,
    @SerializedName("releases_url")
    val releases_url: String? = null,
    @SerializedName("size")
    val size: Int? = null,
    @SerializedName("ssh_url")
    val ssh_url: String? = null,
    @SerializedName("stargazers_count")
    val stargazers_count: Int? = null,
    @SerializedName("stargazers_url")
    val stargazers_url: String? = null,
    @SerializedName("statuses_url")
    val statuses_url: String? = null,
    @SerializedName("subscribers_url")
    val subscribers_url: String? = null,
    @SerializedName("subscription_url")
    val subscription_url: String? = null,
    @SerializedName("svn_url")
    val svn_url: String? = null,
    @SerializedName("tags_url")
    val tags_url: String? = null,
    @SerializedName("teams_url")
    val teams_url: String? = null,
    @SerializedName("topics")
    val topics: List<Any>? = null,
    @SerializedName("trees_url")
    val trees_url: String? = null,
    @SerializedName("updated_at")
    val updated_at: String? = null,
    @SerializedName("url")
    val url: String? = null,
    @SerializedName("visibility")
    val visibility: String? = null,
    @SerializedName("watchers")
    val watchers: Int? = null,
    @SerializedName("watchers_count")
    val watchers_count: Int? = null,
    @SerializedName("web_commit_signoff_required")
    val web_commit_signoff_required: Boolean? = null
): Serializable