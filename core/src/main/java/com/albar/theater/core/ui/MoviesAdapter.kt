package com.albar.theater.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.albar.theater.core.BuildConfig
import com.albar.theater.core.databinding.ItemMovieListBinding
import com.albar.theater.core.domain.model.MovieModel
import com.bumptech.glide.Glide

class MoviesAdapter : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {
    private var listData = ArrayList<MovieModel>()
    var onItemClick: ((MovieModel) -> Unit)? = null

    fun setData(newListData: List<MovieModel>) {
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MoviesAdapter.MovieViewHolder {
        val itemMovieListBinding =
            ItemMovieListBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MovieViewHolder(itemMovieListBinding)
    }


    override fun onBindViewHolder(holder: MoviesAdapter.MovieViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int = listData.size

    inner class MovieViewHolder(private val binding: ItemMovieListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MovieModel) {
            binding.apply {
                Glide.with(itemView.context)
                    .load(BuildConfig.imageUrl + movie.posterPath)
                    .into(ivPosterPath)
                tvOriginalTitle.text = movie.originalTitle
                tvReleaseDate.text = movie.releaseDate
                tvVoteAverage.text = movie.voteAverage.toString()
                tvVoteAverageCircle.progress = movie.voteAverage?.toFloat()!!
            }
        }
        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listData[adapterPosition])
            }
        }
    }
}