package com.most4dev.githubuserrepos.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.most4dev.githubuserrepos.GitHubUserReposApp
import com.most4dev.githubuserrepos.adapters.ReposDownloadedAdapter
import com.most4dev.githubuserrepos.databinding.FragmentDownloadedReposBinding
import com.most4dev.githubuserrepos.viewModels.DatabaseModelFactory
import com.most4dev.githubuserrepos.viewModels.DatabaseViewModel

class DownloadedReposFragment : Fragment() {

    private lateinit var reposDownloadedAdapter: ReposDownloadedAdapter
    private var _binding: FragmentDownloadedReposBinding? = null
    private val binding get() = _binding!!
    private val databaseViewModel: DatabaseViewModel by viewModels {
        DatabaseModelFactory((requireActivity().application as GitHubUserReposApp).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDownloadedReposBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createAdapter()
        databaseViewModel.listDownloadedRepos.observe(viewLifecycleOwner){
            if (it.isEmpty()){
                binding.recyclerDownloadedRepos.visibility = View.GONE
                binding.messageListEmpty.visibility = View.VISIBLE
            }
            else{
                reposDownloadedAdapter.setItems(it)
                binding.recyclerDownloadedRepos.visibility = View.VISIBLE
                binding.messageListEmpty.visibility = View.GONE
            }

        }
    }

    private fun createAdapter() {
        reposDownloadedAdapter = ReposDownloadedAdapter(requireContext())
        binding.recyclerDownloadedRepos.layoutManager = LinearLayoutManager(context)
        binding.recyclerDownloadedRepos.adapter = reposDownloadedAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}