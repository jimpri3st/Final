package com.example.themovieapp2.volley;

import android.content.Context;

import java.util.Map;


public class VolleyRequest {

    public int method;
    public String url;
    public String data;
    public String contentType;
    public Context context;
    public String tag;
    public Map<String, String> authHeaders;
}
