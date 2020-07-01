package org.libreapps.rest;

import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
import org.libreapps.rest.ViewSearch.ApiClient;
import org.libreapps.rest.ViewSearch.ProductJSON;
import org.libreapps.rest.ViewSearch.RequestInterface;


public class SearchBills extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_bills);

        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        List<ProductJSON> modelClassList = new ArrayList<>();
        adapter = new Adapter(modelClassList);
        getAllProduct();
        recyclerView.setAdapter(adapter);
    }


    public void getAllProduct() {
        Call<List<ProductJSON>> productJSON = ApiClient.getRequestInterface().getProducts();
        productJSON.enqueue(new Callback<List<ProductJSON>>() {
            @Override
            public void onResponse(Call<List<ProductJSON>> call, Response<List<ProductJSON>> response) {

                if (response.isSuccessful()) {
                    List<ProductJSON> productJSONS = response.body();

                    adapter.setData(productJSONS);
                    recyclerView.setAdapter(adapter);
                }
            }
            public void onFailure(Call<List<ProductJSON>> call, Throwable t) {
                Log.e("failure", t.getLocalizedMessage());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

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





