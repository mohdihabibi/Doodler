package edu.sjsu.android.doodle;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("post")
public class IndividualDoodle extends ParseObject {

    public static final String DESCRIPTION = "description";
    public static final String IMG = "image";
    public static final String USER = "user";
    public static final String DATE = "createdAt";

    public String getDescription(){
        return getString(DESCRIPTION);
    }

    public void setDescription(String description){
        put(DESCRIPTION, description);
    }

    public ParseFile getImage(){
        return getParseFile(IMG);
    }

    public void setImage(ParseFile parseFile){
        put(IMG, parseFile);
    }

    public ParseUser getUser(){
        return getParseUser(USER);
    }

    public void setUser(ParseUser parseUser){
        put(USER, parseUser);
    }

}
