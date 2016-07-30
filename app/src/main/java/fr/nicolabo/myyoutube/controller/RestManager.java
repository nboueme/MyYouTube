package fr.nicolabo.myyoutube.controller;

import android.content.Context;
import android.util.Log;

import java.io.IOException;

import fr.nicolabo.myyoutube.model.helper.Constants;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Retrofit;

/**
 * Created by nicolasboueme on 15/05/2016.
 */
public class RestManager {

    /*private VideoService videoService;

    public VideoService getVideoService() {
        if (videoService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.HTTP.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            videoService = retrofit.create(VideoService.class);
        }

        return videoService;
    }*/

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static Retrofit.Builder builder;

    private static AppPreferencesTool mAppPreferencesTool;

    /**
     * @description Create a service without the interceptor for the Token
     * @param serviceClass
     * @param <S>
     * @return
     */
    public static <S> S createServiceWithoutToken(Class<S> serviceClass) {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        // add logging as last interceptor
        httpClient.addInterceptor(logging);
        builder = new Retrofit.Builder()
                    .baseUrl(Constants.HTTP.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.client(httpClient.build()).build();
        return retrofit.create(serviceClass);
    }

    /**
     * @description Method for create a service with the interceptor Token and add a token before
     * each request to the service
     * @param serviceClass
     * @param <S>
     * @return
     */
    public static <S> S createServiceProvider(Class<S> serviceClass, final Context context) {

        builder  = new Retrofit.Builder()
                .baseUrl(Constants.HTTP.BASE_URL);
        mAppPreferencesTool = new AppPreferencesTool(context);

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        Log.e("Token", mAppPreferencesTool.getAccessToken());

        builder.addConverterFactory(GsonConverterFactory.create());

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                httpClient.authenticator(new CustomAuthenticator(context));
            }
        });
        thread.start();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();

                // Request customization: add request headers
                Request.Builder requestBuilder = original.newBuilder()
                        .header("Authorization", mAppPreferencesTool.getAccessToken())
                        .method(original.method(), original.body());

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });


        // add logging as last interceptor
        httpClient.addInterceptor(logging);

        OkHttpClient client = httpClient.build();
        Retrofit retrofit = builder.client(client).build();
        return retrofit.create(serviceClass);
    }
}
