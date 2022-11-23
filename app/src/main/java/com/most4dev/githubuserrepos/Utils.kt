package com.most4dev.githubuserrepos

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.most4dev.githubuserrepos.model.GitHubRepository

fun View.showSnackBar(msg: String) {
    Snackbar.make(this, msg, Snackbar.LENGTH_SHORT).show()
}

fun Context.createDescription(gitHubRepository: GitHubRepository): String {
    return getString(R.string.id_repo) + ": " + gitHubRepository.id + "\n" +
            getString(R.string.node_id) + ": " + gitHubRepository.node_id + "\n" +
            getString(R.string.language) + ": " + gitHubRepository.language + "\n" +
            getString(R.string.visibility) + ": " + gitHubRepository.private + "\n" +
            getString(R.string.size) + ": " + gitHubRepository.size + "\n" +
            getString(R.string.createdDate) + ": " + gitHubRepository.created_at + "\n" +
            getString(R.string.updatedDate) + ": " + gitHubRepository.updated_at + "\n" +
            getString(R.string.pushedDate) + ": " + gitHubRepository.pushed_at + "\n" +
            getString(R.string.url_profile) + ": " + gitHubRepository.owner?.url + "\n" +
            getString(R.string.url_repos) + ": " + gitHubRepository.html_url + "\n"

}

fun openURLBrowser(context: Context, url: String){
    val i = Intent(Intent.ACTION_VIEW)
    i.data = Uri.parse(url)
    context.startActivity(i)
}

fun replaceValue(stringDefault: String): String{
    //https://api.github.com/repos/sergey-saskovec/AcquaintanceApp/{archive_format}{/ref}
    var str1 = stringDefault.substring(0, stringDefault.length-6)
    return str1.replace("{archive_format}", "zipball")
}