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
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("AY3MjS6zo6HWQ6k1eNVSWjHHrGTT02ZMR7iS6JBj")
                .clientKey("d1IXpTGOOz7H1Q85BPtkjmE6GkzkrtIZk9OmNHSD")
                .server("https://parseapi.back4app.com/").build()
        );

        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        ParseACL.setDefaultACL(defaultACL, true);



    }
}
