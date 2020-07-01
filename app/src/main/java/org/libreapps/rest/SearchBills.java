package org.libreapps.rest;

import android.os.Bundle;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import org.libreapps.rest.ViewSearch.Adapter;
import org.libreapps.rest.ViewSearch.ProductJSON;
import org.libreapps.rest.ViewSearch.RequestInterface;


public class SearchBills extends AppCompatActivity {

    public static final String BASE_URL = "https://api.munier.me";
    private RecyclerView recyclerView;
    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_bills);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface requestInterface = retrofit.create(RequestInterface.class);
        Call<List<ProductJSON>> call = requestInterface.getProducts();
        call.enqueue(new Callback<List<ProductJSON>>() {
            @Override
            public void onResponse(Call<List<ProductJSON>> call, Response<List<ProductJSON>> response) {
                List<ProductJSON> productJSONS = response.body();
                for (ProductJSON productJSON : productJSONS) {
                    String content = "";
                    content += "ID: " + productJSON.getId() + "\n";
                    content += "User ID: " + productJSON.getName() + "\n";
                    content += "Title: " + productJSON.getType() + "\n";
                    content += "Text: " + productJSON.getPrice() + "\n\n";
                }
            }
            public void onFailure (Call < List < ProductJSON >> call, Throwable t){
                System.out.println(t);
            }
        });
        List<ProductJSON> modelClassList = new ArrayList<>();
        modelClassList.add(new ProductJSON("1", "test", "test", "test1"));
        modelClassList.add(new ProductJSON("2", "test", "test", "test2"));
        modelClassList.add(new ProductJSON("3", "test", "test", "test3"));
        modelClassList.add(new ProductJSON("4", "test", "test", "test4"));
        modelClassList.add(new ProductJSON("5", "test", "test", "test5"));
        modelClassList.add(new ProductJSON("6", "test", "test", "test6"));
        modelClassList.add(new ProductJSON("7", "test", "test", "test7"));
        modelClassList.add(new ProductJSON("8", "test", "test", "test8"));

        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);


        adapter = new Adapter(modelClassList);
        recyclerView.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }
}





