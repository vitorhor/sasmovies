package com.androidapp.sasmovies;

import com.androidapp.sasmovies.contract.MovieListContract;
import com.androidapp.sasmovies.entity.Movie;
import com.androidapp.sasmovies.presenter.MovieListPresenter;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MovieListPresenterTest {

    private List<Movie> mockListMovies = new ArrayList<Movie>() {{
        add(new Movie(1, "Filme Popular 1", ""));
        add(new Movie(2, "Filme Popular 2", ""));
        add(new Movie(3, "Filme Popular 3", ""));
    }};

    private JSONObject mockJsonObjectResult;

    private MovieListPresenter movieListPresenter;

    @Mock
    private MockMovieService mockMovieService;

    @Mock
    private MockAuthenticationService mockAuthenticationService;

    @Mock
    private MovieListContract.View mockView;

    @Captor
    private ArgumentCaptor<JsonHttpResponseHandler> jsonHttpResponseHandlerCaptor;

    @Before
    public void setupMovieListPresenter() {

        MockitoAnnotations.initMocks(this);

        JSONArray jsonArray = new JSONArray(mockListMovies);

        mockJsonObjectResult = new JSONObject();

        try {
            mockJsonObjectResult.put("results", jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        movieListPresenter = new MovieListPresenter(mockMovieService, mockAuthenticationService, mockView);

    }

    @Test
    public void listMoviesAndShow() {

        movieListPresenter.loadMovies();

        Mockito.verify(mockMovieService).getPopularMovies(jsonHttpResponseHandlerCaptor.capture());
        jsonHttpResponseHandlerCaptor.getValue().onSuccess(200, new Header[13], mockJsonObjectResult);

        Mockito.verify(mockView).showMovies(mockListMovies);

    }

}
