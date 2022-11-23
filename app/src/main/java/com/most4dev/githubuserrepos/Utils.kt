package com.most4dev.githubuserrepos

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.showSnackBar(msg:String){
    Snackbar.make(this, msg, Snackbar.LENGTH_SHORT).show()
}