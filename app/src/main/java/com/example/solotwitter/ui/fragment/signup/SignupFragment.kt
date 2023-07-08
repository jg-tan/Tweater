package com.example.solotwitter.ui.fragment.signup

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
import com.example.solotwitter.databinding.FragmentSignupBinding
import com.example.solotwitter.db.RepositoryProvider

class SignupFragment : Fragment() {
    private lateinit var binding: FragmentSignupBinding
    private lateinit var viewModel: SignupFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_signup, container, false)

        val factory = SignupFragmentViewModelFactory(RepositoryProvider.userRepository)
        viewModel = ViewModelProvider(this, factory)[SignupFragmentViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        setObservables()
        return binding.root
    }

    private fun setObservables() {
        viewModel.eventHandler.observe(this) {
            it.getContentIfNotHandled()?.let { event ->
                when (event) {
                    SignupFragmentEvents.NAVIGATE_TO_FEED -> binding.root.findNavController()
                        .navigate(R.id.action_signupFragment_to_feedFragment)
                    SignupFragmentEvents.NAVIGATE_TO_HOME -> binding.root.findNavController()
                        .navigate(R.id.action_signupFragment_to_homeFragment)

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