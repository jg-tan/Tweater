package com.example.solotwitter.ui.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.solotwitter.R
import com.example.solotwitter.databinding.FragmentHomeBinding
import com.example.solotwitter.db.RepositoryProvider

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        val factory = HomeFragmentViewModelFactory(RepositoryProvider.userRepository)
        viewModel = ViewModelProvider(this, factory)[HomeFragmentViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        setObservables()
        return binding.root
    }

    private fun setObservables() {
        viewModel.eventHandler.observe(this) {
            it.getContentIfNotHandled()?.let { event ->
                when (event) {
                    HomeFragmentEvents.NAVIGATE_TO_FEED -> binding.root.findNavController()
                        .navigate(R.id.action_signupFragment_to_feedFragment)
                    HomeFragmentEvents.NAVIGATE_TO_SIGNUP -> binding.root.findNavController()
                        .navigate(R.id.action_homeFragment_to_signupFragment)
                }
            }
        }

        viewModel.messageHandler.observe(this) {
            it.getContentIfNotHandled()?.let { message ->
                Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            }
        }
    }
}