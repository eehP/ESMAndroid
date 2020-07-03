package org.libreapps.rest;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
                    JSONObject bill = new JSONObject();
                    if(id != 0){
                        bill.put("id", id);
                    }

                    regexControl(nameEditTxt.getText().toString(), "Name");
                    regexControl(nicknameEditTxt.getText().toString(), "Nickname");
                    regexControl(typeEditTxt.getText().toString(), "Date");
                    regexControl(priceEditTxt.getText().toString(), "Price");

                    bill.put("name", nameEditTxt.getText().toString() + " " + nicknameEditTxt.getText().toString());
                    bill.put("type", typeEditTxt.getText().toString());
                    bill.put("price", Double.parseDouble(priceEditTxt.getText().toString()));
                    connectionRest.setJsonObj(bill);

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
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (id == 0) {
                    Intent intent = new Intent(AddBill.this, MainActivity.class);
                    startActivity(intent);
                }
                Intent intent = new Intent(AddBill.this, SearchBills.class);
                startActivity(intent);
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(id != 0) {
                    try {
                        ConnectionRest connectionRest = new ConnectionRest();
                        JSONObject bill = new JSONObject();
                        bill.put("id", id);
                        connectionRest.setJsonObj(bill);
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

    private boolean regexControl(String m_element, String m_type) throws IllegalAccessException{
        switch(m_type){
            case "Name":
            case "Nickname":
                if(m_element.matches("[a-zA-ZÉ]{1}[a-zA-Z_-éèê]{0,23}[a-zA-Zéèê]{0,1}")){
                    return true;
                }else{
                    returnAlert("Name Error", "Le Nom/Prénom que vous avez saisi n'est pas valide, caractère spécial autorisé: '-'.");
                    throw new IllegalAccessException("Name Error");
                }

            case "Date":
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

                sdf.setLenient(false);
                try {
                    String[] m_splitElement = m_element.split("/");
                    if(m_splitElement[1].length() != 2 || m_splitElement[2].length() != 4){
                        returnAlert("Date Error", "La date que vous avez saisie n'est pas valide.");
                        throw new IllegalAccessException("Date Error");
                    }
                    sdf.parse(m_element);
                    return true;
                }catch (ParseException e) {
                    returnAlert("Date Error", "La date que vous avez saisie n'est pas valide.");
                    throw new IllegalAccessException("Date Error");
                } catch (ArrayIndexOutOfBoundsException e) {
                    returnAlert("Date Error", "La date que vous avez saisie n'est pas valide.");
                    throw new IllegalAccessException("Date Error");
                }

            case "Price":
                String[] m_priceElements = m_element.split("\\.");
                if(m_priceElements.length == 2 &&
                        (m_priceElements[0].length() <=5 && m_priceElements[0].length() >0) &&
                        (m_priceElements[1].length() >= 1) &&
                        (!m_element.equals("0.00") && !m_element.equals("0.0"))){
                    return true;
                }else{
                    returnAlert("Price Error", "Le prix que vous avez saisi n'est pas valide : 0.1€ à 99999.99€.");
                    throw new IllegalAccessException("Price Error");
                }

            default:
                return false;
        }
    }

    private void returnAlert(String title, String message){

        LayoutInflater factory = LayoutInflater.from(this);
        View my_layout = factory.inflate(R.layout.alert_box, null);
        TextView my_title = (TextView) my_layout.findViewById(R.id.m_title);
        TextView my_content = (TextView) my_layout.findViewById(R.id.m_content);
        my_title.setText(title);
        my_content.setText(message);

        AlertDialog.Builder m_alert = new AlertDialog.Builder(this, R.style.CustomAlertDialog)
                .setView(my_layout)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // No action
                    }
                });
        m_alert.show();
    }
}
