package au.com.wsit.project10.utils;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;

/**
 * Created by guyb on 28/01/17.
 */

public class ParseApplication extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        Parse.enableLocalDatastore(getApplicationContext());
        Parse.initialize(this, "YOUR_APP_ID", "YOUR_CLIENT_KEY");
        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        ParseACL.setDefaultACL(defaultACL, true);



    }
}
