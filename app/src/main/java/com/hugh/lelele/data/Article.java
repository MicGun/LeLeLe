package com.hugh.lelele.data;

public class Article {

    private String mTitle;
    private String mContent;
    private String mType;
    private String mTime;
    private String mAuthor;
    private String mAuthorEmail;
    private String mAuthorPicture;

    public Article() {

        mTitle = "";
        mContent = "";
        mType = "";
        mTime = "";
        mAuthor = "";
        mAuthorEmail = "";
        mAuthorPicture = "";
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public String getTime() {
        return mTime;
    }

    public void setTime(String time) {
        mTime = time;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String author) {
        mAuthor = author;
    }

    public String getAuthorEmail() {
        return mAuthorEmail;
    }

    public void setAuthorEmail(String authorEmail) {
        mAuthorEmail = authorEmail;
    }

    public String getAuthorPicture() {
        return mAuthorPicture;
    }

    public void setAuthorPicture(String authorPicture) {
        mAuthorPicture = authorPicture;
    }
}
