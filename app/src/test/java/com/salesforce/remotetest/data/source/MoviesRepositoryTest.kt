package com.salesforce.remotetest.data.source

import com.google.common.collect.Lists
import com.salesforce.remotetest.data.Movie
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.any
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.*
import org.mockito.Mockito.never
import org.mockito.Mockito.times
import org.mockito.Mockito.verify

/**
 * Unit tests for the implementation of the in-memory repository with cache.
 */
class MoviesRepositoryTest {

    private val MOVIE_TITLE = "title"
    private val MOVIE_TITLE2 = "title2"
    private val MOVIE_TITLE3 = "title3"
    private val MOVIE_DIRECTOR = "Director1"
    private val MOVIES = Lists.newArrayList(
        Movie(title = "Title1", director = "Description1"),
            Movie(title = "Title2", director = "Description2"))
    private lateinit var moviesRepository: MoviesRepository
    @Mock private lateinit var moviesRemoteDataSource: MoviesDataSource
    @Mock private lateinit var moviesLocalDataSource: MoviesDataSource
    @Mock private lateinit var getMovieCallback: MoviesDataSource.GetMovieCallback
    @Mock private lateinit var loadMoviesCallback: MoviesDataSource.LoadMoviesCallback
    @Mock private lateinit var clearFavoritesCallback: MoviesDataSource.ClearFavoriteMoviesCallback
    @Mock private lateinit var deleteMovieCallback: MoviesDataSource.DeleteMovieCallback

    @Captor private lateinit var moviesCallbackCaptor:
            ArgumentCaptor<MoviesDataSource.LoadMoviesCallback>
    @Captor private lateinit var movieCallbackCaptor: ArgumentCaptor<MoviesDataSource.GetMovieCallback>

    @Before fun setupMoviesRepository() {
//        MockitoAnnotations.initMocks(this)
//        moviesRepository = MoviesDataSource.getInstance(moviesRemoteDataSource, moviesLocalDataSource)
    }

    @After fun destroyRepositoryInstance() {
//        MoviesDataSource.destroyInstance()
    }

//    @Test fun getMoviesTest() {
//        twoMoviesLoadCallsToRepository(loadMoviesCallback)
//        verify<MoviesDataSource>(moviesRemoteDataSource).getMovies("title", loadMoviesCallback)
//    }
//
//    @Test fun getMoviesFromLocalDataSource() {
//        moviesLocalDataSource.getMovies("title", loadMoviesCallback)
//        verify<MoviesDataSource>(moviesLocalDataSource).getMovies("title", loadMoviesCallback)
//    }
//
//    @Test fun favoriteMovieTest() {
//        with(moviesRepository) {
//            val newMovie = Movie(title = MOVIE_TITLE, director = MOVIE_DIRECTOR, id="1234")
//            setMovieAsFavorite(newMovie, getMovieCallback)
//            verify<MoviesDataSource>(moviesLocalDataSource).setMovieAsFavorite(newMovie, getMovieCallback)
//            val movie = moviesRepository.getMovie(newMovie.id, getMovieCallback)
//            assertThat(newMovie.id, `is`("1234"))
//            val cachedNewMovie = moviesRepository.getMovie(newMovie.id, getMovieCallback)
//            assertNotNull(cachedNewMovie as Movie)
//            assertThat(cachedNewMovie.favorite, `is`(0))
//        }
//    }
//
//    @Test fun getMovieTest() {
//        moviesRepository.getMovie(MOVIE_TITLE, getMovieCallback)
//        verify(moviesLocalDataSource).getMovie(Mockito.eq(MOVIE_TITLE), getMovieCallback)
//    }
//
//    @Test fun deleteAllMovies() {
//        with(moviesRepository) {
//            val newMovie = Movie(title = MOVIE_TITLE, director = MOVIE_DIRECTOR).apply { favorite = 1 }
//            saveMovieLocally(newMovie, getMovieCallback)
//            val newMovie2 = Movie(title = MOVIE_TITLE2, director = MOVIE_DIRECTOR)
//            saveMovieLocally(newMovie2, getMovieCallback)
//            val newMovie3 = Movie(title = MOVIE_TITLE3, director = MOVIE_DIRECTOR).apply { favorite = 1 }
//            saveMovieLocally(newMovie3, getMovieCallback)
//
//            clearFavorites(clearFavoritesCallback)
//
//            verify(moviesRemoteDataSource).clearFavorites(clearFavoritesCallback)
//            verify(moviesLocalDataSource).clearFavorites(clearFavoritesCallback)
//
////            assertThat(moviesRepository.getMovie(newMovie2.id, getMovieCallback), `is`(1))
//            val movie = moviesRepository.getMovie(newMovie2.id, getMovieCallback)
//            assertNotNull(movie as Movie)
//            assertTrue(movie.favorite == 0)
//            assertThat(movie.title, `is`(MOVIE_TITLE2))
//        }
//    }
//
//    @Test fun deleteMovieTest() {
//        with(moviesRepository) {
//            val newMovie = Movie(title = MOVIE_TITLE, director = MOVIE_DIRECTOR).apply { favorite = 1 }
//            saveMovieLocally(newMovie, getMovieCallback)
//            assertThat(cachedMovies.containsKey(newMovie.id), `is`(true))
//
//            deleteLocalMovie(newMovie.id, deleteMovieCallback)
////            verify(moviesLocalDataSource).deleteLocalMovie(newMovie.id)
//            assertThat(cachedMovies.containsKey(newMovie.id), `is`(false))
//        }
//    }
//
//    /**
//     * Convenience method that issues two calls to the movies repository
//     */
//    private fun twoMoviesLoadCallsToRepository(callback: MoviesDataSource.LoadMoviesCallback) {
////        when(moviesRepository.getMovies(callback)
////        verify(moviesLocalDataSource).getMovies(capture(moviesCallbackCaptor))
//        moviesCallbackCaptor.value.onDataNotAvailable()
////        verify(moviesRemoteDataSource).getMovies(capture(moviesCallbackCaptor))
//        moviesCallbackCaptor.value.onMoviesLoaded(MOVIES)
////        moviesRepository.getMovies(callback)
//    }
//
//    private fun setMoviesNotAvailable(dataSource: MoviesDataSource) {
////        verify(dataSource).getMovies(capture(moviesCallbackCaptor))
//        moviesCallbackCaptor.value.onDataNotAvailable()
//    }
//
//    private fun setMoviesAvailable(dataSource: MoviesDataSource, movies: List<Movie>) {
////        verify(dataSource).getMovies(capture(moviesCallbackCaptor))
//        moviesCallbackCaptor.value.onMoviesLoaded(movies)
//    }
//
//    private fun setMovieNotAvailable(dataSource: MoviesDataSource, movieId: String) {
////        verify(dataSource).getMovie(eq(movieId), capture(movieCallbackCaptor))
//        movieCallbackCaptor.value.onDataNotAvailable()
//    }
//
//    private fun setMovieAvailable(dataSource: MoviesDataSource, movie: Movie) {
////        verify(dataSource).getMovie(eq(movie.id), capture(movieCallbackCaptor))
//        movieCallbackCaptor.value.onMovieLoaded(movie)
//    }
}
