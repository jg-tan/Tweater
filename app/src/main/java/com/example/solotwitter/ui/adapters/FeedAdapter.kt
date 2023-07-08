package com.example.solotwitter.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.solotwitter.R
import com.example.solotwitter.databinding.ListTweetBinding
import com.example.solotwitter.db.tweet.Tweet
import java.text.SimpleDateFormat
import java.util.*

class FeedAdapter : RecyclerView.Adapter<FeedViewHolder>() {
    private var tweetList: List<Tweet> = ArrayList<Tweet>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ListTweetBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.list_tweet, parent, false)
        return FeedViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        holder.bind(tweetList[position])
    }

    override fun getItemCount(): Int {
        return tweetList.size
    }

    fun setTweetList(tweets: List<Tweet>) {
        tweetList = tweets
    }
}

class FeedViewHolder(val binding: ListTweetBinding) : ViewHolder(binding.root) {
    fun bind(tweet: Tweet) {
        binding.apply {
            val sdf = SimpleDateFormat("dd MMMM yyyy, HH:mm:ss", Locale.ENGLISH)
            tvUserName.text = tweet.user_username + " @" + tweet.user_handle
            tvTimestamp.text = sdf.format(tweet.timestamp)
            tvTweetContent.text = tweet.content
        }
    }
}