package com.albar.theater.movies

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.albar.theater.R
import com.albar.theater.core.data.source.Resource
import com.albar.theater.core.ui.MoviesAdapter
import com.albar.theater.databinding.FragmentMoviesBinding
import com.albar.theater.detail.DetailActivity
import com.albar.theater.main.MainActivity
import com.albar.theater.utils.Status
import com.google.android.material.snackbar.Snackbar
import com.miguelcatalan.materialsearchview.MaterialSearchView
import org.koin.android.viewmodel.ext.android.viewModel

class MoviesFragment : Fragment() {
    private var _binding: FragmentMoviesBinding? = null
    private lateinit var searchView: MaterialSearchView
    private val binding get() = _binding!!
    private val movieViewModel: MoviesViewModel by viewModel()

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
        retrieveAllMovies()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        val menuItem = menu.findItem(R.id.item_search)
        searchView.setMenuItem(menuItem)
    }

    private fun retrieveAllMovies() {
        if (activity != null) {
            val movieAdapter = MoviesAdapter()
            movieAdapter.onItemClick = { selectedData ->
                val intent = Intent(activity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.extraData, selectedData)
                startActivity(intent)
            }

            movieViewModel.movie.observe(viewLifecycleOwner, { movieData ->
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
                                "Something went wrong !",
                                Snackbar.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            })

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

//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
}