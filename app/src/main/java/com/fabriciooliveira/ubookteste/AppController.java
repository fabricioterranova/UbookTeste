package com.fabriciooliveira.ubookteste;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.fabriciooliveira.ubookteste.util.BitMapCache;

/**
 * Created by fabriciooliveira on 3/12/15.
 */
public class AppController extends Application {

    private static final String TAG = AppController.class.getSimpleName();

    private static AppController appController;

    private RequestQueue requestQueue;

    private ImageLoader imageLoader;

    @Override
    public void onCreate(){
        super.onCreate();
        appController = this;
    }

    public static synchronized AppController getInstance(){
        return appController;
    }

    public RequestQueue getRequestQueue(){
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return requestQueue;
    }

    public ImageLoader getImageLoader(){
        getRequestQueue();

        if(imageLoader == null){
            imageLoader = new ImageLoader(requestQueue, new BitMapCache());
        }

        return imageLoader;
    }

    public <T> void addToRequestQueue(Request<T> request, String tag){
        request.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(request);
    }

    public <T> void addToRequestQueue(Request<T> request){
        request.setTag(TAG);
        getRequestQueue().add(request);
    }

}
