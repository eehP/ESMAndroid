package org.libreapps.rest.ViewSearch;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static final String BASE_URL = "https://api.munier.me";

    private static Retrofit getRetrofit(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
    public static RequestInterface getRequestInterface(){
        RequestInterface requestInterface = getRetrofit().create(RequestInterface.class);
        return requestInterface;
    }
}
