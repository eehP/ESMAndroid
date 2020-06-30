package org.libreapps.rest;

import android.os.Bundle;

import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import org.libreapps.rest.ViewSearch.ProductJSON;
import org.libreapps.rest.ViewSearch.RequestInterface;


public class SearchBills extends AppCompatActivity {

    public static final String BASE_URL = "https://api.munier.me";
    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_bills);
        textViewResult = findViewById(R.id.text_view_result);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface requestInterface = retrofit.create(RequestInterface.class);
        Call<List<ProductJSON>> call = requestInterface.getProducts();
        call.enqueue(new Callback<List<ProductJSON>>() {
            @Override
            public void onResponse(Call<List<ProductJSON>> call, Response<List<ProductJSON>> response) {
                if (!response.isSuccessful()) {
                    textViewResult.setText("Code: " + response.code());
                    return;
                }
                List<ProductJSON> productJSONS = response.body();
                for (ProductJSON productJSON : productJSONS) {
                    String content = "";
                    content += "ID: " + productJSON.getId() + "\n";
                    content += "User ID: " + productJSON.getName() + "\n";
                    content += "Title: " + productJSON.getType() + "\n";
                    content += "Text: " + productJSON.getPrice() + "\n\n";
                    textViewResult.append(content);
                }
            }
            @Override
            public void onFailure(Call<List<ProductJSON>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }
}




