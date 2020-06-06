package com.digger.app.ui.alert;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.digger.app.model.Alert;
import com.digger.app.model.AllStocks;
import com.digger.app.query.QueryUtils;
import com.onesignal.OneSignal;

import java.util.ArrayList;

public class AlertViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Alert>> alerts = new MutableLiveData<ArrayList<Alert>>();


    private  AlertAsyncTask task ;
    public AlertViewModel() {
  }
    public void refreshAlerts(){
        if(task  == null ){
            task = new AlertAsyncTask();
            task.execute();
        }
        task = null;

    }


    public LiveData<ArrayList<Alert>> getAlerts() {
        return alerts;
    }

    private class AlertAsyncTask extends AsyncTask<String, Void, ArrayList<Alert>> {

        @Override
        protected ArrayList<Alert> doInBackground(String... urls) {
            String UUID = OneSignal.getPermissionSubscriptionState().getSubscriptionStatus().getUserId();
            ArrayList<Alert>  result = QueryUtils.fetchAlerts(
                    "http://efbf0692ba03.ngrok.io/getalertsUsr/"+UUID
);

            return  result;
        }

        @Override
        protected  void onPostExecute(ArrayList<Alert> result){

            alerts.postValue(result);

        }
    }






}