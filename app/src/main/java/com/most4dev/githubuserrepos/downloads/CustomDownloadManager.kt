package com.most4dev.githubuserrepos.downloads

import android.app.Activity
import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.webkit.CookieManager
import android.widget.Toast
import com.most4dev.githubuserrepos.R
import okhttp3.internal.userAgent

class CustomDownloadManager {

    companion object {

        fun downloadRepo(
            activity: Activity,
            urlDownload: String,
            filename: String,
            mimeType: String
        ) {
            Toast.makeText(
                activity,
                activity.getString(R.string.downloading_file),
                Toast.LENGTH_SHORT
            ).show()
            val uri = Uri.parse(urlDownload)
            val downloadManagerRequest = DownloadManager.Request(uri)
            downloadManagerRequest.setMimeType(mimeType)
            val cookies = CookieManager.getInstance().getCookie(urlDownload)
            downloadManagerRequest.addRequestHeader("cookie", cookies)
            downloadManagerRequest.addRequestHeader("User-Agent", userAgent)
            downloadManagerRequest.addRequestHeader("Accept", "application/vnd.github+json")
            downloadManagerRequest.setDescription(activity.getString(R.string.downloading_file))

            downloadManagerRequest.setTitle(
                filename
            )
            downloadManagerRequest.setNotificationVisibility(
                DownloadManager.Request.VISIBILITY_VISIBLE
            )
            downloadManagerRequest.setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS,
                filename
            )
            val downloadManager =
                activity.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager?
            downloadManager!!.enqueue(downloadManagerRequest)
        }

    }

}