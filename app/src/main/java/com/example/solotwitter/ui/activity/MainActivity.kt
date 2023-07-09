package com.example.solotwitter.ui.activity

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHost
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.solotwitter.AppSharedPref
import com.example.solotwitter.R
import com.example.solotwitter.databinding.ActivityMainBinding
import com.example.solotwitter.db.RepositoryProvider

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        initViewModel()
        setNavigationGraph()
    }

    private fun initViewModel() {
        val factory = MainActivityViewModelFactory(
            RepositoryProvider.userRepository
        )
        viewModel = ViewModelProvider(this, factory)[MainActivityViewModel::class.java]
        binding.viewModel = viewModel
        viewModel.setCurrentUser()
    }

    private fun setNavigationGraph() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        val navGraph = navController.navInflater.inflate((R.navigation.nav_graph))

        val startDest = if (AppSharedPref.getLoggedInUserId() == -1) {
            R.id.homeFragment
        } else {
            R.id.feedFragment
        }

        navGraph.setStartDestination(startDest)
        navController.graph = navGraph
    }
}