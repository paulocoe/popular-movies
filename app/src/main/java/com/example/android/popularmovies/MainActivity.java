package com.example.android.popularmovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.rv_main_grid);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new GridLayoutManager(this, 3);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MovieAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }

    private void loadMovies(){
        //TODO: Replace the hardcoded sorting param with the value stored in the options
        new FetchMoviesTask().execute("popular", "1");
    }

    public class FetchMoviesTask extends AsyncTask<String, Void, Movie[]>{

        @Override
        protected Movie[] doInBackground(String... params) {

            if (params.length == 0)
                return null;

            String sortOption = params[0];
            int page = Integer.parseInt(params[1]);

            URL url = NetworkUtils.buildUrl(sortOption, page);
            try {
                NetworkUtils.getResponseFromHttpUrl(url);
                return null;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
