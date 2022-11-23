package com.most4dev.githubuserrepos.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.most4dev.githubuserrepos.R
import com.most4dev.githubuserrepos.adapters.ReposAdapter
import com.most4dev.githubuserrepos.showSnackBar
import com.most4dev.githubuserrepos.viewModels.ReposViewModel
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment() {

    private lateinit var reposViewModel: ReposViewModel
    private lateinit var reposAdapter: ReposAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_search, container, false)
        reposViewModel = ViewModelProvider(this)[ReposViewModel::class.java]
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createAdapter()

        reposViewModel.listRepos.observe(viewLifecycleOwner) { value ->
            value.let { show ->
                endLoading()
                if (show.isEmpty()) {
                    constraintLayoutSearch.showSnackBar(
                        getString(R.string.list_repos_empty)
                    )
                } else {
                    reposAdapter.setItems(show)
                }
            }
        }

        reposViewModel.listReposError.observe(viewLifecycleOwner) {
            constraintLayoutSearch.showSnackBar(it)
        }

        buttonSearch.setOnClickListener {
            if (inputUsername.text.isNotEmpty()) {
                startLoading()
                reposViewModel.getUserRepos(inputUsername.text.toString())
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
        listUserRepos.layoutManager = LinearLayoutManager(context)
        listUserRepos.adapter = reposAdapter
    }

    private fun startLoading(){
        inputUsername.isEnabled = false
        buttonSearch.isEnabled = false
        listUserRepos.isEnabled = false
        progressLoadRepos.visibility = View.VISIBLE
    }

    private fun endLoading(){
        inputUsername.isEnabled = true
        buttonSearch.isEnabled = true
        listUserRepos.isEnabled = true
        progressLoadRepos.visibility = View.GONE
    }

}