package com.example.solotwitter.ui.fragment.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.solotwitter.R
import com.example.solotwitter.databinding.FragmentProfileBinding
import com.example.solotwitter.db.RepositoryProvider
import com.example.solotwitter.ui.adapters.FeedAdapter

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var viewModel: ProfileFragmentViewModel
    private lateinit var feedAdapter: FeedAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        binding.lifecycleOwner = this

        initViewModel()
        initRecyclerView()
        setObservables()

        return binding.root
    }

    private fun initViewModel() {
        val factory = ProfileFragmentViewModelFactory(
            RepositoryProvider.tweetRepository,
            RepositoryProvider.userRepository
        )
        viewModel = ViewModelProvider(this, factory)[ProfileFragmentViewModel::class.java]
        binding.viewModel = viewModel
        viewModel.setUser()
        viewModel.fetchTweets()
    }

    private fun initRecyclerView() {
        feedAdapter = FeedAdapter()
        binding.rvTweetProfile.apply {
            adapter = feedAdapter
            layoutManager = LinearLayoutManager(this@ProfileFragment.context)
        }
        displayTweetList()
    }

    private fun setObservables() {
        viewModel.eventHandler.observe(this) {
            it.getContentIfNotHandled()?.let { event ->
                when (event) {
                    ProfileFragmentEvents.NAVIGATE_TO_HOME -> findNavController().navigate(R.id.action_profileFragment_to_homeFragment)
                }
            }
        }
    }

    private fun displayTweetList() {
        viewModel.tweets?.observe(this) { tweetList ->
            feedAdapter.setTweetList(tweetList)
            feedAdapter.notifyDataSetChanged()
        }
    }
}