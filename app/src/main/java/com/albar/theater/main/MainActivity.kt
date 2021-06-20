package com.albar.theater.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.albar.theater.R
import com.albar.theater.databinding.ActivityMainBinding
import com.albar.theater.movies.MoviesFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@ExperimentalCoroutinesApi
@FlowPreview
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onNavigationChange(MoviesFragment())
        onNavigationSelected()
    }

    private fun onNavigationSelected() {
        binding.bottomNavContainer.setNavigationChangeListener { _, position ->
            when (position) {
                0 -> onNavigationChange(MoviesFragment())
                1 -> favoriteFragment()
            }
        }
    }

    private fun favoriteFragment() {
        val favFragment = fragmentBuilder()
        if (favFragment != null) {
            onNavigationChange(favFragment)
        }
    }

    private fun fragmentBuilder(): Fragment? {
        return try {
            Class.forName("com.albar.theater.favorite.fav.FavFragment").newInstance() as Fragment
        } catch (e: Exception) {
            Snackbar.make(
                window.decorView.rootView,
                "Module is not found",
                Snackbar.LENGTH_SHORT
            ).show()
            null
        }
    }

    private fun onNavigationChange(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frameContainer, fragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
    }
}