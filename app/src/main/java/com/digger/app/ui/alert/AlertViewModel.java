package com.digger.app.ui.alert;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.digger.app.model.Alert;
import com.digger.app.model.AllStocks;
import com.digger.app.query.QueryUtils;

import java.util.ArrayList;

public class AlertViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Alert>> alerts = new MutableLiveData<ArrayList<Alert>>();

    public AlertViewModel() {

    }


    public LiveData<ArrayList<Alert>> getAlerts() {
        return alerts;
    }

    private class AlertAsyncTask extends AsyncTask<String, Void, ArrayList<Alert>> {

        @Override
        protected ArrayList<Alert> doInBackground(String... urls) {

            ArrayList<Alert>  result = QueryUtils.fetchAlerts(urls[0]);

            return  result;
        }

        @Override
        protected  void onPostExecute(ArrayList<Alert> result){

            alerts.postValue(result);

        }
    }



}