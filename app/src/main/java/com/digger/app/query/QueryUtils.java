package com.digger.app.query;

import android.text.TextUtils;
import android.util.Log;

import com.digger.app.model.Alert;
import com.digger.app.model.AllStocks;
import com.digger.app.model.DataPlot;
import com.digger.app.model.News;
import com.digger.app.model.Stock;
import com.github.mikephil.charting.data.Entry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;


public class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getSimpleName();


    private QueryUtils() {

    }

    public static void deleteAlert(String requestUrl){
        URL url = createUrl(requestUrl);



        try {
             makeHttpRequest(url, "GET", null);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);

        }



    }


     public static DataPlot fetchSentimentAnalysis(String requestUrl){
         URL url = createUrl(requestUrl);

         String jsonResponse = null;

         try {
             jsonResponse = makeHttpRequest(url, "GET", null);
         } catch (IOException e) {
             Log.e(LOG_TAG, "Problem making the HTTP request.", e);

         }


        DataPlot scores = extractPredictionNews(jsonResponse);

         return scores;
     }

    public static  DataPlot fetchHistoryAndPrediction(String requestUrl){

        URL url = createUrl(requestUrl);

        String jsonResponse = null;

        try {
            jsonResponse = makeHttpRequest(url, "GET", null);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);

        }


       DataPlot prediction = extractPredictionFromJson(jsonResponse);

        return prediction;
    }
    public static ArrayList<News> fetchNews(String requestUrl) {

        URL url = createUrl(requestUrl);

        String jsonResponse = null;

        try {
            jsonResponse = makeHttpRequest(url, "GET", null);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);

        }


        ArrayList<News> news = extractNewsFromJson(jsonResponse);

        return news;

    }


    public static AllStocks fetchStockData(String requestUrl) {

        URL url = createUrl(requestUrl);

        String jsonResponse = null;

        try {
            jsonResponse = makeHttpRequest(url, "GET", null);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);

        }


        AllStocks allStocks = extractFeatureFromJson(jsonResponse);

        return allStocks;

    }


    public static ArrayList<Alert> fetchAlerts(String requestUrl) {
           URL url = createUrl(requestUrl);

        String jsonResponse = null;

        try {
            jsonResponse = makeHttpRequest(url, "GET", null);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);

        }


        ArrayList<Alert> allAlerts = extractAlertFeatureFromJson(jsonResponse);

        return allAlerts;

    }


    public static void saveAlert(String requestUrl, String body) {

        URL url = createUrl(requestUrl);

        String jsonResponse = null;

        try {
            jsonResponse = makeHttpRequest(url, "POST",
                    body);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);

        }


    }


    private static URL createUrl(String stringUrl) {

        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }

        return url;
    }


    private static String makeHttpRequest(URL url, String method, String body) throws IOException {

        String jsonResponse = "";
        if (url == null) {
            return jsonResponse;
        }
        System.out.println(body);
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");


            urlConnection.setReadTimeout(60000);
            urlConnection.setConnectTimeout(60000);
            urlConnection.setRequestMethod(method);


            if (body != null) {
                urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true);
                try {
                    OutputStream os = urlConnection.getOutputStream();
                    os.write(body.getBytes("UTF-8"));
                    os.flush();
                    os.close();
                } catch (IOException e) {
                    Log.e(LOG_TAG, "Problem in body ", e);
                }
            }
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());

            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }

            if (inputStream != null) {
                inputStream.close();
            }
        }

        return jsonResponse;

    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();

        if (inputStream != null) {

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }




    private static DataPlot  extractPredictionNews(String jsonResponse){
        ArrayList<Entry> result = new ArrayList<>();

        if (TextUtils.isEmpty(jsonResponse)) {
            return null;
        }
        ArrayList<Float> scores = new ArrayList<>();
        ArrayList<String> labelsDate = new ArrayList<>();
        try {


            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(jsonResponse);

            // Extract the JSONArray associated with the key called "features",
            // which represents a list of features (or earthquakes).
            JSONArray stockJsonArray = (JSONArray) baseJsonResponse.get("dates");


            for (int index = 0; index < stockJsonArray.length(); index++) {
                labelsDate.add(stockJsonArray.getString(index));
                scores.add((float) index);
            }
            JSONArray stockResultJsonArray = (JSONArray) baseJsonResponse.get("score");
            ArrayList<Float> values = new ArrayList<>();
            for (int index = 0; index < stockResultJsonArray.length(); index++) {
                values.add((float) stockResultJsonArray.getDouble(index));
            }


            for (int i = 0; i < scores.size(); i++) {
                result.add(new Entry(scores.get(i), values.get(i)));

            }
        }

        catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        return new DataPlot(result,labelsDate);
    }

    private static DataPlot extractPredictionFromJson(String jsonResponse ){
        ArrayList<Entry> result = new ArrayList<>();

        if (TextUtils.isEmpty(jsonResponse)) {
            return null;
        }
        ArrayList<Float> stockDates = new ArrayList<>();
        ArrayList<String> labelsDate = new ArrayList<>();
        try {


            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(jsonResponse);

            // Extract the JSONArray associated with the key called "features",
            // which represents a list of features (or earthquakes).
            JSONArray stockJsonArray = (JSONArray) baseJsonResponse.get("stocks_dates");


            for (int index = 0; index < stockJsonArray.length(); index++) {
                 labelsDate.add(stockJsonArray.getString(index));
                stockDates.add((float) index);
            }
            JSONArray stockResultJsonArray = (JSONArray) baseJsonResponse.get("stocks_result");
            ArrayList<Float> values = new ArrayList<>();
            for (int index = 0; index < stockResultJsonArray.length(); index++) {
                values.add((float) stockResultJsonArray.getDouble(index));
            }


            for (int i = 0; i < stockDates.size(); i++) {
                result.add(new Entry(stockDates.get(i), values.get(i)));

            }
        }

            catch (JSONException e) {
                // If an error is thrown when executing any of the above statements in the "try" block,
                // catch the exception here, so the app doesn't crash. Print a log message
                // with the message from the exception.
                Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
            }

        return new DataPlot(result,labelsDate);

    }

    private static AllStocks extractFeatureFromJson(String earthquakeJSON) {


        ArrayList<Stock> cryptoList = new ArrayList<>();

        ArrayList<Stock> rawMaterialsList = new ArrayList<>();
        ArrayList<Stock> stockMarketIndicesList = new ArrayList<>();

        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(earthquakeJSON)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding earthquakes to
        AllStocks allStocks = new AllStocks();

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {


            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(earthquakeJSON);

            // Extract the JSONArray associated with the key called "features",
            // which represents a list of features (or earthquakes).
            JSONArray crypto = baseJsonResponse.getJSONArray("crypto");

            JSONArray stockMarketIndices = baseJsonResponse.getJSONArray("indice");
            JSONArray rawMaterials = baseJsonResponse.getJSONArray("matieres premieres");

            for (int i = 0; i < crypto.length(); i++) {

                JSONObject currentCrypto = crypto.getJSONObject(i);



                String id = currentCrypto.getString("id");


                Double marketOpen = currentCrypto.getDouble("marketOpen");

                Double PreviousClose = currentCrypto.getDouble("previousClose");

                Double regularMarketChangePercent = currentCrypto.getDouble("regularMarketChangePercent");
                Double regularMarketPrice = currentCrypto.getDouble("regularMarketPrice");

                String symbol =  currentCrypto.getString("symbol");

                String imageUrl =  currentCrypto.getString("image");




                Stock stock = new Stock(id, marketOpen, PreviousClose, regularMarketChangePercent, regularMarketPrice,
                        symbol  , imageUrl);

                cryptoList.add(stock);
            }


            for (int i = 0; i < rawMaterials.length(); i++) {

                JSONObject currentCrypto = rawMaterials.getJSONObject(i);


                String id = currentCrypto.getString("id");


                Double marketOpen = currentCrypto.getDouble("marketOpen");

                Double PreviousClose = currentCrypto.getDouble("previousClose");

                Double regularMarketChangePercent = currentCrypto.getDouble("regularMarketChangePercent");
                Double regularMarketPrice = currentCrypto.getDouble("regularMarketPrice");

                String symbol =  currentCrypto.getString("symbol");

                String imageUrl =  currentCrypto.getString("image");




                Stock stock = new Stock(id, marketOpen, PreviousClose, regularMarketChangePercent, regularMarketPrice ,
                        symbol  , imageUrl);

                rawMaterialsList.add(stock);
            }

            for (int i = 0; i < stockMarketIndices.length(); i++) {

                JSONObject currentCrypto = stockMarketIndices.getJSONObject(i);



                String id = currentCrypto.getString("id");


                Double marketOpen = currentCrypto.getDouble("marketOpen");

                Double PreviousClose = currentCrypto.getDouble("previousClose");

                Double regularMarketChangePercent = currentCrypto.getDouble("regularMarketChangePercent");
                Double regularMarketPrice = currentCrypto.getDouble("regularMarketPrice");

                String symbol =  currentCrypto.getString("symbol");

                String imageUrl =  currentCrypto.getString("image");




                Stock stock = new Stock(id, marketOpen, PreviousClose, regularMarketChangePercent, regularMarketPrice,
                        symbol  , imageUrl);

                stockMarketIndicesList.add(stock);
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        return new AllStocks(cryptoList, stockMarketIndicesList, rawMaterialsList);
    }

    private static ArrayList<Alert> extractAlertFeatureFromJson(String jsonAlert) {

        ArrayList<Alert> alerts = new ArrayList<>();


        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(jsonAlert)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding earthquakes to
        ArrayList<Alert> allAlerts = new ArrayList<Alert>();

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {


            // Create a JSONObject from the JSON response string
            JSONArray baseJsonResponse = new JSONArray(jsonAlert);

            // Extract the JSONArray associated with the key called "features",
            // which represents a list of features (or earthquakes).

            for (int i = 0; i < baseJsonResponse.length(); i++) {

                JSONObject currentAlert = baseJsonResponse.getJSONObject(i);


                String name = currentAlert.getString("stock");

                Double value = currentAlert.getDouble("value");


                Boolean more = currentAlert.getBoolean("more");

                int id = currentAlert.getInt("id");

                Alert alert = new Alert(name, more, value,id);


                alerts.add(alert);
            }

        } catch (Exception e) {

        }
        return alerts;
    }

    private static ArrayList<News> extractNewsFromJson(String jsonNews) {

        ArrayList<News> alerts = new ArrayList<>();


        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(jsonNews)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding earthquakes to
        ArrayList<Alert> allAlerts = new ArrayList<Alert>();

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {


            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(jsonNews);

            JSONArray articles = baseJsonResponse.getJSONArray("articles");


            // Extract the JSONArray associated with the key called "features",
            // which represents a list of features (or earthquakes).

            for (int i = 0; i < articles.length(); i++) {

                JSONObject currentArticle = articles.getJSONObject(i);


                String title = currentArticle.getString("title");

                String source = currentArticle.getJSONObject("source").getString("name");


                String description = currentArticle.getString("content");

                String imageUrl = currentArticle.getString("urlToImage");
                String url = currentArticle.getString("url");


                News n = new News(title, source, imageUrl,url,description);

                alerts.add(n);
            }

        } catch (Exception e) {

        }
        return alerts;
    }


}