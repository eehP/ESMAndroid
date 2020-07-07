package org.libreapps.rest;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.SearchView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import org.jetbrains.annotations.NotNull;
import org.libreapps.rest.ViewSearch.Adapter;
import org.libreapps.rest.ViewSearch.ApiClient;
import org.libreapps.rest.ViewSearch.BillJSON;
import org.libreapps.rest.ViewSearch.RecyclerViewClickInterface;
import org.libreapps.rest.ViewSearch.RequestInterface;

public class SearchBills extends AppCompatActivity implements RecyclerViewClickInterface {

    private String token = null;
    private RecyclerView recyclerView;
    private Adapter adapter;
    private List<BillJSON> bills;
    private String m_sortStatus = "";
    private SortByColumn m_sort = new SortByColumn();
    private SortByColumn m_sorter = new SortByColumn();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_bills);
        token = getIntent().getStringExtra("token");
        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(layoutManager);

        RequestInterface service = ApiClient.ApiService(RequestInterface.class, token);
        final Call<List<BillJSON>> billJSON = service.getBills();
        billJSON.enqueue(new Callback<List<BillJSON>>() {
            @Override
            public void onResponse(@NotNull Call<List<BillJSON>> call, Response<List<BillJSON>> response) {

                if (response.isSuccessful()) {

                    List<BillJSON> billJSONS = response.body();
                    adapter = new Adapter(billJSONS,SearchBills.this);
                    bills = billJSONS;
                    List<BillJSON> m_bills;
                    m_bills = m_sorter.sortTable(bills, m_sortStatus);
                    adapter.setData(m_bills);
                    adapter.notifyDataSetChanged();
                    recyclerView.setAdapter(adapter);

                }
            }
            public void onFailure(@NotNull Call<List<BillJSON>> call, @NotNull Throwable t) {
                // No action
            }
        });

        Button buttonCancel = (Button) findViewById(R.id.button_cancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchBills.this, SummaryBills.class);
                intent.putExtra("token", token);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);

        MenuItem sortItem = menu.findItem(R.id.action_sort);

        sortItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(m_sortStatus != "Name"){
                    m_sortStatus = "Name";
                    List<BillJSON> m_bills = adapter.get_actualList();
                    m_bills = m_sorter.sortTable(m_bills, m_sortStatus);
                    adapter.setData(m_bills);
                    adapter.notifyDataSetChanged();
                    recyclerView.setAdapter(adapter);
                }
                return false;
            }
        });

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
                m_sortStatus = "";
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;

    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(SearchBills.this, AddBill.class);
        intent.putExtra("id", Integer.parseInt(bills.get(position).getId()));
        intent.putExtra("name", bills.get(position).getName());
        intent.putExtra("date", bills.get(position).getDate());
        intent.putExtra("type", bills.get(position).getType());
        intent.putExtra("price", Double.parseDouble(bills.get(position).getPrice()));
        intent.putExtra("token", token);
        startActivity(intent);
    }
}
