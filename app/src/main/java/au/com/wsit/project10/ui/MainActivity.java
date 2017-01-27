package au.com.wsit.project10.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import au.com.wsit.project10.R;
import au.com.wsit.project10.api.CapitolWordsService;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity
{

    private static final String TAG = MainActivity.class.getSimpleName();
    private TextView errorTextView;
    private ProgressBar resultsProgress;
    private RecyclerView resultsRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        errorTextView = (TextView) findViewById(R.id.errorText);
        resultsProgress = (ProgressBar) findViewById(R.id.loadingResultsProgressBar);
        resultsRecycler = (RecyclerView) findViewById(R.id.resultsRecyclerView);
        resultsRecycler.setLayoutManager(new LinearLayoutManager(this));


    }

    private void search()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(CapitolWordsService.CAPITOL_WORDS_BASE_URL)
                .build();

        CapitolWordsService service = retrofit.create(CapitolWordsService.class);

        service.results("search term");
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
                Toast.makeText(MainActivity.this, "Searching: " + searchView.getQuery(), Toast.LENGTH_LONG).show();
                toggleProgress();
                searchView.clearFocus();
                // TODO: Start search function
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        switch (id)
        {
            case R.id.action_search:
                // Start search
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
