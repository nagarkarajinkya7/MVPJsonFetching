package com.appmaker.mvpexample.presenter;

import android.os.AsyncTask;

import com.appmaker.mvpexample.contractor.MainActivityContract;
import com.appmaker.mvpexample.contractor.MainActivityContract.Presenter;
import com.appmaker.mvpexample.contractor.MainActivityContract.View;
import com.appmaker.mvpexample.model.MainViewDataModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivityPresenter implements Presenter {

    private static View mView;

    private static List<MainViewDataModel> model;
    static HttpURLConnection mHttpURLConnection;
    private static final int CONNECTION_TIMEOUT = 10000;
    private static final int READ_TIMEOUT = 15000;


    private URL mUrl;

    public MainActivityPresenter(View mView) {
        this.mView = mView;
        initPresenter();
    }

    private void initPresenter() {
        mView.initView();
    }


    @Override
    public void onClick(android.view.View view) {
        try{
            fetchJsonData();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void fetchJsonData() {

        try {
            new FetchAsync().execute("https://demonuts.com/Demonuts/JsonTest/Tennis/json_parsing.php");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class FetchAsync extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            if(mView != null){
                mView.showProgressDialog();
            }
        }

        @Override
        protected String doInBackground(String... params) {

            String url = params[0];
            if (url != null) {
                URL urlObject = null;
                try {
                    urlObject = new URL(url);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    return "FAILED";
                }
                try {
                    mHttpURLConnection = (HttpURLConnection) urlObject.openConnection();
                    mHttpURLConnection.setConnectTimeout(CONNECTION_TIMEOUT);
                    mHttpURLConnection.setReadTimeout(READ_TIMEOUT);
                    mHttpURLConnection.setDoOutput(true);
                    mHttpURLConnection.setRequestMethod("GET");

                    int rsp_code = mHttpURLConnection.getResponseCode();
                    if (rsp_code == HttpURLConnection.HTTP_OK) {

                        InputStream inputStream = mHttpURLConnection.getInputStream();
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                        StringBuffer stringBuffer = new StringBuffer();
                        String line;

                        while ((line = bufferedReader.readLine()) != null) {
                            stringBuffer.append(line);
                        }
                        inputStream.close();
                        if (stringBuffer.length() > 0) {
                            return (stringBuffer.toString());
                        } else {
                            return "FAILED";
                        }
                    } else {
                        return "FAILED";
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return "FAILED";
                }
            } else {
                return "FAILED";
            }
        }

        @Override
        protected void onPostExecute(String result) {

            if (mView != null) {
                mView.hideProgressDialog();
            }
            if (!result.equals("FAILED")) {
                model = new ArrayList<>();

                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getString("status").equals("true")) {
                        JSONArray dataArray = jsonObject.getJSONArray("data");

                        for (int i = 0; i < dataArray.length(); i++) {
                            JSONObject currentObject = dataArray.getJSONObject(i);
                            MainViewDataModel currentModel = new MainViewDataModel(currentObject.getString("name"), currentObject.getString("imgURL"), currentObject.getString("country"),
                                    currentObject.getString("city"));
                            model.add(currentModel);
                        }
                        if(!model.isEmpty()){
                            mView.setViewData(model);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }
    }
}
