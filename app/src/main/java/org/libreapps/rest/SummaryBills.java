package org.libreapps.rest;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import de.codecrafters.tableview.TableView;

public class SummaryBills extends AppCompatActivity {

    BillTableModel tableModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bill_summary);

        tableModel = new BillTableModel();
        String[][] bills = tableModel.getBills();

        setContentView(R.layout.bill_summary);
        LinearLayout m_elementsList = findViewById(R.id.list_elements);
        findViewById(R.id.last_elements).setVerticalScrollbarPosition(View.SCROLLBAR_POSITION_LEFT);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(60,25,5,25);

        for(int increment = bills.length-1; increment >= 0 && increment >= (bills.length - 10); increment--){
            String m_priceCompletion = "";
            for(int completedSize = 0; completedSize < (40 - bills[increment][3].length() - bills[increment][1].length()); completedSize++){
                m_priceCompletion += " ";
            }
            String m_data = "\t\t" + bills[increment][2].toString() +
                            "\n\n\t\t" + bills[increment][1].toString()  + m_priceCompletion + bills[increment][3].toString() + " €";

            TextView m_element = new TextView(this);
            m_element.setBackgroundResource(R.drawable.text_message_scroll);
            m_element.setText(m_data);
            m_element.setTextColor(Color.parseColor("#FFFFFF"));
            m_element.setTextSize(18);
            m_element.setLayoutParams(params);
            m_elementsList.addView(m_element);
        }


        Button summary = findViewById(R.id.manage);
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
