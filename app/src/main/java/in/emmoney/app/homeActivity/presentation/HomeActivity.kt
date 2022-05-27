package `in`.emmoney.app.homeActivity.presentation

import `in`.emmoney.app.R
import `in`.emmoney.app.databinding.ActivityHomeBinding
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.ktx.Firebase


class HomeActivity : AppCompatActivity() {

    private val TAG = "HomeActivity"

    private lateinit var binding: ActivityHomeBinding

//    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_home)

        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_home) as ActivityHomeBinding

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_home_fragment) as NavHostFragment
        val navController = navHostFragment.findNavController()

        binding.bottomNavigation.setupWithNavController(navController)

//        setSupportActionBar(binding.topAppBar)

//        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homePageFragment,
                R.id.profilePageFragment,
                R.id.dashboardFragment,
                R.id.myAccountFragment,
                R.id.completeKycFragment,
                R.id.schemeFragment
            )
        )

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)

        navController.addOnDestinationChangedListener{_, destination, _ ->
            binding.bottomNavigation.visibility = when (destination.id){
                R.id.myAccountFragment -> View.GONE
                R.id.completeKycFragment -> View.GONE
                R.id.schemeFragment -> View.GONE
                else -> View.VISIBLE
            }
        }

        // Obtain the FirebaseAnalytics instance.
//        firebaseAnalytics = Firebase.analytics
//        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_ITEM) {
//            param(FirebaseAnalytics.Param.ITEM_ID, 1234)
//            param(FirebaseAnalytics.Param.ITEM_NAME, "SURYA")
//        }
//        testCrashlytics()
    }

//    private fun testCrashlytics() {
//        // Creates a button that mimics a crash when pressed
//        val crashButton = Button(this)
//        crashButton.text = "Test Crash"
//        crashButton.setOnClickListener {
//            throw RuntimeException("Test Crash") // Force a crash
//        }
//
//        addContentView(crashButton, ViewGroup.LayoutParams(
//            ViewGroup.LayoutParams.MATCH_PARENT,
//            ViewGroup.LayoutParams.WRAP_CONTENT))
//    }



//    override fun onBackPressed() {
//        val manager: FragmentManager = supportFragmentManager
//        if (manager.backStackEntryCount > 0) {
//            manager.popBackStack()
//        } else {
//            // if there is only one entry in the backstack, show the home screen
//            moveTaskToBack(true)
//        }
//    }

}