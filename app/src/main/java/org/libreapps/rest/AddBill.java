package org.libreapps.rest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class AddBill extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_bill);
        final int id = getIntent().getIntExtra("id",0);
        String name = getIntent().getStringExtra("name");
        String type = getIntent().getStringExtra("type");
        Double price = getIntent().getDoubleExtra("price",1.0);

        final EditText nameEditTxt = (EditText) findViewById(R.id.nameEditTxt);
        final EditText nicknameEditTxt = (EditText) findViewById(R.id.nameEditTxt2);
        final EditText typeEditTxt = (EditText) findViewById(R.id.editTextDate);
        final EditText priceEditTxt = (EditText) findViewById(R.id.priceEditTxt);

        Button buttonOk = (Button) findViewById(R.id.button_ok);
        Button buttonCancel = (Button) findViewById(R.id.button_cancel);
        Button buttonDelete = (Button) findViewById(R.id.button_delete);

        if(id != 0){

            String nameSplit = name;
            String arr[] = nameSplit.split(" ", 2);
            String lastName = arr[0];
            String firstName = arr[1];

            nameEditTxt.setText(lastName);
            nicknameEditTxt.setText(firstName);
            typeEditTxt.setText(type);
            priceEditTxt.setText("" + price);
            buttonOk.setText("Modifier");
            buttonDelete.setText("Supprimer");
        }

        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ConnectionRest connectionRest = new ConnectionRest();
                    JSONObject product = new JSONObject();
                    if(id != 0){
                        product.put("id", id);
                    }
                    product.put("name", nameEditTxt.getText().toString() + " " + nicknameEditTxt.getText().toString());
                    product.put("type", typeEditTxt.getText().toString());
                    product.put("price", Double.parseDouble(priceEditTxt.getText().toString()));
                    connectionRest.setJsonObj(product);

                    if(id != 0){
                        connectionRest.execute("PUT");
                    }
                    else {
                        connectionRest.execute("POST");
                    }

                    Intent intent = new Intent(AddBill.this, SearchBills.class);
                    startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddBill.this, MainActivity.class);
                startActivity(intent);
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(id != 0) {
                    try {
                        ConnectionRest connectionRest = new ConnectionRest();
                        JSONObject product = new JSONObject();
                        product.put("id", id);
                        connectionRest.setJsonObj(product);
                        connectionRest.execute("DELETE");
                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Intent intent = new Intent(AddBill.this, SearchBills.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(AddBill.this, AddBill.class);
                    startActivity(intent);
                }
            }
        });
    }
}
