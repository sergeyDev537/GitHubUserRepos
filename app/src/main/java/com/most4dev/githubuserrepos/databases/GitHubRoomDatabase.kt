package com.most4dev.githubuserrepos.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.most4dev.githubuserrepos.model.RepoDownloadedModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(
    entities = [RepoDownloadedModel::class],
    version = 1, exportSchema = false
)
abstract class GitHubRoomDatabase: RoomDatabase() {

    abstract fun gitHubDao(): GitHubDao

    private class GitHubDatabaseCallback(
        private val scope: CoroutineScope
    ) : Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    database.gitHubDao()
                }
            }
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: GitHubRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): GitHubRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GitHubRoomDatabase::class.java,
                    "github_database"
                )
                    .addCallback(GitHubDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

}