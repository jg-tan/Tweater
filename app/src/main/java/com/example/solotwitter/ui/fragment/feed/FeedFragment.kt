package com.example.solotwitter.ui.fragment.feed

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
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

        return binding.root
    }

    private fun initViewModel() {
        val factory = FeedFragmentViewModelFactory(
            RepositoryProvider.tweetRepository,
            RepositoryProvider.userRepository
        )
        viewModel = ViewModelProvider(this, factory)[FeedFragmentViewModel::class.java]
        binding.viewModel = viewModel
        viewModel.setUser()
        viewModel.fetchTweets()
    }

    private fun initRecyclerView() {
        feedAdapter = FeedAdapter()
        binding.rvTweetFeed.apply {
            adapter = feedAdapter
            layoutManager = LinearLayoutManager(this@FeedFragment.context)
        }
        displayTweetList()
    }

    private fun displayTweetList() {
        viewModel.tweets?.observe(this) { tweetList ->
            feedAdapter.setTweetList(tweetList)
            feedAdapter.notifyDataSetChanged()
        }
    }


}