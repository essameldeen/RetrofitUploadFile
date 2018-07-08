package com.example.toshiba.retrofituploadfile;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.drm.DrmStore;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.toshiba.retrofituploadfile.server.BackGroundService;
import com.example.toshiba.retrofituploadfile.server.UserClient;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Url;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private  static  final  int permision_request=100;
    private    int pick_image =1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ruining background  thread
        Intent intent = new Intent(MainActivity.this, BackGroundService.class);
        startService(intent);
         //

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},permision_request);
        }
        Button upload = (Button)findViewById(R.id.upload);
        upload.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==pick_image && requestCode==RESULT_OK  && data!=null && data.getData()!=null){
            Uri uri = data.getData();
            uploadFile(uri);
        }
    }

    private void uploadFile(Uri uri) {
        final EditText descriptionEditText = (EditText)findViewById(R.id.description);

        // for first part description
        RequestBody description= RequestBody.create(MultipartBody.FORM,descriptionEditText.getText().toString());

        // geting the phycial file from the uri
       File originalFile =   new File(String.valueOf(uri));
        // for second part the file
        RequestBody filePart = RequestBody.create(MediaType.parse(getContentResolver().getType(uri)),
                originalFile
                );

         MultipartBody.Part file= MultipartBody.Part.createFormData("photo",originalFile.getName(),filePart);

        Retrofit.Builder builder = new Retrofit.Builder()
                //specific Url
                .baseUrl("http://10.2.2")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        UserClient userClient = retrofit.create(UserClient.class);

        Call<ResponseBody>  uploadFile=userClient.uploadImage(description,file);
        uploadFile.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(getApplicationContext(),"Done :)",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Error :(",Toast.LENGTH_LONG).show();
            }
        });





    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.upload){
            Intent  intent = new Intent();
            // to show only image
            intent.setType("image/*");
         intent.setAction(Intent.ACTION_GET_CONTENT);
         // Chooser For If need select multiple image
         startActivityForResult(Intent.createChooser(intent,"Select Picture"),pick_image);
        }
    }
}
