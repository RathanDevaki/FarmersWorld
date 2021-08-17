package com.rathandevaki.farmersworld;

public class Voice {


    private String AboutPost;

    private String ProfilePhoto,UserID,UserName,PostPhoto;

    // Mandatory empty constructor
    // for use of FirebaseUI
    public Voice() {}


    // Getter and setter method
    public String getUserID ()
    {
        return UserID;
    }
    public void setUserID(String UserID)
    {
        this.UserID=UserID;
    }

    public String getPostPhoto(){
        return PostPhoto;
    }
    public void setPostPhoto(String PostPhoto)
    {
        this.PostPhoto=PostPhoto;
    }

    public String getUserName()
    {
        return UserName;
    }
    public void setUserName(String UserName)
    {
        this.UserName=UserName;
    }

    public String getAboutPost()
    {
        return AboutPost;
    }
    public void setAboutPost(String AboutPost)
    {
        this.AboutPost=AboutPost;
    }

    public String getProfilePhoto()
    {
        return ProfilePhoto;
    }
    public void setProfilePhoto(String ProfilePhoto)
    {
        this.ProfilePhoto = ProfilePhoto;
    }

}
