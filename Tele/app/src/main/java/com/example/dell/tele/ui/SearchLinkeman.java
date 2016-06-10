package com.example.dell.tele.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.dell.tele.R;

/**
 * Created by dell on 2016/6/9.
 */
public class SearchLinkeman extends LinearLayout {

    private SearchView searchView;
    private ListView showResult;

    public SearchLinkeman(Context context, AttributeSet attrs) {

        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.search_layoutr, this);


    }
}
