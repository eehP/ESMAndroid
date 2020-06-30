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
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.listeners.TableDataClickListener;
import de.codecrafters.tableview.listeners.TableHeaderClickListener;
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
        final String[][] reverse_products = tableModel.getProducts();

        int m_reverseIncrement = 0;
        for(int increment = products.length-1; increment>=0; increment--){
            reverse_products[m_reverseIncrement] = products[increment];
            m_reverseIncrement++;
        }

        tb.setDataAdapter(new SimpleTableDataAdapter(this, reverse_products));

        //TABLE CHANGE
        tb.setColumnWeight(0, 0);

        tb.setColumnWeight(1, 30);
        tb.setColumnWeight(2, 50);
        tb.setColumnWeight(3, 20);

        tb.addHeaderClickListener(new TableHeaderClickListener() {
            @Override
            public void onHeaderClicked(int columnIndex) {
                SortByColumn tableSorting = new SortByColumn();
                System.out.println(columnIndex);
                System.out.println(reverse_products.length);
                String[][] m_resultProduct;
                switch(columnIndex){
                    case 1:
                        String[][] dateSort_products = tableSorting.myDateSort(reverse_products, 1);
                        for(String[] line : dateSort_products ) {
                            System.out.println(Arrays.toString(line));
                        }
                        m_resultProduct = dateSort_products;
                        break;

                    case 2:
                        String[][] stringSort_products = tableSorting.myStringSort(reverse_products, 2);
                        for(String[] line : stringSort_products ) {
                            System.out.println(Arrays.toString(line));
                        }
                        m_resultProduct = stringSort_products;
                        break;

                    case 3:
                        String[][] floatSort_products = tableSorting.myFloatSort(reverse_products, 3);
                        for(String[] line : floatSort_products ) {
                            System.out.println(Arrays.toString(line));
                        }
                        m_resultProduct = floatSort_products;
                        break;

                    default:
                        m_resultProduct = reverse_products;
                        break;
                }

                TableView m_table = (TableView<String[]>) findViewById(R.id.tableView);
                int childCount = m_table.getChildCount();
                if (childCount > 1) {
                    m_table.removeViews(1, childCount - 1);
                }
                m_table.setDataAdapter(new SimpleTableDataAdapter(SearchBills.this, reverse_products));//CURRENT
            }
        });

        //TABLE CLICK
        tb.addDataClickListener(new TableDataClickListener() {
            @Override
            public void onDataClicked(int rowIndex, Object clickedData) {
                Intent intent = new Intent(SearchBills.this, AddBill.class);
                // intent.putExtra("id", tableModel.get(rowIndex).getId());
                intent.putExtra("id", Integer.parseInt(((String[]) clickedData)[0]));
                intent.putExtra("type", ((String[])clickedData)[1]);
                intent.putExtra("name", ((String[])clickedData)[2]);
                intent.putExtra("price", Double.parseDouble(((String[]) clickedData)[3]));
                startActivity(intent);
            }
        });

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


