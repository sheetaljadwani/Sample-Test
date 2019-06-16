package com.salesforce.remotetest.searchmovies

import android.app.Application
import android.content.Context
import android.content.res.Resources
import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.google.common.collect.Lists
import com.salesforce.remotetest.R
import com.salesforce.remotetest.base.BaseViewModel
import com.salesforce.remotetest.data.Movie
import com.salesforce.remotetest.data.source.MoviesDataSource
import com.salesforce.remotetest.data.source.MoviesRepository
import com.salesforce.remotetest.searchmoviestask.SearchMoviesViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.*
import java.util.ArrayList
import javax.inject.Inject

class SearchMoviesViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()
    @Mock
    private lateinit var moviesRepository: MoviesRepository
    @Mock
    private lateinit var context: Application
    @Captor
    private lateinit var loadMoviesCallbackCaptor: ArgumentCaptor<MoviesDataSource.LoadMoviesCallback>
    private lateinit var searchMoviesViewModel: SearchMoviesViewModel
    private lateinit var movies: List<Movie>

    @Before
    fun setupTasksViewModel() {
        MockitoAnnotations.initMocks(this)
        setupContext()
        searchMoviesViewModel = SearchMoviesViewModel(moviesRepository)
        val movie1 = Movie(title = "Title1", director = "director1")
        val movie2 = Movie(title = "Title2", director = "director2").apply {
            favorite = 1
        }
        val task3 = Movie(title = "Title3", director = "director3").apply {
            favorite = 1
        }
        movies = Lists.newArrayList(movie1, movie2, task3)
    }

    private fun setupContext() {
        Mockito.`when`<Context>(context.applicationContext).thenReturn(context)
        Mockito.`when`(context.resources).thenReturn(Mockito.mock(Resources::class.java))
    }

//    @Test
//    fun getMoviesTest() {
//        searchMoviesViewModel.getMovies("Title1", true)
//    }


}