package com.most4dev.githubuserrepos.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.most4dev.githubuserrepos.R
import com.most4dev.githubuserrepos.createDescription
import com.most4dev.githubuserrepos.model.GitHubRepository
import com.most4dev.githubuserrepos.openURLBrowser
import com.most4dev.githubuserrepos.showSnackBar
import kotlinx.android.synthetic.main.fragment_details_repo.*

class DetailsRepoFragment : Fragment() {

    private lateinit var gitHubRepository: GitHubRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_details_repo, container, false)
        gitHubRepository = requireArguments().getSerializable("GitHubRepository") as GitHubRepository
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (gitHubRepository != null){
            loadData()
        }
        else{
            constraintLayoutRepo.showSnackBar(
                getString(R.string.not_found_repo)
            )
        }

        fabOpenWeb.setOnClickListener {
            openURLBrowser(requireContext(), gitHubRepository.html_url)
            floatingButtonsMenu.close(true)
        }

    }

    private fun loadData() {
        Glide.with(requireContext()).load(gitHubRepository.owner?.avatar_url)
            .into(shapeableImageViewProfile)
        nameRepoProfile.text = gitHubRepository.name
        authorProfile.text = gitHubRepository.owner?.login
        customDescriptionRepo.text = requireContext().createDescription(gitHubRepository)
    }
}