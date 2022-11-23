package com.most4dev.githubuserrepos.fragments

import android.app.DownloadManager
import android.content.IntentFilter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.most4dev.githubuserrepos.*
import com.most4dev.githubuserrepos.broadcasts.DownloadBroadcastReceiver
import com.most4dev.githubuserrepos.downloads.CustomDownloadManager
import com.most4dev.githubuserrepos.model.GitHubRepository
import com.most4dev.githubuserrepos.viewModels.ReposViewModel
import kotlinx.android.synthetic.main.fragment_details_repo.*

class DetailsRepoFragment : Fragment() {

    private lateinit var gitHubRepository: GitHubRepository
    private lateinit var reposViewModel: ReposViewModel
    private lateinit var downloadBroadcastReceiver: DownloadBroadcastReceiver

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_details_repo, container, false)
        gitHubRepository =
            requireArguments().getSerializable("GitHubRepository") as GitHubRepository
        reposViewModel = ViewModelProvider(this)[ReposViewModel::class.java]
        downloadBroadcastReceiver = DownloadBroadcastReceiver()
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fabsClick()
        checkGitHubRepo()

        reposViewModel.fileName.observe(viewLifecycleOwner) {
            CustomDownloadManager.downloadRepo(
                requireActivity(),
                replaceValue(gitHubRepository.archive_url!!),
                it,
                "applications/zip"
            )
        }

        reposViewModel.fileNameError.observe(viewLifecycleOwner) {
            constraintLayoutRepo.showSnackBar(it)
        }

    }

    private fun checkGitHubRepo() {
        if (gitHubRepository != null) {
            loadData()
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
        Glide.with(requireContext()).load(gitHubRepository.owner?.avatar_url)
            .into(shapeableImageViewProfile)
        nameRepoProfile.text = gitHubRepository.name
        authorProfile.text = gitHubRepository.owner?.login
        customDescriptionRepo.text = requireContext().createDescription(gitHubRepository)
    }

    private fun fabsClick() {
        fabOpenWeb.setOnClickListener {
            openURLBrowser(requireContext(), gitHubRepository.html_url)
            floatingButtonsMenu.close(true)
        }

        fabDownload.setOnClickListener {
            downloadRepo(
                gitHubRepository.owner?.login!!,
                gitHubRepository.name!!,
                "zipball"
            )

        }
    }

    private fun downloadRepo(owner: String, nameRepo: String, archiveFormat: String) {
        reposViewModel.inspectDownload(owner, nameRepo, archiveFormat)
    }

    override fun onDestroy() {
        super.onDestroy()
        requireActivity().unregisterReceiver(downloadBroadcastReceiver)
    }
}