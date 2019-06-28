package pe.com.redcups.juergapp_android

import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.ConnectivityManager.CONNECTIVITY_ACTION
import android.net.Network
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import kotlinx.android.synthetic.main.activity_main.*
import pe.com.redcups.core.network.Connection

class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration : AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        val host: NavHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment? ?: return
        val navController = host.navController

        appBarConfiguration = AppBarConfiguration(
            //list all top level destination (bottom bar butotns)
            setOf(R.id.product_category_dest, R.id.events_dest, R.id.games_dest, R.id.profile_dest, R.id.music_dest),
            drawer_layout)

        // Set up Action Bar
        setupActionBar(navController, appBarConfiguration)
        setupNavigationMenu(navController)
        setupBottomNavMenu(navController)

        // check if user has internet access show no internet access

        val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager?.let {
            it.registerDefaultNetworkCallback(object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    //take action when network connection is gained
                    runOnUiThread {
                        if(Connection.hasInternetAccess(this@MainActivity)){
                            no_internet_alert.visibility =  View.GONE
                        }
                    }
                }
                override fun onLost(network: Network?) {
                    //take action when network connection is lost
                    runOnUiThread {
                        if(!Connection.hasInternetAccess(this@MainActivity)){
                            no_internet_alert.visibility =  View.VISIBLE
                        }
                    }
                }
            })
        }



    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        //setupActionBar(null);
    }

    private fun setupBottomNavMenu(navController: NavController) {
        bottom_navigation?.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        // Allows NavigationUI to support proper up navigation or the drawer layout
        // drawer menu, depending on the situation
        return findNavController(R.id.nav_host_fragment).navigateUp(appBarConfiguration)
    }
    private fun setupNavigationMenu(navController: NavController) {
        nav_view?.setupWithNavController(navController)
    }
    private fun setupActionBar(navController: NavController,
                               appBarConfig : AppBarConfiguration) {

        // This allows NavigationUI to destinationcide what label to show in the action bar
        // By using appBarConfig, it will also determine whether to
        // show the up arrow or drawer menu icon
        setupActionBarWithNavController(navController, appBarConfig)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.cart_menu,menu)
        var drawable = menu!!.findItem(R.id.cart_dest).icon
        drawable = DrawableCompat.wrap(drawable)
        DrawableCompat.setTint(drawable, ContextCompat.getColor(this, R.color.white))
        menu!!.findItem(R.id.cart_dest).icon = drawable
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(findNavController(R.id.nav_host_fragment))
                || super.onOptionsItemSelected(item)
    }

}
