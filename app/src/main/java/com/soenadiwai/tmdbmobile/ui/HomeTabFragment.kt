package com.soenadiwai.tmdbmobile.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.tabs.TabLayout
import com.soenadiwai.tmdbmobile.R
import com.soenadiwai.tmdbmobile.databinding.FragmentHomeTabBinding
import com.soenadiwai.tmdbmobile.util.TabUtil

class HomeTabFragment : Fragment() {

    private var _binding: FragmentHomeTabBinding? = null
    private val binding get() = _binding!!
    private var tabLayout: TabLayout? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeTabBinding.inflate(inflater, container, false)
        val root: View = binding.root
        tabLayout = binding.tablayout
        setupTabLayout()
        setCurrentTabFragment(0)
        return root
    }

    private fun setupTabLayout() {
        val popular = "Popular"
        val upcoming = "Upcoming"
        tabLayout?.addTab(
            tabLayout!!.newTab()
                .setCustomView(TabUtil.renderTabView(context, popular))
        )
        tabLayout?.addTab(
            tabLayout!!.newTab()
                .setCustomView(TabUtil.renderTabView(context, upcoming))
        )

        tabLayout?.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                setCurrentTabFragment(tab!!.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun setCurrentTabFragment(tabPostion: Int) {
        when (tabPostion) {
            0 -> replaceFragmentInActivity(MovieListFragment.TYPE_POPULAR)
            1 -> replaceFragmentInActivity(MovieListFragment.TYPE_UPCOMING)
        }
    }

    fun replaceFragmentInActivity(fragmentType: String?) {
        val extras = Bundle()
        extras.putString(MovieListFragment.KEY_FRAGMENT_TYPE, fragmentType)
        val fragment: Fragment = MovieListFragment()
        fragment.arguments = extras
        childFragmentManager
            .beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .replace(R.id.tab_frame_replace, fragment,"HomeTabFragment")
            .commit()
    }
}