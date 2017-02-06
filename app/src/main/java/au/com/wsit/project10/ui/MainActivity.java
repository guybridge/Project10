package au.com.wsit.project10.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;

import au.com.wsit.project10.R;
import au.com.wsit.project10.adapters.ResultsAdapter;
import au.com.wsit.project10.api.SearchHelper;
import au.com.wsit.project10.model.Result;
import au.com.wsit.project10.utils.Constants;

public class MainActivity extends AppCompatActivity
{

    private static final String TAG = MainActivity.class.getSimpleName();
    private ProgressBar resultsProgress;
    private RecyclerView resultsRecycler;
    private ResultsAdapter resultsAdapter;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultsProgress = (ProgressBar) findViewById(R.id.loadingResultsProgressBar);
        resultsRecycler = (RecyclerView) findViewById(R.id.resultsRecyclerView);
        resultsRecycler.setLayoutManager(new LinearLayoutManager(this));
        resultsAdapter = new ResultsAdapter(this);
        resultsRecycler.setAdapter(resultsAdapter);
        sharedPreferences = getSharedPreferences(Constants.PREFERENCES_FILE, Context.MODE_PRIVATE);

        // Get the last thing searched
        String lastSearchTerm = sharedPreferences.getString(Constants.LAST_SEARCH_TERM, "Android");

        // Get the most popular to start with
        search(lastSearchTerm);


    }


    private void toggleProgress()
    {
        if(resultsProgress.getVisibility() == View.GONE)
        {
            resultsProgress.setVisibility(View.VISIBLE);
        }
        else
        {
            resultsProgress.setVisibility(View.GONE);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);


        final SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                searchView.clearFocus();
                search(searchView.getQuery().toString());
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText)
            {
                return false;
            }
        });

        return true;
    }

    // Start a search using the API call
    public void search(String term)
    {
        sharedPreferences.edit().putString(Constants.LAST_SEARCH_TERM, term).apply();
        toggleProgress();

        SearchHelper searchHelper = new SearchHelper();
        searchHelper.search(term, new SearchHelper.SearchCallback()
        {
            @Override
            public void onSuccess(ArrayList<Result> results)
            {
                toggleProgress();
                resultsAdapter.swap(results);
            }

            @Override
            public void onFail(String errorMessage)
            {
                toggleProgress();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        switch (id)
        {
            case R.id.action_search:
                // Start search
                break;
            case R.id.action_topics:
                Intent topicsIntent = new Intent(this, TopicsActivity.class);
                startActivity(topicsIntent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
