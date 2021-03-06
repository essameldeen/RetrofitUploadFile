package com.example.toshiba.retrofituploadfile.server;

import com.example.toshiba.retrofituploadfile.Model.User;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface UserClient {
    @GET("/users/{user}/repos")
    Call<List<User>> reposForUser(@Path("user") String userName);

    @Multipart
    @POST("upload")
    Call<ResponseBody> uploadImage(
            @Part("description") RequestBody description ,
            @Part MultipartBody.Part photo
    );

    @Multipart
    @POST("upload")
    Call<ResponseBody> uploadTowImage(
            @Part MultipartBody.Part photo1,
            @Part MultipartBody.Part photo2,
            @Part MultipartBody.Part photo3
    );
    @Multipart
    @POST("upload")
    Call<ResponseBody> uploadImages(
            @Part List<MultipartBody.Part> photo

    );
}
