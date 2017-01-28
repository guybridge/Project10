package au.com.wsit.project10.model;

import java.util.ArrayList;

/**
 * Created by guyb on 28/01/17.
 */

public class Topic
{
    private String topicName;
    private String topicId;
    private ArrayList<Result> topicVideosList;

    public String getTopicName()
    {
        return topicName;
    }

    public void setTopicName(String topicName)
    {
        this.topicName = topicName;
    }

    public String getTopicId()
    {
        return topicId;
    }

    public void setTopicId(String topicId)
    {
        this.topicId = topicId;
    }

    public ArrayList<Result> getTopicVideosList()
    {
        return topicVideosList;
    }

    public void setTopicVideosList(ArrayList<Result> topicVideosList)
    {
        this.topicVideosList = topicVideosList;
    }



}
