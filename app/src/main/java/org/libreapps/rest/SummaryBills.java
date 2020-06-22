package org.libreapps.rest;

        import androidx.appcompat.app.AppCompatActivity;

        import android.graphics.Color;
        import android.os.Bundle;

        import de.codecrafters.tableview.TableView;
        import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
        import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

public class SummaryBills extends AppCompatActivity {

    TableView<String[]>  tb;
    ProductTableModel tableModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_summary);

        //TABLEVIEW
        tableModel = new ProductTableModel();
        tb = (TableView<String[]>) findViewById(R.id.tableView);
        tb.setColumnCount(4);
        tb.setHeaderBackgroundColor(Color.parseColor("#03DAC5"));
        tb.setHeaderAdapter(new SimpleTableHeaderAdapter(this, tableModel.getProductHeaders()));
        tb.setDataAdapter(new SimpleTableDataAdapter(this, tableModel.getProducts()));
    }
}
