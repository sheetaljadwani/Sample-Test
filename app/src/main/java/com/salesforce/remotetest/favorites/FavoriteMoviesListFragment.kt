package com.salesforce.remotetest.favorites

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.salesforce.remotetest.MainActivity
import com.salesforce.remotetest.databinding.FavoriteMoviesFragmentBinding
import com.salesforce.remotetest.obtainViewModel

class FavoriteMoviesListFragment : Fragment() {

    private lateinit var viewDataBinding: FavoriteMoviesFragmentBinding
    private lateinit var favoriteListAdapter: FavoriteMoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = FavoriteMoviesFragmentBinding.inflate(inflater, container, false).apply {
            viewmodel = (activity as MainActivity).obtainViewModel(FavoriteMoviesViewModel::class.java)
        }
        setHasOptionsMenu(true)
        return viewDataBinding.root
    }

    override fun onResume() {
        super.onResume()
        viewDataBinding.viewmodel?.getFavoriteMovies(true)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if(isVisibleToUser) {
            viewDataBinding.viewmodel?.getFavoriteMovies(true)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewDataBinding.setLifecycleOwner(this.viewLifecycleOwner)
        setupListAdapter()
        setupRefreshLayout()
    }

    @SuppressLint("WrongConstant")
    private fun setupListAdapter() {
        val viewModel = viewDataBinding.viewmodel
        if (viewModel != null) {
            viewDataBinding.favMoviesList.adapter = FavoriteMoviesAdapter(activity!!,listOf(), viewModel)
            var layoutManager = LinearLayoutManager(context).apply {
                orientation = LinearLayoutManager.VERTICAL
            }
            viewDataBinding.favMoviesList.layoutManager = layoutManager
            viewDataBinding.favMoviesList.hasFixedSize()
            viewDataBinding.favMoviesList.addItemDecoration(DividerItemDecoration(context, layoutManager.orientation))
        } else {
            Log.w(TAG, "ViewModel not initialized when attempting to set up adapter.")
        }
    }

    private fun setupRefreshLayout() {
        viewDataBinding.refreshLayout.run {
        }
    }


    companion object {
        fun newInstance() = FavoriteMoviesListFragment()
        private const val TAG = "FavoriteMoviesFragment"
    }
}