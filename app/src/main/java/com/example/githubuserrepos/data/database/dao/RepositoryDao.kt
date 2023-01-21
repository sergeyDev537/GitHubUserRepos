package com.example.githubuserrepos.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.githubuserrepos.data.database.model.RepositoryDbModel

@Dao
interface RepositoryDao {

    @Query("SELECT * FROM downloaded_repositories")
    fun getListRepositories(): LiveData<List<RepositoryDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addRepositoryItem(repositoryDbModel: RepositoryDbModel)

}