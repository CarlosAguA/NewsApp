package com.example.android.newsapp;

import static android.os.Build.VERSION_CODES.M;

/**
 * Created by Paviliondm4 on 2/26/2017.
 */

/*
 Java class with the states of an Article object retrieved from The Guardian API
 */
public class Article {

    private String section ;
    private String title ;
    private String url ;
    private String date ;
    //private String image ; pending

    /*
    Public constructor
    @param section that indicates the section the article belongs to
    @param title with the name of the article
    @param url with the web url to open the page where the article was published when item is clicked
    @param date to display the date when the article was published
     */
    public Article (String section, String title, String date, String url){
        this.section = section ;
        this.title = title ;
        this.url = url ;
        this.date = date ;

    }


    public String getSection() {
        return section;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getDate() {
        return date;
    }
}
