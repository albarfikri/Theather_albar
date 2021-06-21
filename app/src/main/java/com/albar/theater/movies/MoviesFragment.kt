package com.albar.theater.movies

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.albar.theater.R
import com.albar.theater.core.data.source.Resource
import com.albar.theater.core.ui.MoviesAdapter
import com.albar.theater.databinding.FragmentMoviesBinding
import com.albar.theater.detail.DetailActivity
import com.albar.theater.main.MainActivity
import com.albar.theater.search.SearchViewModel
import com.albar.theater.utils.Status
import com.google.android.material.snackbar.Snackbar
import com.miguelcatalan.materialsearchview.MaterialSearchView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.android.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
@FlowPreview
class MoviesFragment : Fragment() {
    private var _binding: FragmentMoviesBinding? = null
    private lateinit var searchView: MaterialSearchView
    private val binding get() = _binding!!
    private lateinit var movieAdapter: MoviesAdapter
    private val movieViewModel: MoviesViewModel by viewModel()
    private val searchViewModel: SearchViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviesBinding.inflate(inflater, container, false)
        setUpSearchView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Movies"
        movieAdapter = MoviesAdapter()
        retrieveAllMovies()
        searchQuery()
        setSearchQueryList()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        val menuItem = menu.findItem(R.id.item_search)
        searchView.setMenuItem(menuItem)
    }

    private fun searchQuery() {
        searchView.setOnQueryTextListener(object : MaterialSearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(keywords: String?): Boolean {
                keywords?.let { searchViewModel.setQuery(it) }
                if (keywords != null) {
                    if (keywords.isNotEmpty()) {
                        Toast.makeText(
                            context,
                            keywords,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                setStatus(Status.LOADING)
                return true
            }
        })
    }

    private fun setSearchQueryList() {
        searchViewModel.getSearchResult.observe(viewLifecycleOwner) { searchData ->
            Log.d("Testing", searchData.toString())
            if (searchData.isNullOrEmpty()) {
                setStatus(Status.ERROR)
            } else {
                setStatus(Status.SUCCESS)
            }
            movieAdapter.setData(searchData)
        }

        searchView.setOnSearchViewListener(object : MaterialSearchView.SearchViewListener {
            override fun onSearchViewShown() {
                setStatus(Status.SUCCESS)
            }

            override fun onSearchViewClosed() {
                setStatus(Status.SUCCESS)
                retrieveAllMovies()
            }
        })
    }

    private fun retrieveAllMovies() {
        if (activity != null) {
            movieAdapter.onItemClick = { selectedData ->
                val intent = Intent(activity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.extraData, selectedData)
                startActivity(intent)
            }

            movieViewModel.movie.observe(viewLifecycleOwner) { movieData ->
                if (movieData != null) {
                    when (movieData) {
                        is Resource.Loading -> setStatus(Status.LOADING)
                        is Resource.Success -> {
                            movieData.data?.let { movieAdapter.setData(it) }
                            setStatus(Status.SUCCESS)
                        }
                        is Resource.Error -> {
                            setStatus(Status.ERROR)
                            val view: View = requireActivity().findViewById(android.R.id.content)
                            Snackbar.make(
                                view,
                                "Error, Check your connection !",
                                Snackbar.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }

            with(binding.rvMovies) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = movieAdapter
            }
        }
    }

    private fun setUpSearchView() {
        val toolbar: androidx.appcompat.widget.Toolbar =
            activity?.findViewById<View>(R.id.toolbar) as androidx.appcompat.widget.Toolbar
        (activity as AppCompatActivity?)?.setSupportActionBar(toolbar)
        setHasOptionsMenu(true)
        searchView = (activity as MainActivity).findViewById(R.id.search_view)
    }

    private fun setStatus(state: Status) {
        when (state) {
            Status.LOADING -> {
                binding.apply {
                    rvMovies.visibility = View.GONE
                    progressBar.visibility = View.VISIBLE
                    movieNotFound.noFav.visibility = View.GONE
                }
            }
            Status.SUCCESS -> {
                binding.apply {
                    rvMovies.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                    movieNotFound.noFav.visibility = View.GONE
                }
            }
            Status.ERROR -> {
                binding.apply {
                    rvMovies.visibility = View.GONE
                    progressBar.visibility = View.GONE
                    movieNotFound.noFav.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        searchView.setOnQueryTextListener(null)
        searchView.setOnSearchViewListener(null)
        binding.rvMovies.adapter = null
        _binding = null
    }
}