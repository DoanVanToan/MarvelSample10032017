package com.framgia.toandoan.apiservice;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.framgia.toandoan.apiservice.APIUtils.BASE_URL;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by framgia on 10/03/2017.
 */
public class ServiceGenerator {
    private static final long TIME_OUT_SERVER = 30000;
    private static OkHttpClient.Builder sHttpClient;
    private static Retrofit.Builder sBuilder =
        new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    public static <S> S createService(Class<S> serviceClass) {
        if (sHttpClient == null) {
            sHttpClient = new OkHttpClient.Builder();
            sHttpClient.addInterceptor(new Interceptor() {
                @Override
                public okhttp3.Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();
                    Request.Builder requestBuilder = original.newBuilder()
                        .header("Accept", "application/json")
                        .method(original.method(), original.body());
                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });
        }
        sHttpClient.connectTimeout(TIME_OUT_SERVER, SECONDS);
        OkHttpClient client = sHttpClient.build();
        Retrofit retrofit = sBuilder.client(client).build();
        return retrofit.create(serviceClass);
    }
}
