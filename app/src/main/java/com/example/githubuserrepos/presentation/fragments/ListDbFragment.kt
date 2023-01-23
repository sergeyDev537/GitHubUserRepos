package com.example.githubuserrepos.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.githubuserrepos.R
import com.example.githubuserrepos.databinding.FragmentListDbBinding
import com.example.githubuserrepos.presentation.adapters.UserRepositoriesAdapter
import com.example.githubuserrepos.presentation.viewModels.RepositoryViewModel

class ListDbFragment : Fragment() {

    private val repositoryViewModel: RepositoryViewModel by activityViewModels()

    private val userRepositoryAdapter by lazy {
        UserRepositoriesAdapter()
    }

    private var _binding: FragmentListDbBinding? = null
    private val binding: FragmentListDbBinding
        get() = _binding ?: throw RuntimeException("FragmentListDbBinding is null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListDbBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        setObserver()
    }

    private fun setupAdapter() {
        binding.rvUserRepositoriesDb.adapter = userRepositoryAdapter
    }

    private fun setObserver() {
        repositoryViewModel.listUserRepositoriesDb.observe(viewLifecycleOwner){
            userRepositoryAdapter.submitList(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}