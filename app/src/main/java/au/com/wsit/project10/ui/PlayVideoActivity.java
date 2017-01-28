package au.com.wsit.project10.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import au.com.wsit.project10.R;
import au.com.wsit.project10.utils.Constants;

public class PlayVideoActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener
{
    private YouTubePlayerView playerView;
    private static final int RECOVERY_REQUEST = 1;
    private TextView videoTitleTextView;
    private TextView videoDescriptionTextView;

    private RecyclerView comments;
    private LinearLayoutManager layoutManager;

    private String videoID;
    private String videoTitle;
    private String videoDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);

        // Title and decription setup
        videoTitleTextView = (TextView) findViewById(R.id.videoTitle);
        videoDescriptionTextView = (TextView) findViewById(R.id.videoDescription);

        // Video player setup
        playerView = (YouTubePlayerView) findViewById(R.id.youtubeVideo);
        playerView.initialize(Constants.YOUTUBE_API_KEY, this);

        // Comments RecyclerView setup
        comments = (RecyclerView) findViewById(R.id.commentsRecyclerView);
        layoutManager = new LinearLayoutManager(this);
        comments.setLayoutManager(layoutManager);

        // Get the data from the intent
        Intent intent = getIntent();
        videoID = intent.getStringExtra(Constants.VIDEO_ID);
        videoTitle = intent.getStringExtra(Constants.VIDEO_TITLE);
        videoDescription = intent.getStringExtra(Constants.VIDEO_DESCRIPTION);

        // Set the title and description
        videoTitleTextView.setText(videoTitle);
        videoDescriptionTextView.setText(videoDescription);

    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored)
    {
        if(!wasRestored)
        {
            // Load the video from the ID we got from the intent
            youTubePlayer.cueVideo(videoID);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorReason)
    {
        if (errorReason.isUserRecoverableError())
        {
            errorReason.getErrorDialog(this, RECOVERY_REQUEST).show();
        }
        else
        {
            String error = String.format(getString(R.string.player_error), errorReason.toString());
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == RECOVERY_REQUEST)
        {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(Constants.YOUTUBE_API_KEY, this);
        }
    }

    protected YouTubePlayer.Provider getYouTubePlayerProvider()
    {
        return playerView;
    }
}
