package com.most4dev.githubuserrepos.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.most4dev.githubuserrepos.activities.MainActivity
import com.most4dev.githubuserrepos.R
import com.most4dev.githubuserrepos.adapters.ReposAdapter
import com.most4dev.githubuserrepos.databinding.FragmentSearchBinding
import com.most4dev.githubuserrepos.showSnackBar
import com.most4dev.githubuserrepos.viewModels.ReposViewModel

class SearchFragment : Fragment() {

    private lateinit var reposViewModel: ReposViewModel
    private lateinit var reposAdapter: ReposAdapter
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        reposViewModel = ViewModelProvider(this)[ReposViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createAdapter()

        reposViewModel.listRepos.observe(viewLifecycleOwner) { value ->
            value.let { show ->
                endLoading()
                if (show.isEmpty()) {
                    binding.constraintLayoutSearch.showSnackBar(
                        getString(R.string.list_repos_empty)
                    )
                } else {
                    reposAdapter.setItems(show)
                }
            }
        }

        reposViewModel.listReposError.observe(viewLifecycleOwner) {
            binding.constraintLayoutSearch.showSnackBar(it)
            endLoading()
        }

        binding.buttonSearch.setOnClickListener {
            if (binding.inputUsername.text.isNotEmpty()) {
                startLoading()
                reposViewModel.getUserRepos(binding.inputUsername.text.toString())
            } else {
                reposViewModel._listReposError.postValue(
                    getString(
                        R.string.error_input_username
                    )
                )
            }
        }
    }

    private fun createAdapter() {
        reposAdapter = ReposAdapter(requireContext())
        binding.listUserRepos.layoutManager = LinearLayoutManager(context)
        binding.listUserRepos.adapter = reposAdapter

        reposAdapter.clickRepo = {
            val bundle = Bundle()
            bundle.putSerializable("GitHubRepository", it)
            (requireActivity() as MainActivity).navController.navigate(
                R.id.action_navigation_search_repositories_to_detailsRepoFragment,
                bundle
            )
        }
    }

    private fun startLoading(){
        binding.inputUsername.isEnabled = false
        binding.buttonSearch.isEnabled = false
        binding.listUserRepos.isEnabled = false
        binding.progressLoadRepos.visibility = View.VISIBLE
    }

    private fun endLoading(){
        binding.inputUsername.isEnabled = true
        binding.buttonSearch.isEnabled = true
        binding.listUserRepos.isEnabled = true
        binding.progressLoadRepos.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}