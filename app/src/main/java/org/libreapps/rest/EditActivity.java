package org.libreapps.rest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

public class EditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        final EditText nameEditTxt = (EditText) findViewById(R.id.nameEditTxt);
        final EditText typeEditTxt = (EditText) findViewById(R.id.typeEditTxt);
        final EditText priceEditTxt = (EditText) findViewById(R.id.priceEditTxt);

        Button buttonOk = (Button) findViewById(R.id.button_ok);

        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ConnectionRest connectionRest = new ConnectionRest();
                    JSONObject product = new JSONObject();
                    product.put("name", nameEditTxt.getText().toString());
                    product.put("type", typeEditTxt.getText().toString());
                    product.put("price", Double.parseDouble(priceEditTxt.getText().toString()));
                    connectionRest.setJsonObj(product);

                    connectionRest.execute("POST");

                    Intent intent = new Intent(EditActivity.this, MainActivity.class);
                    startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
