package com.soenadiwai.tmdbmobile

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.snackbar.Snackbar
import com.soenadiwai.tmdbmobile.databinding.ActivityMainBinding
import com.soenadiwai.tmdbmobile.ui.HomeTabFragment
import com.soenadiwai.tmdbmobile.ui.MovieDetailFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var doubleBackToExitPressedOnce = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun onBackPressed() {
        this.supportActionBar?.setDisplayHomeAsUpEnabled(false);
        val currentFragment = supportFragmentManager.findFragmentById(R.id.container)
        if (currentFragment is MovieDetailFragment) {
            loadHomeFragment(HomeTabFragment(), "HomeTabFragment")
        } else {
            if (doubleBackToExitPressedOnce) {
                finishAffinity()
                System.exit(1)
            }
            this.doubleBackToExitPressedOnce = true
            val snackbar = Snackbar.make(
                this!!.currentFocus!!,
                "Press BACK again to exit",
                Snackbar.LENGTH_SHORT
            )
            snackbar.show()
            Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)

        }

    }

    fun loadHomeFragment(fragment: Fragment, tag: String) {
        supportFragmentManager.beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .replace(R.id.container, fragment, tag)
            .addToBackStack("null")
            .commit()
    }

    fun replaceFragment(fragment: Fragment, tag: String, movie_id: Int) {
        val extras = Bundle()
        extras.putInt("id", movie_id)
        fragment.arguments = extras
        supportFragmentManager.beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .replace(R.id.container, fragment, tag)
            .addToBackStack("HomeTabFragment")
            .commit()
    }
}