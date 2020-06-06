package com.digger.app.ui.alert;

import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModel;

import com.digger.app.model.Alert;
import com.digger.app.query.QueryUtils;
import com.google.gson.Gson;
import com.onesignal.OneSignal;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.telephony.TelephonyManager;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;


class EditorViewModel extends ViewModel {

    final private Context context;

    private Alert createdAlert ;

    private String stock;
    private boolean more;
    private Double value;




    private  AlertRegisterAsyncTask task ;

    EditorViewModel(Context context) {
        this.context = context;
    }


    public Alert getCreatedAlert() {
        return createdAlert;
    }

    public void setCreatedAlert() {
        this.createdAlert = new Alert(stock, more, value);
    }

    public void registerAlert() {
        if (task == null ){
            task = new AlertRegisterAsyncTask();
            task.execute();
        }

        task = null;
    }

    private String buildBodyFromAlert() {
        String res = "";

        if (createdAlert != null) {

            TelephonyManager telephonyManager;
            telephonyManager = (TelephonyManager) context.getSystemService(Context.
                    TELEPHONY_SERVICE);


            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.

            }
            String UUID = OneSignal.getPermissionSubscriptionState().getSubscriptionStatus().getUserId();
            createdAlert.setDeviceId(UUID);
            Gson gson = new Gson();

            return  gson.toJson(createdAlert);

        }

        return res;

    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public boolean isMore() {
        return more;
    }

    public void setMore(boolean more) {
        this.more = more;
    }

    public Double isValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public class AlertRegisterAsyncTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... urls) {
            QueryUtils.saveAlert("https://ahmed-alerts.ndogga.com/saveAlert", buildBodyFromAlert());

            return  null;
        }

        @Override
        protected  void onPostExecute(Void result){

            ((Activity) context).finish();



        }
    }



}
