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
import com.most4dev.githubuserrepos.model.RepoDownloadedModel
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_downloaded_repository.view.*

class ReposDownloadedAdapter(private var context: Context) :
    RecyclerView.Adapter<ReposDownloadedAdapter.ReposViewHolder>() {

    var listGitHubRepos: List<RepoDownloadedModel> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReposViewHolder {

        return ReposLayoutHolder(
            R.layout.item_downloaded_repository,
            parent
        )

    }

    override fun onBindViewHolder(holder: ReposViewHolder, position: Int) {
        holder.bind(context, listGitHubRepos[position])
    }

    override fun getItemCount(): Int {
        return listGitHubRepos.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(list: List<RepoDownloadedModel>) {
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
        fun bind(context: Context, repoDownloadedModel: RepoDownloadedModel) {

            Glide.with(context).load(repoDownloadedModel.urlAuthorRepo)
                .into(containerView.avatarUser)

            containerView.username.text =
                context.getString(R.string.author) + ": " + repoDownloadedModel.authorRepo
            containerView.nameRepo.text =
                context.getString(R.string.nameRepo) + ": " + repoDownloadedModel.nameRepo

        }
    }
}