package com.examples.rest;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.TextView;

public class SearchActivity extends Activity {

	private static final String SEARCH_ACTION = "com.examples.rest.SEARCH";
	private static final String SEARCH_URI = "http://search.yahooapis.com/WebSearchService/V1/webSearch";
	private static final String SEARCH_QUERY = "Android";
	
	private TextView result;
	private ProgressDialog progress;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Activity");
        result = new TextView(this);
        setContentView(result);
        
      //Create the search request
        try{
            HttpPost searchRequest = new HttpPost( new URI(SEARCH_URI) );
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            parameters.add(new BasicNameValuePair("appid","YahooDemo"));
            parameters.add(new BasicNameValuePair("query",SEARCH_QUERY));
            searchRequest.setEntity(new UrlEncodedFormEntity(parameters));
            
            RestTask task = new RestTask(this,SEARCH_ACTION);
            task.execute(searchRequest);
            //Display progress to the user
            progress = ProgressDialog.show(this, "Searching", "Waiting For Results...", true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
    @Override
    public void onResume() {
        super.onResume();
    	registerReceiver(receiver, new IntentFilter(SEARCH_ACTION));	
    }
    
    @Override
    public void onPause() {
        super.onPause();
    	unregisterReceiver(receiver);
    }
    
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //Clear progress indicator
            if(progress != null) {
                progress.dismiss();
            }
            String response = intent.getStringExtra(RestTask.HTTP_RESPONSE);
            //Process the response data (here we just display it)
            result.setText(response);
        }
    };
}