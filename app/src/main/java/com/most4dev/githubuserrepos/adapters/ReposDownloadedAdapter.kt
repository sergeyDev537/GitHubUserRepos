package com.most4dev.githubuserrepos.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater.from
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.most4dev.githubuserrepos.R
import com.most4dev.githubuserrepos.databinding.ItemDownloadedRepositoryBinding
import com.most4dev.githubuserrepos.model.RepoDownloadedModel

class ReposDownloadedAdapter(private var context: Context) :
    RecyclerView.Adapter<ReposDownloadedAdapter.DownloadedReposViewHolder>() {

    private var listGitHubRepos: List<RepoDownloadedModel> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DownloadedReposViewHolder {
        val binding = ItemDownloadedRepositoryBinding
            .inflate(from(parent.context), parent, false)
        return DownloadedReposViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: DownloadedReposViewHolder, position: Int) {
        Glide.with(context).load(listGitHubRepos[position].urlAuthorRepo)
            .into(holder.binding.avatarUser)

        holder.binding.username.text =
            context.getString(R.string.author) + ": " + listGitHubRepos[position].authorRepo
        holder.binding.nameRepo.text =
            context.getString(R.string.nameRepo) + ": " + listGitHubRepos[position].nameRepo
    }

    override fun getItemCount(): Int {
        return listGitHubRepos.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(list: List<RepoDownloadedModel>) {
        listGitHubRepos = list
        notifyDataSetChanged()
    }

    inner class DownloadedReposViewHolder(val binding: ItemDownloadedRepositoryBinding) :
        RecyclerView.ViewHolder(binding.root)

}