package com.example.toshiba.retrofituploadfile.server;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UserClient {

    @Multipart
    @POST("upload")
    Call<ResponseBody> uploadImage(
            @Part("description") RequestBody description ,
            @Part MultipartBody.Part photo
    );

}
