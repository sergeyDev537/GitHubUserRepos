package com.example.githubuserrepos.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.githubuserrepos.databinding.FragmentSearchBinding
import com.example.githubuserrepos.presentation.adapters.UserRepositoriesAdapter
import com.example.githubuserrepos.presentation.viewModels.RepositoryViewModel
import com.example.githubuserrepos.utils.showSnackBar

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding: FragmentSearchBinding
        get() = _binding ?: throw RuntimeException("FragmentSearchBinding is null")

    private val repositoryViewModel: RepositoryViewModel by activityViewModels()

    private val userRepositoryAdapter by lazy {
        UserRepositoriesAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        setClickListeners()
        setObserver()
    }

    private fun setObserver() {
        repositoryViewModel.listUserRepositories.observe(viewLifecycleOwner) {
            userRepositoryAdapter.submitList(it)
        }
        repositoryViewModel.listUserRepositoriesError.observe(viewLifecycleOwner) {
            binding.root.showSnackBar(it)
        }
        repositoryViewModel.startLoading.observe(viewLifecycleOwner) {
            visibleProgressBar(true)
        }
        repositoryViewModel.endLoading.observe(viewLifecycleOwner) {
            visibleProgressBar(false)
        }
    }

    private fun visibleProgressBar(boolean: Boolean) {
        if (boolean) {
            binding.rvUserRepositories.visibility = View.GONE
            binding.loadRepositories.visibility = View.VISIBLE
        } else {
            binding.rvUserRepositories.visibility = View.VISIBLE
            binding.loadRepositories.visibility = View.GONE
        }
    }

    private fun setClickListeners() {
        binding.buttonSearch.setOnClickListener {
            repositoryViewModel.getListUserRepositories(binding.etUsername.text.toString())
        }
    }

    private fun setupAdapter() {
        userRepositoryAdapter.clickItemRepository = {
            findNavController().navigate(
                SearchFragmentDirections.actionNavigationSearchRepositoriesToItemFragment(
                    it
                )
            )
        }
        binding.rvUserRepositories.adapter = userRepositoryAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}