package com.example.githubuserrepos.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.githubuserrepos.domain.entities.RepositoryEntity

class UserRepositoriesDiffCallback : DiffUtil.ItemCallback<RepositoryEntity>() {

    override fun areItemsTheSame(oldItem: RepositoryEntity, newItem: RepositoryEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: RepositoryEntity, newItem: RepositoryEntity): Boolean {
        return oldItem == newItem
    }
}