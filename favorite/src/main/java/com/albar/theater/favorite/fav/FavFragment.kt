package com.albar.theater.favorite.fav

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.albar.theater.core.ui.MoviesAdapter
import com.albar.theater.detail.DetailActivity
import com.albar.theater.favorite.databinding.FragmentFavBinding
import com.albar.theater.favorite.di.favModule
import com.albar.theater.utils.Status
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class FavFragment : Fragment() {
    private var _binding: FragmentFavBinding? = null
    private val binding get() = _binding!!

    private val favViewModel: FavViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadKoinModules(favModule)
        setStatus(Status.LOADING)
        retrieveAllFavMovies()
        (activity as AppCompatActivity).supportActionBar?.title = "Favorite"
    }

    private fun retrieveAllFavMovies() {
        if (activity != null) {
            val movieAdapter = MoviesAdapter()
            movieAdapter.onItemClick = { selectedData ->
                val intent = Intent(activity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.extraData, selectedData)
                startActivity(intent)
            }

            favViewModel.favMovie.observe(viewLifecycleOwner) { favData ->
                if (favData.isNullOrEmpty()) {
                    setStatus(Status.ERROR)
                } else {
                    setStatus(Status.SUCCESS)
                }
                movieAdapter.setData(favData)
            }

            with(binding.rvMovies) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = movieAdapter
            }
        }
    }

    private fun setStatus(state: Status) {
        when (state) {
            Status.LOADING -> {
                binding.apply {
                    rvMovies.visibility = View.GONE
                    progressBar.visibility = View.VISIBLE
                    textNoData.visibility = View.GONE
                    animationView.visibility = View.GONE
                }
            }
            Status.SUCCESS -> {
                binding.apply {
                    rvMovies.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                    textNoData.visibility = View.GONE
                    animationView.visibility = View.GONE
                }
            }
            Status.ERROR -> {
                binding.apply {
                    rvMovies.visibility = View.GONE
                    progressBar.visibility = View.GONE
                    textNoData.visibility = View.VISIBLE
                    animationView.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}