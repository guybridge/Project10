package au.com.wsit.project10.ui;

import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;
import android.widget.TextView;

import au.com.wsit.project10.R;

public class MainActivity extends AppCompatActivity
{

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
}
