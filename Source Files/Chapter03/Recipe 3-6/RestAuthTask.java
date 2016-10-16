package com.examples.rest;

import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

public class RestAuthTask extends AsyncTask<HttpUriRequest, Void, String> {

    public static final String HTTP_RESPONSE = "httpResponse";

    private static final String AUTH_USER = "user@mydomain.com";
    private static final String AUTH_PASS = "password";

    private Context mContext;
    private AbstractHttpClient mClient;
    private String mAction;

    public RestAuthTask(Context context, String action, boolean authenticate) {
        mContext = context;
        mAction = action;
        mClient = new DefaultHttpClient();
        if(authenticate) {
            UsernamePasswordCredentials creds = 
                    new UsernamePasswordCredentials(AUTH_USER, AUTH_PASS);
            mClient.getCredentialsProvider().setCredentials(AuthScope.ANY, creds);
        }
    }

    public RestAuthTask(Context context, String action, AbstractHttpClient client) {
        mContext = context;
        mAction = action;
        mClient = client;
    }

    @Override
    protected String doInBackground(HttpUriRequest... params) {
        try{
            HttpUriRequest request = params[0];
            HttpResponse serverResponse = mClient.execute(request);

            BasicResponseHandler handler = new BasicResponseHandler();
            String response = handler.handleResponse(serverResponse);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(String result) {
        Intent intent = new Intent(mAction);
        intent.putExtra(HTTP_RESPONSE, result);
        //Broadcast the completion
        mContext.sendBroadcast(intent);
    }

}
