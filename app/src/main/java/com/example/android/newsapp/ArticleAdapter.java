package com.example.android.newsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Paviliondm4 on 2/26/2017.
 */

public class ArticleAdapter extends ArrayAdapter<Article> {


    private Context context ;
    private Article currentArticle ;

    /*
    Public constructor that passes the context and the source of information for the adapter
    @param context is the context of the activity
    @param article is the List that contains objects of type Article
     */
    public ArticleAdapter(Context context , List<Article> article){
        super(context,0 ,article);
        this.context = context ;

    }

    @NonNull
    @Override
    public View getView(int position, View listItemView, ViewGroup parent) {

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_article_item, parent, false);
        }


        currentArticle = getItem(position);

        // Find the TextView with view ID tv1 for setting title
        TextView titleTextView = (TextView) listItemView.findViewById(R.id.tv1) ;
        // Display the magnitude of the current book in that TextView
        titleTextView.setText(currentArticle.getTitle()) ;

        // Find the TextView with view ID tv2 for setting title
        TextView sectionTextView = (TextView) listItemView.findViewById(R.id.tv2) ;
        // Display the magnitude of the current book in that TextView
        sectionTextView.setText(currentArticle.getSection()) ;

        // Find the TextView with view ID tv1 for setting title
        TextView dateTextView = (TextView) listItemView.findViewById(R.id.tv3) ;
        // Display the magnitude of the current book in that TextView
        dateTextView.setText(currentArticle.getDate()) ;

        return listItemView ;
    }

}
