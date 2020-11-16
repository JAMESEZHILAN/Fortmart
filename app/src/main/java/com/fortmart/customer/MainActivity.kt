package com.fortmart.customer

import android.content.Context
import android.content.SharedPreferences
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import com.fortmart.customer.enums.RetrofitStatus
import com.fortmart.customer.shared.Constants
import com.fortmart.customer.shared.Constants.LOGGED_IN
import com.fortmart.customer.utils.hide
import com.fortmart.customer.utils.show
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
            when (destination.id) {
                R.id.navigation_language,
                R.id.navigation_login -> {
                    supportActionBar?.apply {
                        show()
                        setHomeAsUpIndicator(
                            ContextCompat.getDrawable(
                                this@MainActivity,
                                R.drawable.ic_back
                            )
                        )
                    }
                    bottom_nav_view.hide()
                }
                R.id.navigation_category_list,
                R.id.navigation_orders -> {
                    supportActionBar?.setBackgroundDrawable(
                        ContextCompat.getDrawable(
                            this,
                            R.drawable.bg_dashboard_actionbar
                        )
                    )
                    bottom_nav_view.show()
                }
                R.id.navigation_third_screen -> {
                    supportActionBar?.setBackgroundDrawable(
                        ColorDrawable(
                            ContextCompat.getColor(
                                this,
                                R.color.colorWhite
                            )
                        )
                    )
                    bottom_nav_view.show()
                }

                R.id.navigation_product_list -> {
                    bottom_nav_view.hide()
                    supportActionBar?.apply {
                        setDisplayShowTitleEnabled(false)
                        setDisplayShowCustomEnabled(false)
                        setDisplayHomeAsUpEnabled(true)
                        setHomeAsUpIndicator(
                            ContextCompat.getDrawable(
                                this@MainActivity,
                                R.drawable.ic_back
                            )
                        )
                        setDisplayShowHomeEnabled(true)
                        setDisplayUseLogoEnabled(true)
                        setLogo(
                            ContextCompat.getDrawable(
                                this@MainActivity,
                                R.drawable.ic_fortmart
                            )
                        )
                    }
                }
            }
            pendingTransitions = when (destination.id) {
                R.id.navigation_category_list -> {
                    {
                        supportActionBar?.apply {
                            setDisplayShowTitleEnabled(true)
                            setDisplayUseLogoEnabled(false)
                            setDisplayShowCustomEnabled(true)
                            displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
                            setCustomView(R.layout.actionbar_address)
                            val title = findViewById<TextView>(
                                resources.getIdentifier(
                                    "action_bar_title",
                                    "id",
                                    packageName
                                )
                            )
                            val subtitle = findViewById<TextView>(
                                resources.getIdentifier(
                                    "action_bar_subtitle",
                                    "id",
                                    packageName
                                )
                            )
                            title.text = "Velachery - 600098"
                            subtitle.text = "678, Gangai amman koil street"
                            setDisplayHomeAsUpEnabled(false)
                        }
                    }
                }
                R.id.navigation_orders -> {
                    {
                        supportActionBar?.apply {
                            setDisplayShowCustomEnabled(false)
                            setDisplayShowTitleEnabled(false)
                            setDisplayShowHomeEnabled(true)
                            setDisplayUseLogoEnabled(true)
                            setDisplayHomeAsUpEnabled(false)
                            setLogo(
                                ContextCompat.getDrawable(
                                    this@MainActivity,
                                    R.drawable.ic_fortmart
                                )
                            )
                        }
                    }
                }
                R.id.navigation_third_screen -> {
                    {
                        supportActionBar?.apply {
                            setDisplayShowCustomEnabled(false)
                            setDisplayHomeAsUpEnabled(false)
                            setDisplayShowTitleEnabled(false)
                            setDisplayUseLogoEnabled(false)
                        }
                    }
                }
                R.id.navigation_store_list -> {
                    {
                        supportActionBar?.apply {
                            setBackgroundDrawable(
                                ColorDrawable(
                                    ContextCompat.getColor(
                                        this@MainActivity,
                                        R.color.colorBlueBackground
                                    )
                                )
                            )
                            setDisplayShowTitleEnabled(true)
                            setDisplayUseLogoEnabled(false)
                            setDisplayShowCustomEnabled(true)
                            displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
                            setCustomView(R.layout.actionbar_title)
                            val title = findViewById<TextView>(
                                resources.getIdentifier(
                                    "action_bar_title",
                                    "id",
                                    packageName
                                )
                            )
                            title.text = controller.currentDestination?.label
                            setDisplayHomeAsUpEnabled(false)
                        }
                    }
                }
                R.id.navigation_product_list -> {
                    {
                        supportActionBar?.apply {
                            setDisplayShowTitleEnabled(false)
                            setDisplayShowCustomEnabled(false)
                            setDisplayHomeAsUpEnabled(true)
                            setHomeAsUpIndicator(
                                ContextCompat.getDrawable(
                                    this@MainActivity,
                                    R.drawable.ic_back
                                )
                            )
                            setDisplayShowHomeEnabled(true)
                            setDisplayUseLogoEnabled(true)
                            setLogo(
                                ContextCompat.getDrawable(
                                    this@MainActivity,
                                    R.drawable.ic_fortmart
                                )
                            )
                        }
                    }
                }
                else -> {
                    {}
                }
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        sharedPref = getSharedPreferences(Constants.USER_DETAILS, Context.MODE_PRIVATE)
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
        if (isLoggedIn == false) {
            supportActionBar?.apply {
                setDisplayShowTitleEnabled(false)
//                setDisplayShowHomeEnabled(true)
//                setDisplayUseLogoEnabled(true)
//                setLogo(
//                    ContextCompat.getDrawable(
//                        this@MainActivity,
//                        R.drawable.ic_fortmart
//                    )
//                )
            }
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
