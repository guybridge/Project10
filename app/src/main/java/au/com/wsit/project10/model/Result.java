package au.com.wsit.project10.model;

/**
 * Created by guyb on 28/12/16.
 */

public class Result
{
    private String speakerName;
    private String sampleComment;
    private String searchedTerm; // TODO: Highlight

    public String getSearchedTerm()
    {
        return searchedTerm;
    }

    public void setSearchedTerm(String searchedTerm)
    {
        this.searchedTerm = searchedTerm;
    }

    public String getSampleComment()
    {
        return sampleComment;
    }

    public void setSampleComment(String sampleComment)
    {
        this.sampleComment = sampleComment;
    }

    public String getSpeakerName()
    {
        return speakerName;
    }

    public void setSpeakerName(String speakerName)
    {
        this.speakerName = speakerName;
    }


}
