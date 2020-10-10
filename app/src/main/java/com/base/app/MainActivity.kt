package com.base.app

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import com.base.app.enums.RetrofitStatus
import com.base.app.shared.Constants
import com.base.app.shared.Constants.LOGGED_IN
import com.base.app.utils.hide
import com.base.app.utils.show
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>
    override fun supportFragmentInjector() = dispatchingAndroidInjector

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private var isLoggedIn: Boolean? = null
    private lateinit var sharedPref: SharedPreferences
    private lateinit var pendingTransitions: () -> Unit
    private val navigationListener =
        NavController.OnDestinationChangedListener { controller, destination, arguments ->
            pendingTransitions = when (destination.id) {
                R.id.navigation_first_screen,
                R.id.navigation_second_screen,
                R.id.navigation_third_screen,
                R.id.navigation_fourth_screen-> {
                    {
                        bottom_nav_view.show()
                    }
                }
                else -> {
                    {}
                }
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        sharedPref = getSharedPreferences(Constants.USER_CREDENTIALS, Context.MODE_PRIVATE)
        if (sharedPref.getBoolean(LOGGED_IN, false))
            setTheme(R.style.DashboardTheme)
        else
            setTheme(R.style.MainTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController = findNavController(R.id.main_nav_host)
        setupViewWithLoginStatus()
        appBarConfiguration = AppBarConfiguration(navController.graph)
        navController.addOnDestinationChangedListener(navigationListener)
        bottom_nav_view.setupWithNavController(navController)
        bottom_nav_view.setOnNavigationItemSelectedListener { item ->
            NavigationUI.onNavDestinationSelected(item, navController)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun executePendingTransitions() {
        pendingTransitions.invoke()
    }

    private fun setupViewWithLoginStatus() {
        isLoggedIn = sharedPref
            .getBoolean(LOGGED_IN, false)
        var navGraph = R.navigation.dashboard_nav_graph
        if (isLoggedIn == true) {
            bottom_nav_view.inflateMenu(R.menu.dashboard_menu)
        } else {
            bottom_nav_view.hide()
            navGraph = R.navigation.login_nav_graph
        }
        navController.setGraph(navGraph)
        setupActionBarWithNavController(navController)
        supportActionBar?.apply {
            setDisplayShowTitleEnabled(false)
            setDisplayShowHomeEnabled(true)
            setDisplayUseLogoEnabled(true)
            setLogo(
                ContextCompat.getDrawable(
                    this@MainActivity,
                    R.drawable.ic_launcher_background
                )
            )
        }
    }

    fun showOrHideLoader(status: RetrofitStatus) {
        if (status == RetrofitStatus.IN_PROGRESS) {
            touchBlocker.show()
            progressbar.show()
        } else {
            touchBlocker.hide()
            progressbar.hide()
        }
    }
}
