package com.soenadiwai.tmdbmobile.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.soenadiwai.tmdbmobile.MainActivity
import com.soenadiwai.tmdbmobile.MovieApplication
import com.soenadiwai.tmdbmobile.R
import com.soenadiwai.tmdbmobile.databinding.FragmentDetailBinding
import com.soenadiwai.tmdbmobile.model.MovieDetail
import com.soenadiwai.tmdbmobile.network.ServiceCallbackWrapper
import com.soenadiwai.tmdbmobile.util.AppConstant
import com.soenadiwai.tmdbmobile.util.ConnectivityUtil

class MovieDetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    var movie_id: Int? = null
    private val movieDetailViewModel: MovieDetailViewModel by viewModels {
        MovieDetailViewFactory((context?.applicationContext as MovieApplication).moviedetail_repository)
    }
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val extras = arguments
        movie_id =
            extras?.getInt("id") ?: 0
        if(movieDetailViewModel.isFavorite(movie_id!!)){
            binding.addFavBtn.isChecked = true
        }
        loadMovieDetail(movie_id)
        clickListeners()
        return root
    }

    fun clickListeners(){

        binding.backImgBtn.setOnClickListener{
            (activity as MainActivity).onBackPressed()
        }

        binding.addFavBtn.setOnCheckedChangeListener { buttonView, isChecked ->
            movieDetailViewModel.updateFavorite(isChecked, movie_id!!)
            if(isChecked){
                Toast.makeText(context,"Added to Favorite",Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(context,"Removed from Favorite",Toast.LENGTH_SHORT).show()
            }

        }
    }

    fun loadMovieDetail(id: Int?){
        if(ConnectivityUtil.isConnected(context)){
            movieDetailViewModel.getMovieDetail(id,object : ServiceCallbackWrapper(context) {
                override fun onStart() {
                    binding.progressbar.isVisible = true
                }

                override fun <T> onSuccess(t: T) {
                   loadMovieDetail(t as MovieDetail)
                }

                override fun onCompleted() {
                    super.onCompleted()
                    binding.progressbar.isVisible = false
                }

            })
        }else{
            Toast.makeText(context,"No Internet Connection",Toast.LENGTH_LONG).show()

            var movieDetail = movieDetailViewModel.getOfflineMovieDetail(id!!)
            if(movieDetail == null){
                Toast.makeText(context,"No Data",Toast.LENGTH_LONG).show()
            }else{
                loadMovieDetail(movieDetail)
            }
        }
    }

    fun loadMovieDetail(movieDetail: MovieDetail){
        if (movieDetail != null) {
            binding.movieNameTxt.text = movieDetail.title
            for(genre in movieDetail.genres){
                val tv_dynamic = TextView(context)
                tv_dynamic.textSize = 13f
                tv_dynamic.setTextColor(resources.getColor(R.color.black))
                tv_dynamic.setPadding(8,8,8,8)

                val params: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                params.setMargins(15, 10, 10, 10)
                tv_dynamic.layoutParams = params

                tv_dynamic.setBackgroundResource(R.drawable.dashboard_drawable)
                tv_dynamic.text = genre.name
                binding.llGenreLayout.addView(tv_dynamic)
            }
            binding.movieOverviewBodyTxt.text = movieDetail.overview
            binding.movieReleaseDateTxt.text = movieDetail.release_date
            val imageUrl = AppConstant.baseImageURL + movieDetail.poster_path
            Glide.with(binding.movieImageView)
                .load(imageUrl)
                .into(binding.movieImageView)

            movieDetailViewModel.insert(movieDetail)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}