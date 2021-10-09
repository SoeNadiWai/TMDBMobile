package com.soenadiwai.tmdbmobile.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.soenadiwai.tmdbmobile.MainActivity
import com.soenadiwai.tmdbmobile.MovieApplication
import com.soenadiwai.tmdbmobile.adapter.RVAdapterForMovieList
import com.soenadiwai.tmdbmobile.databinding.FragmentMovielistBinding
import com.soenadiwai.tmdbmobile.model.Movie
import com.soenadiwai.tmdbmobile.model.PopularMovieHeader
import com.soenadiwai.tmdbmobile.model.UpComingMovieHeader
import com.soenadiwai.tmdbmobile.network.ServiceCallbackWrapper
import com.soenadiwai.tmdbmobile.util.ConnectivityUtil
import io.reactivex.disposables.CompositeDisposable

class MovieListFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModels {
        HomeViewModelFactory((context?.applicationContext as MovieApplication).popularmovielist_repository,
            (context?.applicationContext as MovieApplication).upcomingmovielist_repository)
    }
    private var _binding: FragmentMovielistBinding? = null
    private val binding get() = _binding!!
    private lateinit var movieListAdapter: RVAdapterForMovieList
    private val disposal: CompositeDisposable = CompositeDisposable()
    private var fragmentType: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovielistBinding.inflate(inflater, container, false)
        val root: View = binding.root
        getExtras()
        initRecyclerViews()
        getMovieLists()
        clickListeners()
        return root
    }

    private fun clickListeners(){
        movieListAdapter.setItemClickListener(object : RVAdapterForMovieList.OnItemClickListener {
            override fun onItemClick(view: View?, clickposition: Int) {
                val id = movieListAdapter.movieList[clickposition].id
                (activity as MainActivity).replaceFragment(MovieDetailFragment(), "FavoriteFragment",id)
            }
        })
    }

    private fun initRecyclerViews() {
            binding.popularMovieRV.apply {
                val layoutManager =
                    GridLayoutManager(this.context, 2)
                binding.popularMovieRV.layoutManager = layoutManager
                movieListAdapter = RVAdapterForMovieList()
                adapter = movieListAdapter
        }
    }

    private fun getMovieLists() {
        if(fragmentType == TYPE_POPULAR){
            if(ConnectivityUtil.isConnected(context)){
                homeViewModel.getPopularMovieList(object : ServiceCallbackWrapper(context) {
                    override fun onStart() {
                        binding.progressbar.isVisible = true
                    }

                    override fun <T> onSuccess(t: T) {
                        var movieHeader = t as PopularMovieHeader
                        homeViewModel.deletePopularMovieHeader()
                        homeViewModel.insertPopularMovieHeader(movieHeader)
                        loadMovieList(movieHeader.results)
                    }

                    override fun onCompleted() {
                        super.onCompleted()
                        binding.progressbar.isVisible = false
                    }

                })
            }else{
                var movieHeader: PopularMovieHeader = homeViewModel.getOfflinePopularMovieList()
                if(movieHeader == null){
                    Toast.makeText(context,"No Data",Toast.LENGTH_LONG).show()
                }else{
                    loadMovieList(movieHeader.results)
                }
            }
        }else{
            if(ConnectivityUtil.isConnected(context)) {
                homeViewModel.getUpcomingMovieList(object : ServiceCallbackWrapper(context) {
                    override fun onStart() {
                        binding.progressbar.isVisible = true
                    }
                    override fun <T> onSuccess(t: T) {
                        var movieHeader = t as UpComingMovieHeader
                        homeViewModel.deleteUpComingMovieHeader()
                        homeViewModel.insertUpcomingMovieHeader(movieHeader)
                        loadMovieList(movieHeader.results)
                    }

                    override fun onCompleted() {
                        super.onCompleted()
                        binding.progressbar.isVisible = false
                    }

                })
            }else{
                var movieHeader: UpComingMovieHeader = homeViewModel.getOfflineUpcomingMovieList()
                if(movieHeader == null){
                    Toast.makeText(context,"No Data",Toast.LENGTH_LONG).show()
                }else{
                    loadMovieList(movieHeader.results)
                }
                loadMovieList(movieHeader.results)
            }
        }
    }

    fun loadMovieList(movieList: List<Movie>){
        if (movieList != null) {
            movieListAdapter.movieList.clear()
            movieListAdapter.movieList = movieList as ArrayList<Movie>
            movieListAdapter.notifyDataSetChanged()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object{
        val TYPE_POPULAR = "popular_home"
        val TYPE_UPCOMING = "upcoming_home"
        val KEY_FRAGMENT_TYPE = "type"
    }

    fun getExtras(){
        val extras = arguments
        fragmentType =
            if (extras != null) extras.getString(KEY_FRAGMENT_TYPE) else TYPE_POPULAR
        if(fragmentType == TYPE_UPCOMING){
            binding.textTitleType.text = "Upcoming Movies"
        }else{
            binding.textTitleType.text = "Popular Movies"
        }
    }

}