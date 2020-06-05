package com.digger.app.ui.dashboard;

import android.os.AsyncTask;
import android.os.Handler;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.digger.app.model.AllStocks;
import com.digger.app.model.Stock;
import com.digger.app.query.QueryUtils;

import java.util.ArrayList;

public class DashboardViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Stock>> mCrypto;
    private MutableLiveData<ArrayList<Stock>> mRawMaterials;
    private MutableLiveData<ArrayList<Stock>> mStockMarketIndices;
    StockAsyncTask stockFetcher  = new StockAsyncTask();
    public void fetchDataStock(){
//        ArrayList<Stock> stocks = new ArrayList<Stock>();
//        stocks.add(new Stock(1,"bitcoin",new Double(7000),new Double(7500),new Double(5),null));
//        stocks.add(new Stock(1,"bitcoin",new Double(7000),new Double(7500),new Double(5),null));

        stockFetcher = new StockAsyncTask ();
        stockFetcher.execute();


        // TODO:

        refresh(5000);


    }

    public void refresh(int milliseconds) {

        final Handler handler = new Handler ();

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                fetchDataStock();
            }
        };

        handler.postDelayed(runnable, milliseconds);
    }


    public DashboardViewModel() {
        mCrypto = new MutableLiveData<ArrayList<Stock>>();
        mRawMaterials = new MutableLiveData<ArrayList<Stock>>();
        mStockMarketIndices = new MutableLiveData<ArrayList<Stock>>();
    }

    public LiveData<ArrayList<Stock>> getCrypto(){
        return  mCrypto;
    }

    public LiveData<ArrayList<Stock>> getRawMaterials(){
        return  mRawMaterials;
    }

    public LiveData<ArrayList<Stock>> getStockMarketIndices(){
        return  mStockMarketIndices;
    }


    private class StockAsyncTask extends AsyncTask<String, Void, AllStocks>{

        @Override
        protected AllStocks doInBackground(String... urls) {

            AllStocks  result = QueryUtils.fetchStockData("https://a4bb16f2b859.ngrok.io/market" );

            return  result;
        }

        @Override
        protected  void onPostExecute(AllStocks result){
            mCrypto.postValue(result.getCrypto());
            mRawMaterials.postValue(result.getRawMaterials());
            mStockMarketIndices.postValue(result.getStockMarketIndices());
        }
    }


}