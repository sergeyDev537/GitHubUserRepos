package com.example.githubuserrepos.utils

import android.content.Context
import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar


fun Context.loadImage(imageView: ImageView, url: String) {
    Glide.with(this)
        .load(url)
        .centerCrop()
        .into(imageView)
}

fun View.showSnackBar(msg: String) {
    Snackbar.make(this, msg, Snackbar.LENGTH_LONG).show()
}

fun Context.openUrl(url: String){
    val customTabsIntent = CustomTabsIntent.Builder().build()
    customTabsIntent.launchUrl(this, Uri.parse(url))
}

fun Context.showToast(message: String){
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}