package org.libreapps.rest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {
    private EditText userEmail;
    private EditText userPassword;
    private Button buttonlogin;
    private String action = "bills", token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userEmail = (EditText)findViewById(R.id.user_email);
        userPassword = (EditText)findViewById(R.id.user_password);
        buttonlogin = (Button)findViewById(R.id.button_login);
        buttonlogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    JSONObject jAuth = new JSONObject();
                    jAuth.put("email", userEmail.getText());
                    jAuth.put("password", userPassword.getText());
                    jAuth.put("app", "MNA");

                    ConnectionRest connectionRest = new ConnectionRest();
                    connectionRest.setJsonObj(jAuth);
                    connectionRest.setAction("auth");
                    connectionRest.setToken(token);
                    connectionRest.execute("POST");
                    String token = (String) connectionRest.get();
                    if(token.charAt(0)=='(') {
                        Log.v("LoginActivity", token);
                    }else {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("token", token);
                        startActivity(intent);
                    }

                } catch (JSONException | InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}