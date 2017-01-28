package au.com.wsit.project10.model;

/**
 * Created by guyb on 28/01/17.
 */

public class Comment
{
    private String authorName;
    private String authorComment;
    private String authorImageUrl;

    public String getAuthorName()
    {
        return authorName;
    }

    public void setAuthorName(String authorName)
    {
        this.authorName = authorName;
    }

    public String getAuthorComment()
    {
        return authorComment;
    }

    public void setAuthorComment(String authorComment)
    {
        this.authorComment = authorComment;
    }

    public String getAuthorImageUrl()
    {
        return authorImageUrl;
    }

    public void setAuthorImageUrl(String authorImageUrl)
    {
        this.authorImageUrl = authorImageUrl;
    }
}
