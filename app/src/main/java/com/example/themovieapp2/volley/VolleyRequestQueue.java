package com.example.themovieapp2.volley;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleyRequestQueue {

    private static final String TAG = "VolleyRequestQueue";

    private static VolleyRequestQueue mInstance;
    private RequestQueue mRequestQueue;
    private static Context mCtx;

    private VolleyRequestQueue(Context context){
        mCtx = context;
        mRequestQueue = getRequestQueue();
    }

    public static synchronized VolleyRequestQueue getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new VolleyRequestQueue(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());

            // Testing new Request Queue with 'body' on DELETE method
            //mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext(), new CustomHurlStack());

        }
        return mRequestQueue;
    }

    public void addToRequestQueue(Request req) {
        getRequestQueue().add(req);
    }
}
