package pe.com.redcups.juergapp_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import pe.com.redcups.juergapp_android.fragment.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null){
            val fragment = EventFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.container,fragment,fragment.javaClass.name)
                .commit()
        }

        bottom_navigation.setOnNavigationItemSelectedListener { menuItem ->
            lateinit var selectedFragment: Fragment
            when(menuItem.itemId){
                R.id.events -> selectedFragment = EventFragment()
                R.id.orders -> selectedFragment = OrderFragment()
                R.id.games -> selectedFragment = GameFragment()
                R.id.music -> selectedFragment = MusicFragment()
                R.id.profile -> selectedFragment = ProfileFragment()
            }
            supportFragmentManager.beginTransaction()
                .replace(R.id.container,selectedFragment,selectedFragment.javaClass.name)
                .commit()
            true
        }
    }
}
