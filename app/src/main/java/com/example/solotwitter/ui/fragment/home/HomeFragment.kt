package com.example.solotwitter.ui.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.solotwitter.R
import com.example.solotwitter.databinding.FragmentHomeBinding
import com.example.solotwitter.db.user.UserDatabase
import com.example.solotwitter.db.user.UserRepository

class HomeFragment : Fragment() {
    private lateinit var binding : FragmentHomeBinding
    private lateinit var viewModel : HomeFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        val dao = UserDatabase.getInstance(activity!!.applicationContext).userDAO
        val repository = UserRepository(dao)
        val factory = HomeFragmentViewModelFactory(repository)

        viewModel = ViewModelProvider(this, factory)[HomeFragmentViewModel::class.java]

        return binding.root
    }

}