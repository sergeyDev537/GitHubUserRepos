package com.example.githubuserrepos.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import com.example.githubuserrepos.R
import com.example.githubuserrepos.databinding.ItemUserRepositoryBinding
import com.example.githubuserrepos.domain.entities.RepositoryEntity
import com.example.githubuserrepos.utils.loadImage

class UserRepositoriesAdapter :
    ListAdapter<RepositoryEntity, UserRepositoriesViewHolder>(UserRepositoriesDiffCallback()) {

    var clickItemRepository: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserRepositoriesViewHolder {
        val binding = ItemUserRepositoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return UserRepositoriesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserRepositoriesViewHolder, position: Int) {
        val repositoryItem = getItem(position)
        val binding = holder.binding
        val context = holder.binding.root.context

        binding.root.setOnClickListener {
            clickItemRepository?.invoke(getItem(position).id)
        }

        context.loadImage(binding.avatarUser, repositoryItem.owner.avatar_url)

        binding.tvUsername.text = String.format(
            context.getString(R.string.username_label), repositoryItem.owner.login
        )
        binding.tvNameRepo.text = String.format(
            context.getString(R.string.name_repository_label), repositoryItem.name
        )
        binding.tvLanguage.text = String.format(
            context.getString(R.string.language_label), repositoryItem.language
        )
        binding.tvBranch.text = String.format(
            context.getString(R.string.branch_label), repositoryItem.defaultBranch
        )

        if (repositoryItem.description.isNotEmpty()) {
            binding.tvDescription.text = String.format(
                context.getString(R.string.description_label), repositoryItem.description
            )
            visibleDescription(true, binding.tvDescription)
        } else {
            visibleDescription(false, binding.tvDescription)
        }
    }

    private fun visibleDescription(boolean: Boolean, textView: TextView) {
        if (boolean) {
            textView.visibility = View.VISIBLE
        } else {
            textView.visibility = View.GONE
        }
    }
}