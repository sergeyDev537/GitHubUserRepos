package com.most4dev.githubuserrepos.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater.from
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.most4dev.githubuserrepos.R
import com.most4dev.githubuserrepos.model.RepositoryModel
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_repository.view.*

class ReposAdapter(private var context: Context) : RecyclerView.Adapter<ReposAdapter.ReposViewHolder>() {

    var listGitHubRepos: List<RepositoryModel> = arrayListOf()
    var clickRepo: ((RepositoryModel) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReposViewHolder {
        return ReposLayoutHolder(
            R.layout.item_search_repository,
            parent
        )
    }

    override fun onBindViewHolder(holder: ReposViewHolder, position: Int) {
        holder.bind(context, listGitHubRepos[position])
        holder.containerView.setOnClickListener {
            clickRepo?.invoke(
                listGitHubRepos[position]
            )
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

    open class ReposLayoutHolder(
        @LayoutRes val layoutRes: Int,
        parent: ViewGroup
    ) : ReposViewHolder(
        from(parent.context).inflate(
            layoutRes,
            parent,
            false
        )
    )

    open class ReposViewHolder(
        override val containerView: View
    ) : RecyclerView.ViewHolder(
        containerView
    ), LayoutContainer {

        @SuppressLint("SetTextI18n")
        fun bind(context: Context, gitHubRepository: RepositoryModel) {

            Glide.with(context).load(gitHubRepository.owner?.avatar_url)
                .into(containerView.avatarUser)

            containerView.username.text =
                context.getString(R.string.username) + ": " + gitHubRepository.owner?.login
            containerView.nameRepo.text =
                context.getString(R.string.nameRepo) + ": " + gitHubRepository.name
            containerView.createdDate.text =
                context.getString(R.string.createdDate) + ": " + gitHubRepository.created_at
            containerView.updatedDate.text =
                context.getString(R.string.updatedDate) + ": " + gitHubRepository.updated_at

            if (gitHubRepository.description == null) {
                containerView.description.visibility = View.GONE
            } else {
                containerView.description.visibility = View.VISIBLE
                containerView.description.text =
                    context.getString(R.string.description) + ": " + gitHubRepository.description
            }
        }
    }
}