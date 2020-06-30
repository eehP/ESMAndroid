package org.libreapps.rest;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.listeners.TableDataClickListener;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;


public class SearchBills extends AppCompatActivity {

    private ConnectionRest connectionRest = null;
    TableView<String[]>  tb;
    ProductTableModel tableModel;

    SearchView mySearchView;
    ListView myList;
    String SearchS = null;

    ArrayList<String> list;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_bills);

        mySearchView = (SearchView)findViewById(R.id.searchView);
        myList = (ListView)findViewById(R.id.myList);

        list = new ArrayList<>();
        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,list);
        myList.setAdapter(adapter);
        mySearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {


            @Override
            public boolean onQueryTextSubmit(String s) {
                    return false;
                }
            @Override
            public boolean onQueryTextChange (String s){
                adapter.getFilter().filter(s);
                return false;
            }
        });

        //TABLEVIEW
        tableModel = new ProductTableModel();
        tb = (TableView<String[]>) findViewById(R.id.tableView);
        tb.setColumnCount(4);
        tb.setHeaderBackgroundColor(Color.parseColor("#3399FF"));
        tb.setHeaderAdapter(new SimpleTableHeaderAdapter(this, tableModel.getProductHeaders()));

        String[][] products = tableModel.getProducts();
        String[][] reverse_products = tableModel.getProducts();

        int m_reverseIncrement = 0;
        for(int increment = products.length-1; increment>=0; increment--){
            reverse_products[m_reverseIncrement] = products[increment];
            m_reverseIncrement++;
        }

        tb.setDataAdapter(new SimpleTableDataAdapter(this, reverse_products));


        Button buttonCancel = (Button) findViewById(R.id.button_cancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchBills.this, SummaryBills.class);
                startActivity(intent);
            }
        });
    }
}



