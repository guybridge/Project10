package au.com.wsit.project10.model;

import rx.subjects.ReplaySubject;

/**
 * Youtube search results
 */

public class Result
{
    private String videoUrl;
    private String videoTitle;
    private String videoDescription;
    private String videoID;

    public String getVideoID()
    {
        return videoID;
    }

    public void setVideoID(String videoID)
    {
        this.videoID = videoID;
    }

    public String getVideoUrl()
    {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl)
    {
        this.videoUrl = videoUrl;
    }

    public String getVideoTitle()
    {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle)
    {
        this.videoTitle = videoTitle;
    }

    public String getVideoDescription()
    {
        return videoDescription;
    }

    public void setVideoDescription(String videoDescription)
    {
        this.videoDescription = videoDescription;
    }
}
