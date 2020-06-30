package org.libreapps.rest;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.listeners.TableDataClickListener;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

public class SearchBills extends AppCompatActivity {

    TableView<String[]>  tb;
    ProductTableModel tableModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_bills);

        //TABLEVIEW
        tableModel = new ProductTableModel();
        tb = (TableView<String[]>) findViewById(R.id.tableView);
        tb.setColumnCount(4);
        tb.setHeaderBackgroundColor(Color.parseColor("#03DAC5"));
        tb.setHeaderAdapter(new SimpleTableHeaderAdapter(this, tableModel.getProductHeaders()));
        tb.setDataAdapter(new SimpleTableDataAdapter(this, tableModel.getProducts()));

        //TABLE CLICK
        tb.addDataClickListener(new TableDataClickListener() {
            @Override
            public void onDataClicked(int rowIndex, Object clickedData) {
                Intent intent = new Intent(SearchBills.this, AddBill.class);
                intent.putExtra("id", tableModel.get(rowIndex).getId());
                intent.putExtra("type", ((String[])clickedData)[1]);
                intent.putExtra("name", ((String[])clickedData)[2]);
                intent.putExtra("price", Double.parseDouble(((String[]) clickedData)[3]));
                startActivity(intent);
            }
        });
    }
}
