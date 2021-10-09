package com.soenadiwai.tmdbmobile.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.soenadiwai.tmdbmobile.R
import com.soenadiwai.tmdbmobile.model.Movie
import com.soenadiwai.tmdbmobile.util.AppConstant

class RVAdapterForMovieList() :
    RecyclerView.Adapter<RVAdapterForMovieList.MovieViewHolder>() {
    var movieList = ArrayList<Movie>()
    private var mItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.upcoming_movie_item_view, parent, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movieList[position], mItemClickListener)
    }

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var movieNameTxt: TextView = itemView.findViewById(R.id.movieTitle_textView)
        var releasedDateTxt: TextView = itemView.findViewById(R.id.releasedDate_textView)
        var movieImageView: ImageView = itemView.findViewById(R.id.movie_imageView)
        var favoriteBtn: ToggleButton = itemView.findViewById(R.id.add_favBtn)

        fun bind(movie: Movie, mItemClickListener: OnItemClickListener?) {
            movieNameTxt.text = movie.title
            releasedDateTxt.text = movie.release_date
            val imageUrl = AppConstant.baseImageURL + movie.poster_path
            Glide.with(movieImageView)
                .load(imageUrl)
                .into(movieImageView)
            movieImageView.setOnClickListener { v ->
                if (mItemClickListener != null) mItemClickListener!!.onItemClick(v, position)
            }
        }

    }

    interface OnItemClickListener {
        fun onItemClick(view: View?, position: Int)
    }

    fun setItemClickListener(itemClickListener: OnItemClickListener) {
        this.mItemClickListener = itemClickListener
    }


}