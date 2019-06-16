package com.salesforce.remotetest.searchmoviestask

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.salesforce.remotetest.MainActivity
import com.salesforce.remotetest.R
import com.salesforce.remotetest.data.Movie
import com.salesforce.remotetest.databinding.SearchMoviesFragmentBinding
import com.salesforce.remotetest.favorites.FavoriteMoviesViewModel
import com.salesforce.remotetest.obtainViewModel

class SearchMoviesFragment : Fragment(){

    private lateinit var viewDataBinding: SearchMoviesFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        viewDataBinding = SearchMoviesFragmentBinding.inflate(inflater, container, false).apply {
            viewmodel = (activity as MainActivity).obtainViewModel(SearchMoviesViewModel::class.java)
        }
        setHasOptionsMenu(true)
        return viewDataBinding.root
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
            viewDataBinding.moviesList.adapter = SearchMoviesAdapter(activity!!,listOf(), viewModel)
            var layoutManager = LinearLayoutManager(context).apply {
                orientation = LinearLayoutManager.VERTICAL
            }
            viewDataBinding.moviesList.layoutManager = layoutManager
            viewDataBinding.moviesList.hasFixedSize()
            viewDataBinding.moviesList.addItemDecoration(DividerItemDecoration(context, layoutManager.orientation))
        } else {
            Log.w(TAG, "ViewModel not initialized when attempting to set up adapter.")
        }
    }

    private fun setupRefreshLayout() {
        viewDataBinding.refreshLayout.run {
        }
    }


    companion object{
        fun newInstance() = SearchMoviesFragment()
        private const val TAG = "SearchMoviesFragment"
    }

}