package com.example.githubuserrepos.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.githubuserrepos.R
import com.example.githubuserrepos.databinding.FragmentItemBinding
import com.example.githubuserrepos.domain.entities.RepositoryEntity
import com.example.githubuserrepos.presentation.viewModels.RepositoryViewModel
import com.example.githubuserrepos.utils.loadImage
import com.example.githubuserrepos.utils.openUrl
import com.example.githubuserrepos.utils.showSnackBar

class ItemFragment : Fragment() {

    private val args by navArgs<ItemFragmentArgs>()

    private val repositoryViewModel: RepositoryViewModel by activityViewModels()

    private lateinit var itemRepository: RepositoryEntity

    private var _binding: FragmentItemBinding? = null
    private val binding: FragmentItemBinding
        get() = _binding ?: throw RuntimeException("FragmentItemBinding is null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListeners()
        setObserve()
        repositoryViewModel.getItemRepository(args.repositoryId)
    }

    private fun setClickListeners() {
        binding.fabWeb.setOnClickListener {
            requireContext().openUrl(itemRepository.html_url)
        }
        binding.fabDownload.setOnClickListener {
            //TODO
        }
    }

    private fun setObserve() {
        repositoryViewModel.itemRepository.observe(viewLifecycleOwner) {
            itemRepository = it
            setData(it)
        }
        repositoryViewModel.itemRepositoryError.observe(viewLifecycleOwner) {
            binding.root.showSnackBar(it)
        }
    }

    private fun setData(repositoryEntity: RepositoryEntity) {
        requireContext().loadImage(binding.imageAuthor, repositoryEntity.owner.avatar_url)
        with(binding) {
            tvAuthorName.text =
                String.format(
                    requireContext().getString(R.string.author_repository_label),
                    repositoryEntity.owner.login
                )
            tvAuthorProfile.text =
                String.format(
                    requireContext().getString(R.string.author_profile_repository_label),
                    repositoryEntity.owner.url
                )
            tvAuthorType.text =
                String.format(
                    requireContext().getString(R.string.author_type_repository_label),
                    repositoryEntity.owner.type
                )
            tvIdRepository.text =
                String.format(
                    requireContext().getString(R.string.id_repository_label),
                    repositoryEntity.id.toString()
                )
            tvNodeIdRepository.text =
                String.format(
                    requireContext().getString(R.string.node_id_repository_label),
                    repositoryEntity.node_id
                )
            tvName.text =
                String.format(
                    requireContext().getString(R.string.name_repository_label),
                    repositoryEntity.name
                )
            tvFullName.text =
                String.format(
                    requireContext().getString(R.string.full_name_repository_label),
                    repositoryEntity.full_name
                )
            tvLanguage.text =
                String.format(
                    requireContext().getString(R.string.language_label),
                    repositoryEntity.language
                )
            tvDefaultBranch.text =
                String.format(
                    requireContext().getString(R.string.default_branch_repository_label),
                    repositoryEntity.defaultBranch
                )
            tvUrlRepository.text =
                String.format(
                    requireContext().getString(R.string.url_repository_label),
                    repositoryEntity.url
                )
            tvSshUrlRepository.text =
                String.format(
                    requireContext().getString(R.string.ssh_url_repository_label),
                    repositoryEntity.ssh_url
                )
            tvHtmlUrlRepository.text =
                String.format(
                    requireContext().getString(R.string.html_url_repository_label),
                    repositoryEntity.html_url
                )
            tvGitUrlRepository.text =
                String.format(
                    requireContext().getString(R.string.git_url_repository_label),
                    repositoryEntity.git_url
                )
            tvCloneUrlRepository.text =
                String.format(
                    requireContext().getString(R.string.clone_url_repository_label),
                    repositoryEntity.clone_url
                )
            tvArchiveUrlRepository.text =
                String.format(
                    requireContext().getString(R.string.archive_url_repository_label),
                    repositoryEntity.archive_url
                )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}