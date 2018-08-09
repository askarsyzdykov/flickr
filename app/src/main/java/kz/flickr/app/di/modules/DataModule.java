package kz.flickr.app.di.modules;

import android.support.annotation.NonNull;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import kz.flickr.app.data.PhotosRepositoryImpl;
import kz.flickr.app.data.network.ErrorHandler;
import kz.flickr.app.data.network.NetworkChecker;
import kz.flickr.app.data.network.RestApiService;
import kz.flickr.app.data.network.interceptors.NetworkCheckInterceptor;
import kz.flickr.app.di.qualifiers.OkHttpInterceptors;
import kz.flickr.app.di.qualifiers.OkHttpNetworkInterceptors;
import kz.flickr.app.domain.repositories.PhotosRepository;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class DataModule {

    private final String baseUrl;

    public DataModule(String baseUrl) {
        this.baseUrl = baseUrl;
    }


    @Provides
    @Singleton
    PhotosRepository provideLoginRepository(PhotosRepositoryImpl repository) {
        return repository;
    }

    @Provides
    @NonNull
    @Singleton
    OkHttpClient provideOkHttpClient(NetworkChecker networkChecker,
                                     @OkHttpInterceptors @NonNull List<Interceptor> interceptors,
                                     @OkHttpNetworkInterceptors @NonNull List<Interceptor> networkInterceptors) {
        final OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
        okHttpBuilder.addInterceptor(new NetworkCheckInterceptor(networkChecker));

        for (Interceptor interceptor : interceptors) {
            okHttpBuilder.addInterceptor(interceptor);
        }

        for (Interceptor networkInterceptor : networkInterceptors) {
            okHttpBuilder.addNetworkInterceptor(networkInterceptor);
        }

        okHttpBuilder.addInterceptor(chain -> {
            Request request = chain.request();
            HttpUrl url = request.url().newBuilder()
                    .addQueryParameter("api_key", "e7a5c12916cb962ad10d9b0ad83472d4")
                    .build();
            request = request.newBuilder()
                    .url(url)
                    .build();
            return chain.proceed(request);
        });

        return okHttpBuilder.build();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
        return new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build();
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new Gson();
    }

    @Provides
    @Singleton
    ErrorHandler provideErrorHandler(Gson gson) {
        return new ErrorHandler(gson);
    }

    @Provides
    @Singleton
    RestApiService provideApi(Retrofit retrofit) {
        return retrofit.create(RestApiService.class);
    }

}
