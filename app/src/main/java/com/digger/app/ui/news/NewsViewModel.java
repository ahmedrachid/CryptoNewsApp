package com.digger.app.ui.news;

import android.os.AsyncTask;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.digger.app.model.Alert;
import com.digger.app.model.News;
import com.digger.app.query.QueryUtils;
import com.digger.app.ui.dashboard.DashboardViewModel;

import java.util.ArrayList;

public class NewsViewModel extends ViewModel {

    private MutableLiveData<ArrayList<News>> news = new MutableLiveData<>();
    @Nullable
    private   NewsViewModel.AlertAsyncTask activeTask  ;



    public NewsViewModel() {
    }

    public void loadNews(String text){
        if(activeTask != null){
            activeTask.cancel(false);
        }
        activeTask  = new AlertAsyncTask();
        activeTask.execute(text);



    }


    public LiveData<ArrayList<News>> getNews() {
        return news;
    }

    private class AlertAsyncTask extends AsyncTask<String, Void, ArrayList<News>> {

        @Override
        protected ArrayList<News> doInBackground(String... urls) {

            ArrayList<News>  result = QueryUtils.fetchNews(
                    "https://ahmed-newsapp.ndogga.com/newskeyword/"+urls[0]);

            return  result;
        }

        @Override
        protected  void onPostExecute(ArrayList<News> result){

            news.postValue(result);

        }
    }


}