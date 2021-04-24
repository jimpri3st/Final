package com.example.themovieapp2.volley;



public interface NetworkResponse {

    interface Listener{
        void onResponse(Object result);
    }
    interface ErrorListener{
        void onError(NetworkException error);
    }
}
