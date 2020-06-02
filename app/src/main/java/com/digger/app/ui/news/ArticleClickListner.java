package com.digger.app.ui.news;

import com.digger.app.model.News;

@FunctionalInterface
public interface ArticleClickListner {


    void onClick(News article );

}
