package com.rathandevaki.farmersworld;

public class Comments {
    private String Comment,CommentorName;

    // Mandatory empty constructor
    // for use of FirebaseUI
    public Comments() {}


    // Getter and setter method
    public String getComment ()
    {
        return Comment;
    }
    public void setComments(String _comment)
    {
        this.Comment=_comment;
    }

    public String getCommentorName(){
        return CommentorName;
    }
    public void setCommentorName(String _CommentatorName)
    {
        this.CommentorName=_CommentatorName;
    }
}
