package com.example.themovieapp2;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Search extends Fragment {
    String category = "search";
    String api_key = "b7e59f58c0bccf15f6d4dd07f31176ec";
    private static String BASE_URL = "https://api.themoviedb.org";
    List<MovieResults.Result> movieList;
    RecyclerView recyclerView;
    private static String Now_Playing = "https://api.themoviedb.org/3/movie/now_playing?api_key=b7e59f58c0bccf15f6d4dd07f31176ec";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_movies, container, false);

        movieList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.search_rec);

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL).build();
        InterfaceApi myInterface = retrofit.create(InterfaceApi.class);
        Call<MovieResults> call = myInterface.getMovies(category, api_key);
        call.enqueue(new Callback<MovieResults>() {
            @Override
            public void onResponse(Call<MovieResults> call, Response<MovieResults> response) {
                MovieResults results = response.body();
                List<MovieResults.Result> listOfMovies = results.getResults();

                for (int i = 0; i < listOfMovies.size(); i++) {
                    MovieResults.Result model = new MovieResults.Result();
                    MovieResults.Result nowPlaying = listOfMovies.get(i);
                    model.setTitle(nowPlaying.getTitle());
                    model.setOverview(nowPlaying.getOverview());
                    model.setPosterPath(nowPlaying.getPosterPath());
                    model.setVoteAverage(nowPlaying.getVoteAverage());
                    movieList.add(model);

                }
                MovieAdapter movieAdapter = new MovieAdapter(getContext(), movieList);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                recyclerView.setAdapter(movieAdapter);
            }

            @Override
            public void onFailure(Call<MovieResults> call, Throwable t) {

            }
        });

//        NowPlaying.GetData getData = new NowPlaying.GetData();
//        getData.execute();
        return view;
    }
}
//    public  class GetData extends AsyncTask<String, String, String> {
//
//        @Override
//        protected String doInBackground(String... strings) {
//
//            String current = "";
//
//            try{
//                URL url;
//                HttpURLConnection urlConnection = null;
//
//                try{
//                    url = new URL(Now_Playing);
//                    urlConnection = (HttpURLConnection) url.openConnection();
//
//                    InputStream is = urlConnection.getInputStream();
//                    InputStreamReader isr = new InputStreamReader(is);
//                    int data = isr.read();
//                    while(data != -1){
//                        current += (char) data;
//                        data = isr.read();
//                    }
//                    return current;
//
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }finally {
//                    if(urlConnection != null){
//                        urlConnection.disconnect();;
//                    }
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//
//            return current;
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            try {
//                JSONObject jsonObject = new JSONObject(s);
//                JSONArray jsonArray = jsonObject.getJSONArray("results");
//
//                for(int i = 0; i<jsonArray.length(); i++){
//                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
//
//                    MovieResults.Result model = new MovieResults.Result();
//                    model.setTitle(jsonObject1.getString("title"));
//                    model.setOverview(jsonObject1.getString("overview"));
//                    model.setPosterPath(jsonObject1.getString("poster_path"));
//                    model.setVoteAverage(jsonObject1.getDouble("vote_average"));
//
//
//                    movieList.add(model);
//                }
//
//
//
//
//
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            PutDataIntoRecyclerView(movieList);
//
//        }
//    }
//
//    private void PutDataIntoRecyclerView(List<MovieResults.Result> movieList){
//
//    }
//
//}



//public class Search extends Fragment {
//    List<MovieModel> movieList;
//    RecyclerView recyclerView;
//    EditText searchText;
//
//    private String Search = "https://api.themoviedb.org/3/search/movie?api_key=b7e59f58c0bccf15f6d4dd07f31176ec";
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.search_movies,container,false);
//
//        movieList = new ArrayList<>();
//        recyclerView = view.findViewById(R.id.search_rec);
//        searchText = (EditText) view.findViewById(R.id.search_text);
//        searchText.addTextChangedListener(new TextWatcher()
//        {
//            @Override
//            public void afterTextChanged(Editable mEdit)
//            {
//            }
//            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
//
//            public void onTextChanged(CharSequence s, int start, int before, int count){}
//        });
//
//
//        Search.GetData3 getData = new Search.GetData3();
//        getData.execute();
//
//        return view;
//
//
//    }
//
//    public  class GetData3 extends AsyncTask<String, String, String> {
//      String query = searchText.toString();
//        @Override
//        protected String doInBackground(String... strings) {
//
//            String current = "";
//
//            try{
//                URL url;
//                HttpURLConnection urlConnection = null;
//
//                try{
//                    url = new URL(Search+"&query"+query);
//                    urlConnection = (HttpURLConnection) url.openConnection();
//
//                    InputStream is = urlConnection.getInputStream();
//                    InputStreamReader isr = new InputStreamReader(is);
//                    int data = isr.read();
//                    while(data != -1){
//                        current += (char) data;
//                        data = isr.read();
//                    }
//                    return current;
//
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }finally {
//                    if(urlConnection != null){
//                        urlConnection.disconnect();;
//                    }
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//
//            return current;
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            try {
//                JSONObject jsonObject = new JSONObject(s);
//                JSONArray jsonArray = jsonObject.getJSONArray("results");
//
//                for(int i = 0; i<jsonArray.length(); i++){
//                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
//
//                    MovieModel model = new MovieModel();
//                    model.setId(jsonObject1.getString("title"));
//                    model.setName(jsonObject1.getString("overview"));
//                    model.setImg(jsonObject1.getString("poster_path"));
//                    model.setRating(jsonObject1.getString("vote_average"));
//
//
//                    movieList.add(model);
//                }
//
//
//
//
//
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            PutDataIntoRecyclerView(movieList);
//
//        }
//    }
//
//    private void PutDataIntoRecyclerView(List<MovieModel> movieList){
//        MovieAdapter movieAdapter = new MovieAdapter(getContext(), movieList);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//
//        recyclerView.setAdapter(movieAdapter);
//    }
//
//
//}