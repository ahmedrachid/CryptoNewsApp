package com.digger.app.ui.dashboard;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.digger.app.model.DataPlot;
import com.digger.app.model.News;
import com.digger.app.query.QueryUtils;
import com.github.mikephil.charting.data.Entry;

import java.security.KeyStore;
import java.util.ArrayList;
import java.util.Map;

public class ChartViewModel extends ViewModel {


    private MutableLiveData<ArrayList<Entry>> dataVals = new MutableLiveData<>();

    private  MutableLiveData<ArrayList<String>> labels = new MutableLiveData<>();

    public MutableLiveData<ArrayList<String>> getLabels() {
        return labels;
    }

    private GraphTask task = new GraphTask();

    public ChartViewModel() {

        task.execute();
    }

    public MutableLiveData<ArrayList<Entry>> dataValues() {

        return dataVals;

    }

    private class GraphTask extends AsyncTask<String, Void, DataPlot> {

        @Override
        protected DataPlot doInBackground(String... urls) {

            DataPlot result = QueryUtils.fetchHistoryAndPrediction(
                    "https://ahmed-newsapp.ndogga.com/predictions");

            return  result;
        }

        @Override
        protected  void onPostExecute(DataPlot result){
            labels.postValue(result.getDates());

            dataVals.postValue(result.getEntries());
        }
    }



}
