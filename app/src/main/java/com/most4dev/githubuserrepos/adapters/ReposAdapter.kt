package com.most4dev.githubuserrepos.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater.from
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.most4dev.githubuserrepos.R
import com.most4dev.githubuserrepos.databinding.ItemSearchRepositoryBinding
import com.most4dev.githubuserrepos.model.RepositoryModel

class ReposAdapter(private var context: Context) :
    RecyclerView.Adapter<ReposAdapter.ReposViewHolder>() {

    var listGitHubRepos: List<RepositoryModel> = arrayListOf()
    var clickRepo: ((RepositoryModel) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReposViewHolder {
        val binding = ItemSearchRepositoryBinding
            .inflate(from(parent.context), parent, false)
        return ReposViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ReposAdapter.ReposViewHolder, position: Int) {
        Glide.with(context).load(listGitHubRepos[position].owner?.avatar_url)
            .into(holder.binding.avatarUser)

        holder.binding.username.text =
            context.getString(R.string.username) + ": " + listGitHubRepos[position].owner?.login
        holder.binding.nameRepo.text =
            context.getString(R.string.nameRepo) + ": " + listGitHubRepos[position].name
        holder.binding.createdDate.text =
            context.getString(R.string.createdDate) + ": " + listGitHubRepos[position].created_at
        holder.binding.updatedDate.text =
            context.getString(R.string.updatedDate) + ": " + listGitHubRepos[position].updated_at

        if (listGitHubRepos[position].description == null) {
            holder.binding.description.visibility = View.GONE
        } else {
            holder.binding.description.visibility = View.VISIBLE
            holder.binding.description.text =
                context.getString(R.string.description) + ": " + listGitHubRepos[position].description
        }
    }

    override fun getItemCount(): Int {
        return listGitHubRepos.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(list: List<RepositoryModel>) {
        listGitHubRepos = list
        notifyDataSetChanged()
    }

    inner class ReposViewHolder(val binding: ItemSearchRepositoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.containerViewSearch.setOnClickListener {
                clickRepo?.invoke(
                    listGitHubRepos[adapterPosition]
                )
            }
        }
    }

}