package com.digger.app.ui.model;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.digger.app.model.DataPlot;
import com.digger.app.query.QueryUtils;
import com.digger.app.ui.dashboard.ChartViewModel;
import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.List;

public class ModelViewModel  extends ViewModel {

    private MutableLiveData<ArrayList<Entry>> dataVals = new MutableLiveData<>();
    private  MutableLiveData<ArrayList<String>> labels = new MutableLiveData<>();

    private GraphTaskNews task = new GraphTaskNews();
    public ModelViewModel() {
        task.execute();

    }
    public MutableLiveData<ArrayList<String>> getLabels() {
        return labels;
    }





    public MutableLiveData<ArrayList<Entry>> dataValues() {

        return dataVals;

    }

    private class GraphTaskNews extends AsyncTask<String, Void, DataPlot> {

        @Override
        protected DataPlot doInBackground(String... urls) {

            DataPlot result = QueryUtils.fetchSentimentAnalysis(
                    "https://ahmed-newsapp.ndogga.com/sentiment/bitcoin");

            return  result;
        }

        @Override
        protected  void onPostExecute(DataPlot result){

            labels.postValue(result.getDates());

            dataVals.postValue(result.getEntries());        }
    }


}