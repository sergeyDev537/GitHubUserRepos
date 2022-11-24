package com.most4dev.githubuserrepos.fragments

import android.app.DownloadManager
import android.content.IntentFilter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.most4dev.githubuserrepos.*
import com.most4dev.githubuserrepos.broadcasts.DownloadBroadcastReceiver
import com.most4dev.githubuserrepos.downloads.CustomDownloadManager
import com.most4dev.githubuserrepos.model.RepoDownloadedModel
import com.most4dev.githubuserrepos.model.RepositoryModel
import com.most4dev.githubuserrepos.viewModels.DatabaseModelFactory
import com.most4dev.githubuserrepos.viewModels.DatabaseViewModel
import com.most4dev.githubuserrepos.viewModels.ReposViewModel
import kotlinx.android.synthetic.main.fragment_details_repo.*

class DetailsRepoFragment : Fragment() {

    private lateinit var repositoryModel: RepositoryModel
    private lateinit var reposViewModel: ReposViewModel
    private lateinit var downloadBroadcastReceiver: DownloadBroadcastReceiver
    private val databaseViewModel: DatabaseViewModel by viewModels {
        DatabaseModelFactory((requireActivity().application as GitHubUserReposApp).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_details_repo, container, false)
        repositoryModel =
            requireArguments().getSerializable("GitHubRepository") as RepositoryModel
        reposViewModel = ViewModelProvider(this)[ReposViewModel::class.java]
        downloadBroadcastReceiver = DownloadBroadcastReceiver()
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkGitHubRepo()

        reposViewModel.fileName.observe(viewLifecycleOwner) {
            CustomDownloadManager.downloadRepo(
                requireActivity(),
                replaceValue(repositoryModel.archive_url!!),
                it,
                "applications/zip"
            )
        }

        reposViewModel.fileNameError.observe(viewLifecycleOwner) {
            constraintLayoutRepo.showSnackBar(it)
        }

    }

    private fun checkGitHubRepo() {
        if (repositoryModel != null) {
            loadData()
            fabsClick()
        } else {
            constraintLayoutRepo.showSnackBar(
                getString(R.string.not_found_repo)
            )
        }
    }

    override fun onResume() {
        super.onResume()
        requireActivity().registerReceiver(
            downloadBroadcastReceiver,
            IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        )
    }

    private fun loadData() {
        Glide.with(requireContext()).load(repositoryModel.owner?.avatar_url)
            .into(shapeableImageViewProfile)
        nameRepoProfile.text = repositoryModel.name
        authorProfile.text = repositoryModel.owner?.login
        customDescriptionRepo.text = requireContext().createDescription(repositoryModel)
    }

    private fun fabsClick() {
        fabOpenWeb.setOnClickListener {
            openURLBrowser(requireContext(), repositoryModel.html_url)
            floatingButtonsMenu.close(true)
        }

        fabDownload.setOnClickListener {
            downloadRepo(
                repositoryModel.owner?.login!!,
                repositoryModel.name!!,
                "zipball"
            )
            if (!checkRepo(repositoryModel.id!!)){
                databaseViewModel.insertDownloadedRepo(
                    RepoDownloadedModel(
                        repositoryModel.id!!,
                        repositoryModel.name!!,
                        repositoryModel.owner!!.login!!,
                        repositoryModel.owner!!.avatar_url!!
                    )
                )
            }


        }
    }

    private fun checkRepo(idRepo: Int): Boolean {
        val currentListDownloaded = databaseViewModel.listDownloadedRepos.value
        var checkRepoBoolean = false
        currentListDownloaded?.let {
            for (itemRepo in currentListDownloaded!!){
                if (idRepo == itemRepo.idRepo){
                    checkRepoBoolean = true
                    break
                }
                else{
                    checkRepoBoolean = false
                }
            }
        }
        return checkRepoBoolean
    }

    private fun downloadRepo(owner: String, nameRepo: String, archiveFormat: String) {
        reposViewModel.inspectDownload(owner, nameRepo, archiveFormat)
    }

    override fun onDestroy() {
        super.onDestroy()
        requireActivity().unregisterReceiver(downloadBroadcastReceiver)
    }
}