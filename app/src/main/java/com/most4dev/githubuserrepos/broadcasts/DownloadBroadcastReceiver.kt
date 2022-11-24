package com.most4dev.githubuserrepos.broadcasts

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.most4dev.githubuserrepos.R

class DownloadBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action

        if (DownloadManager.ACTION_DOWNLOAD_COMPLETE == action) {
            Toast.makeText(context, context.getString(R.string.file_downloaded), Toast.LENGTH_SHORT)
                .show()
        }
    }
}