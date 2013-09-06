package com.technotalkative.volleyexamplesimple;

import org.json.JSONObject;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ShareActionProvider;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends Activity {

	private TextView txtDisplay;
	private NetworkImageView imageView;
	private ShareActionProvider shareActionProvider;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		txtDisplay = (TextView) findViewById(R.id.txtDisplay);

		RequestQueue queue = Volley.newRequestQueue(this);
		// String url =
		// "https://www.googleapis.com/customsearch/v1?key=AIzaSyBmSXUzVZBKQv9FJkTpZXn0dObKgEQOIFU&cx=014099860786446192319:t5mr0xnusiy&q=AndroidDev&alt=json&searchType=image";
		String url = "http://headers.jsontest.com/";

		JsonObjectRequest jsObjRequest = new JsonObjectRequest(
				Request.Method.GET, url, null,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						// TODO Auto-generated method stub
						txtDisplay.setText("Response => " + response.toString());
						findViewById(R.id.progressBar1)
								.setVisibility(View.GONE);
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
						Log.e("VOLLEY ERROR", "sdf");
						Log.e("VOLLEY ERROR", error.getMessage());
					}
				});

		queue.add(jsObjRequest);

		url = "http://www.google.com/";

		StringRequest request = new StringRequest(Request.Method.GET, url,
				new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {
						Log.i("String response", response);
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						Log.i("String response error", error.getMessage());
					}
				});

		queue.add(request);

		ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		int memoryAvailableToApplicationInMb = activityManager.getMemoryClass();
		int memoryAvailableInBytes = memoryAvailableToApplicationInMb * 1024 * 1024;
		int cacheShareInBytes = memoryAvailableInBytes / 12;

		ImageLruCache imageLruCache = new ImageLruCache(cacheShareInBytes);

		ImageLoader imageLoader = new ImageLoader(queue, imageLruCache);

//		String imageUrl = "http://cdn.crackberry.com/sites/crackberry.com/files/styles/large/public/topic_images/2013/ANDROID.png";
		String imageUrl = "http://www.yapwallpapers.com/wallpapers/2013/05/Android-Vs-Apple-Computer-2048x2048.jpg";

		imageView = (NetworkImageView) findViewById(R.id.image_view);
		imageView.setImageUrl(imageUrl, imageLoader);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		
	    // Locate MenuItem with ShareActionProvider
	    MenuItem item = menu.findItem(R.id.menu_item_share);

	    // Fetch and store ShareActionProvider
	    shareActionProvider = (ShareActionProvider) item.getActionProvider();
	    Intent shareIntent = new Intent();
	    shareIntent.setAction(Intent.ACTION_SEND);
	    shareIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
	    shareIntent.setType("text/plain");
		shareActionProvider.setShareIntent(shareIntent );

	    // Return true to display menu
	    return true;
	}

}
