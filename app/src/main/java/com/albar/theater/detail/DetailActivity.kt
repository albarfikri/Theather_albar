package com.albar.theater.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import androidx.core.content.ContextCompat
import com.albar.theater.BuildConfig
import com.albar.theater.R
import com.albar.theater.core.domain.model.MovieModel
import com.albar.theater.databinding.ActivityDetailBinding
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_detail.*
import org.koin.android.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {
    companion object {
        const val extraData = "extra_data"
    }

    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val detailMovie = intent.getParcelableExtra<MovieModel>(extraData)
        if (detailMovie != null) {
            showDetailMovie(detailMovie)
        }

        collapsingToolbar()
        setupBackButton()


        binding.share.setOnClickListener {
            if (detailMovie != null) {
                shareContent(detailMovie)
            }
        }

        binding.play.setOnClickListener {
            Snackbar.make(
                window.decorView.rootView,
                "Coming soon !",
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    private fun setupBackButton() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    private fun showDetailMovie(detailMovie: MovieModel) {
        with(binding) {
            Glide.with(this@DetailActivity)
                .load(BuildConfig.IMAGE_URL + detailMovie.backdropPath)
                .into(tvBackdrop)
            Glide.with(this@DetailActivity)
                .load(BuildConfig.IMAGE_URL + detailMovie.posterPath)
                .into(iv_posterPath)
            tvOriginalLanguage.text = detailMovie.originalLanguage
            tvOriginalTitle.text = detailMovie.originalTitle
            tvVoteAverage.text = detailMovie.voteAverage.toString()
            tvReleaseDate.text = detailMovie.releaseDate
            tvOverview.text = detailMovie.overview
            tvRating.text = getString(
                R.string.ratingContent,
                detailMovie.voteAverage.toString(),
                detailMovie.voteCount.toString(),
                detailMovie.popularity.toString()
            )

            var favState = detailMovie.isFavorite
            setFavState(favState)
            btnFavorite.setOnClickListener {
                favState = !favState
                viewModel.setFavMovie(detailMovie, favState)
                setFavState(favState)
            }
        }
    }

    private fun shareContent(detailMovie: MovieModel) {
        val movieName: String = detailMovie.originalTitle
        val urlImageMovie: String = BuildConfig.IMAGE_URL + detailMovie.posterPath
        val voteAverage: String = detailMovie.voteAverage.toString()
        val mimeType = "text/plain"
        ShareCompat.IntentBuilder(this).apply {
            setType(mimeType)
            setChooserTitle("Theater Movies Favorite")
            setText("Movie Name : $movieName\nVote Average : $voteAverage\nMovie Banner : $urlImageMovie")
            startChooser()
        }
    }

    private fun collapsingToolbar() {
        binding.apply {
            collapsingToolbar.title = getString(R.string.detail)
            collapsingToolbar.setExpandedTitleTextColor(getColorStateList(R.color.white))
            collapsingToolbar.setCollapsedTitleTextColor(getColor(R.color.white))
            collapsingToolbar.setExpandedTitleColor(getColor(R.color.white))
            collapsingToolbar.setContentScrimColor(getColor(R.color.grey_500))
        }
    }

    private fun setFavState(state: Boolean) {
        if (state) {
            binding.btnFavorite.setImageDrawable(
                ContextCompat.getDrawable(this, R.drawable.ic_favorite)
            )
        } else {
            binding.btnFavorite.setImageDrawable(
                ContextCompat.getDrawable(this, R.drawable.ic_unfavorite)
            )
        }
    }
}