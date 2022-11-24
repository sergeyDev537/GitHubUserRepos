package com.most4dev.githubuserrepos.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.most4dev.githubuserrepos.GitHubUserReposApp
import com.most4dev.githubuserrepos.R
import com.most4dev.githubuserrepos.adapters.ReposDownloadedAdapter
import com.most4dev.githubuserrepos.viewModels.DatabaseModelFactory
import com.most4dev.githubuserrepos.viewModels.DatabaseViewModel
import kotlinx.android.synthetic.main.fragment_downloaded_repos.*

class DownloadedReposFragment : Fragment() {

    private lateinit var reposDownloadedAdapter: ReposDownloadedAdapter
    private val databaseViewModel: DatabaseViewModel by viewModels {
        DatabaseModelFactory((requireActivity().application as GitHubUserReposApp).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_downloaded_repos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createAdapter()
        databaseViewModel.listDownloadedRepos.observe(viewLifecycleOwner){
            if (it.isEmpty()){
                recyclerDownloadedRepos.visibility = View.GONE
                messageListEmpty.visibility = View.VISIBLE
            }
            else{
                reposDownloadedAdapter.setItems(it)
                recyclerDownloadedRepos.visibility = View.VISIBLE
                messageListEmpty.visibility = View.GONE
            }

        }
    }

    private fun createAdapter() {
        reposDownloadedAdapter = ReposDownloadedAdapter(requireContext())
        recyclerDownloadedRepos.layoutManager = LinearLayoutManager(context)
        recyclerDownloadedRepos.adapter = reposDownloadedAdapter
    }
}