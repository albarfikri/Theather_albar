package com.albar.theater.movies

import android.os.Bundle
import android.view.*
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.albar.theater.R
import com.albar.theater.databinding.FragmentMoviesBinding
import com.albar.theater.main.MainActivity
import com.miguelcatalan.materialsearchview.MaterialSearchView
import kotlinx.android.synthetic.main.activity_main.*

class MoviesFragment : Fragment() {
    private lateinit var binding: FragmentMoviesBinding
    private lateinit var searchView: MaterialSearchView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMoviesBinding.inflate(inflater, container, false)
        setUpSearchView()
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        val menuItem = menu.findItem(R.id.item_search)
        searchView.setMenuItem(menuItem)
    }

    private fun setUpSearchView() {
        val toolbar: androidx.appcompat.widget.Toolbar = activity?.findViewById<View>(R.id.toolbar) as androidx.appcompat.widget.Toolbar
        (activity as AppCompatActivity?)?.setSupportActionBar(toolbar)
        setHasOptionsMenu(true)
        searchView = (activity as MainActivity).findViewById(R.id.search_view)
    }
}