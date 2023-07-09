package com.example.solotwitter.ui.fragment.feed

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.solotwitter.AppSharedPref
import com.example.solotwitter.R
import com.example.solotwitter.databinding.FragmentFeedBinding
import com.example.solotwitter.db.RepositoryProvider
import com.example.solotwitter.ui.adapters.FeedAdapter

class FeedFragment : Fragment() {
    private lateinit var binding: FragmentFeedBinding
    private lateinit var viewModel: FeedFragmentViewModel
    private lateinit var feedAdapter: FeedAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i("MyTag", "feed fragment")
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_feed, container, false)
        binding.lifecycleOwner = this

        initViewModel()
        initRecyclerView()
        setObservables()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.resetSelectedUser()
    }

    private fun initViewModel() {
        val factory = FeedFragmentViewModelFactory(
            RepositoryProvider.tweetRepository,
            RepositoryProvider.userRepository
        )
        viewModel = ViewModelProvider(this, factory)[FeedFragmentViewModel::class.java]
        binding.viewModel = viewModel
        viewModel.setUser(AppSharedPref.getLoggedInUserId())
        viewModel.fetchTweets()
    }

    private fun initRecyclerView() {
        feedAdapter = FeedAdapter {
            onTweetUsernameClicked(it)
        }
        binding.rvTweetFeed.apply {
            adapter = feedAdapter
            layoutManager = LinearLayoutManager(this@FeedFragment.context)
        }
        displayTweetList()
    }

    private fun setObservables() {
        viewModel.eventHandler.observe(this) {
            it.getContentIfNotHandled()?.let { event ->
                when (event) {
                    FeedFragmentEvents.NAVIGATE_TO_PROFILE -> findNavController().navigate(R.id.action_feedFragment_to_profileFragment)
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

    private fun onTweetUsernameClicked(userId: Int) {
        Log.i("MyTag", "@FeedFragment: onTweetUsernameClicked $userId")
        viewModel.setSelectedUser(userId)
        findNavController().navigate(R.id.action_feedFragment_to_profileFragment)
    }
}