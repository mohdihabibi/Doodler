package edu.sjsu.android.doodle;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ParseObject.registerSubclass(IndividualDoodle.class);
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("b7gvq59yIlbS4EE2SsODu8kHrXgf5TBmCjbdWoZn")
                .clientKey("fIX7bWXuMnKC436JizxENFzI6bGDHdn7q32PoQgS")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
