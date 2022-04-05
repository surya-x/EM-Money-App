package `in`.emmoney.app.homeActivity.presentation

import `in`.emmoney.app.R
import `in`.emmoney.app.databinding.ActivityHomeBinding
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_home)

        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_home) as ActivityHomeBinding

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_home_fragment) as NavHostFragment
        val navController = navHostFragment.findNavController()

        binding.bottomNavigation.setupWithNavController(navController)

//        setSupportActionBar(binding.topAppBar)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homePageFragment,
                R.id.profilePageFragment,
                R.id.dashboardFragment,
                R.id.myAccountFragment
            )
        )
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)

        navController.addOnDestinationChangedListener{_, destination, _ ->
//            if(destination.id == R.id.myAccountFragment) {
//                binding.bottomNavigation.visibility = View.GONE
//            }
//            else if(destination.id == R.id.homePageFragment) {
//                binding.bottomNavigation.visibility = View.VISIBLE
//            }
//            else if(destination.id == R.id.profilePageFragment) {
//                binding.bottomNavigation.visibility = View.VISIBLE
//            }
//            else if(destination.id == R.id.dashboardFragment) {
//                binding.bottomNavigation.visibility = View.VISIBLE
//            }

            binding.bottomNavigation.visibility = when (destination.id){
                R.id.myAccountFragment -> View.GONE
                else -> View.VISIBLE
            }
        }
    }


}