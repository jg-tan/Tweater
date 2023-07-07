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
import com.example.solotwitter.db.tweet.TweetDatabase
import com.example.solotwitter.db.tweet.TweetRepository

class FeedFragment : Fragment() {
    private lateinit var binding: FragmentFeedBinding
    private lateinit var viewModel: FeedFragmentViewModel
    private lateinit var feedAdapter: FeedAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i("MyTag", "feed fragment")
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_feed, container, false)

        val dao = TweetDatabase.getInstance(activity!!.applicationContext).tweetDAO
        val repository = TweetRepository(dao)
        val factory = FeedFragmentViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[FeedFragmentViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        initRecyclerView()
        return binding.root
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
        viewModel.tweets.observe(this) {
            Log.i("MyTag", it.toString())
            feedAdapter.setTweetList(it)
            feedAdapter.notifyDataSetChanged()
        }
    }


}