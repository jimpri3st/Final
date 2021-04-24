package com.example.themovieapp2.volley;


public class RequestResponseListener {

    public interface Listener{
        <T> void onResponse(T response);
    }


    public interface ErrorListener{
        void onError(NetworkException error);
    }

    public interface AuthErrorListener{
        void onAuthError();
    }
}
