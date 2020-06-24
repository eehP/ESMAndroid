package org.libreapps.rest;

        import androidx.appcompat.app.AppCompatActivity;

        import android.content.Intent;
        import android.graphics.Color;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;

        import de.codecrafters.tableview.TableView;
        import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
        import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

public class SummaryBills extends AppCompatActivity {

    TableView<String[]>  tb;
    ProductTableModel tableModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bill_summary);

        //TABLEVIEW
        tableModel = new ProductTableModel();
        tb = (TableView<String[]>) findViewById(R.id.tableView);
        tb.setColumnCount(4);
        tb.setHeaderBackgroundColor(Color.parseColor("#03DAC5"));
        tb.setHeaderAdapter(new SimpleTableHeaderAdapter(this, tableModel.getProductHeaders()));
        tb.setDataAdapter(new SimpleTableDataAdapter(this, tableModel.getProducts()));

        Button summary = findViewById(R.id.search);
        summary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SummaryBills.this, SearchBills.class);
                startActivity(intent);
            }
        });

        Button buttonCancel = (Button) findViewById(R.id.button_cancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SummaryBills.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
