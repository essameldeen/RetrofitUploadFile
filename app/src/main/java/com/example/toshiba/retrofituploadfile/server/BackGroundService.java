package com.example.toshiba.retrofituploadfile.server;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.example.toshiba.retrofituploadfile.Model.User;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BackGroundService extends IntentService {
    public BackGroundService() {
        super("BackGroundService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {


        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        UserClient gitHubClient =  retrofit.create(UserClient.class);
        Call<List<User>> call =gitHubClient.reposForUser("fs-opensource");

        try{
            Response<List<User>> listName = call.execute();
            Toast.makeText(getApplicationContext(),"done",Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
