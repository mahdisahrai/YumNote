package com.mahdi.yumnote.rx.main.fragments.profile;



import android.graphics.Bitmap;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.bumptech.glide.Glide;
import com.mahdi.yumnote.R;
import com.mahdi.yumnote.model.retrofit.api1.ProfileImageServer;
import com.mahdi.yumnote.model.retrofit.api1.photoServer;
import com.mahdi.yumnote.networking.retrofit.api.ApiServices1;
import com.mahdi.yumnote.networking.retrofit.client.RetrofitClient;
import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import javax.inject.Inject;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;




public class ImageUploadRx {


    private ImageView profileDisplay;


    @Inject
    public ImageUploadRx() {
    }


    public void Uploading(Bitmap bitmap, String user, String pass) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);

        String _name = String.valueOf(Calendar.getInstance().getTimeInMillis());
        String _image = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);

        ApiServices1 apiServices = RetrofitClient.getApiServices();
        Observable<ProfileImageServer> upload = apiServices.SendImageProfile(_name, _image, user, pass);
        upload.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ProfileImageServer>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ProfileImageServer imageServer) {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }





    public void Fetching(View view , String user , String pass)
    {

        profileDisplay = view.findViewById(R.id.default_profile);


        ApiServices1 apiServices = RetrofitClient.getApiServices();
        Observable<photoServer> observable = apiServices.ReceivedProfilephoto(user, pass);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<photoServer>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull photoServer photoServer) {

                        if (photoServer.getImageimg().equals(""))
                        {
                            profileDisplay.setImageResource(R.drawable.default_profile);
                        }
                        else {

                            Glide.with(view.getContext())
                                    .load("http://10.0.2.2/yumnote/" + photoServer.getImageimg())
                                    .into(profileDisplay);
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Toast.makeText(view.getContext() , "there is not connection. please connect to a network", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }





}


