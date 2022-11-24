package com.most4dev.githubuserrepos.fragments

import android.Manifest
import android.app.DownloadManager
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
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
    private var fileName: String? = null
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

        reposViewModel.fileNameError.observe(viewLifecycleOwner) {
            constraintLayoutRepo.showSnackBar(it)
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkGitHubRepo()
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
            reposViewModel.inspectDownload(
                repositoryModel.owner?.login!!,
                repositoryModel.name!!,
                Config.archiveFormat
            ).observe(viewLifecycleOwner) {
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P){
                    fileName = it
                    checkPermissions()
                }
                else{
                    downloadFileAndSaveList(it)
                }

            }
        }
    }

    private fun checkRepo(idRepo: Int): Boolean {
        val currentListDownloaded = databaseViewModel.listDownloadedRepos.value
        var checkRepoBoolean = false
        currentListDownloaded?.let {
            for (itemRepo in currentListDownloaded) {
                if (idRepo == itemRepo.idRepo) {
                    checkRepoBoolean = true
                    break
                } else {
                    checkRepoBoolean = false
                }
            }
        }
        return checkRepoBoolean
    }

    override fun onDestroy() {
        super.onDestroy()
        requireActivity().unregisterReceiver(downloadBroadcastReceiver)
    }

    private fun downloadFileAndSaveList(fileName: String) {
        CustomDownloadManager.downloadRepo(
            requireActivity(),
            replaceValue(repositoryModel.archive_url!!),
            fileName,
            getMimeType(Config.archiveFormat)
        )

        if (!checkRepo(repositoryModel.id!!)) {
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


    private fun checkPermissions() {
        activityResultLauncher.launch(
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    }

    private val activityResultLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()){ isGranted ->
            if (isGranted) {
                fileName?.let {
                    downloadFileAndSaveList(it)
                }

            } else {
                constraintLayoutRepo.showSnackBar(getString(R.string.permission_not_granted))
            }
        }
    }
