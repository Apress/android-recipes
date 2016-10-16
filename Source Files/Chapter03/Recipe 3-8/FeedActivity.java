package com.examples.rest;

import java.io.StringReader;
import java.net.URI;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.http.client.methods.HttpGet;
import org.xml.sax.InputSource;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.examples.rest.RSSHandler.NewsItem;

public class FeedActivity extends Activity {

	private static final String FEED_ACTION = "com.examples.rest.FEED";
	private static final String FEED_URI = "http://news.google.com/?output=rss";
	
	private ListView list;
	private ArrayAdapter<NewsItem> adapter;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Activity");
        
        list = new ListView(this);
        adapter = new ArrayAdapter<NewsItem>(this, android.R.layout.simple_list_item_1, android.R.id.text1);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                NewsItem item = adapter.getItem(position);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(item.link));
                startActivity(intent);
            }
        });
        
        setContentView(list);
    }
	
    @Override
    public void onResume() {
        super.onResume();
    	registerReceiver(receiver, new IntentFilter(FEED_ACTION));
    	//Retrieve the RSS feed
        try{
            HttpGet feedRequest = new HttpGet( new URI(FEED_URI) );
            RestTask task = new RestTask(this,FEED_ACTION);
            task.execute(feedRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void onPause() {
        super.onPause();
    	unregisterReceiver(receiver);
    }
    
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String response = intent.getStringExtra(RestTask.HTTP_RESPONSE);
            //Process the response data
            try {
                SAXParserFactory factory = SAXParserFactory.newInstance();
                SAXParser p = factory.newSAXParser();
                RSSHandler parser = new RSSHandler();
                p.parse(new InputSource(new StringReader(response)), parser);
                
                adapter.clear();
                for(NewsItem item : parser.getParsedItems()) {
                    adapter.add(item);
                }
                adapter.notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
}