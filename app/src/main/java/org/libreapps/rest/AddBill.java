package org.libreapps.rest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

public class AddBill extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_bill);

        final EditText nameEditTxt = (EditText) findViewById(R.id.nameEditTxt);
        final EditText nicknameEditTxt = (EditText) findViewById(R.id.nameEditTxt2);
        final EditText typeEditTxt = (EditText) findViewById(R.id.editTextDate);
        final EditText priceEditTxt = (EditText) findViewById(R.id.priceEditTxt);

        Button buttonOk = (Button) findViewById(R.id.button_ok);

        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ConnectionRest connectionRest = new ConnectionRest();
                    JSONObject product = new JSONObject();
                    product.put("name", nameEditTxt.getText().toString() + nicknameEditTxt.getText().toString());
                    product.put("type", typeEditTxt.getText().toString());
                    product.put("price", Double.parseDouble(priceEditTxt.getText().toString()));
                    connectionRest.setJsonObj(product);

                    connectionRest.execute("POST");

                    Intent intent = new Intent(AddBill.this, MainActivity.class);
                    startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        Button buttonCancel = (Button) findViewById(R.id.button_cancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddBill.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
