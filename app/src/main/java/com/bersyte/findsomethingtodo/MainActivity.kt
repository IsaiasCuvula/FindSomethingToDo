package com.bersyte.findsomethingtodo

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bersyte.findsomethingtodo.databinding.ActivityMainBinding
import com.bersyte.findsomethingtodo.db.FavActDatabase
import com.bersyte.findsomethingtodo.repository.FavActivityRepository
import com.bersyte.findsomethingtodo.viewmodel.FavoriteActivityViewModel
import com.bersyte.findsomethingtodo.viewmodel.FavoriteActivityViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    lateinit var viewModel: FavoriteActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewModel()
        setUpNavController()

    }

    private fun setupViewModel() {
        val repository = FavActivityRepository(
            FavActDatabase.getDatabase(this)
        )

        val viewModelFactory = FavoriteActivityViewModelFactory(
            repository
        )

        viewModel = ViewModelProvider(
            this, viewModelFactory
        ).get(FavoriteActivityViewModel::class.java)
    }


    private fun setUpNavController() {

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentHost) as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomNav.setupWithNavController(
            navController
        )

        val appBarConfig = AppBarConfiguration(
            setOf(R.id.homeFragment)
        )
        setupActionBarWithNavController(navController, appBarConfig)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, null)
    }

}