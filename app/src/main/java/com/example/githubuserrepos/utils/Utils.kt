package com.example.githubuserrepos.utils

import android.content.Context
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar

fun Context.loadImage(url: String, imageView: ImageView) {
    Glide.with(this)
        .load(url)
        .centerCrop()
        .into(imageView)
}

fun View.showSnackBar(msg: String) {
    Snackbar.make(this, msg, Snackbar.LENGTH_LONG).show()
}