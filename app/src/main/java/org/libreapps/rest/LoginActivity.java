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

import static android.widget.Toast.LENGTH_LONG;

public class LoginActivity extends AppCompatActivity {
    private EditText userEmail;
    private EditText userPassword;
    private String action = "bills", token;
    private String error = "Login ou mot de passe incorrect.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userEmail = (EditText)findViewById(R.id.user_email);
        userPassword = (EditText)findViewById(R.id.user_password);
        Button buttonLogin = (Button) findViewById(R.id.button_login);
        Button buttonRegister = (Button) findViewById(R.id.button_register);

        userEmail.setText("");
        userPassword.setText("");

        buttonLogin.setOnClickListener(new View.OnClickListener() {

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
                    connectionRest.execute("POST");
                    String token = (String) connectionRest.get();
                    if(token.charAt(0) !='{') {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("token", token);
                        startActivity(intent);
                    } else {
                        throw new InterruptedException();
                    }

                } catch (JSONException | InterruptedException | ExecutionException e) {
                    Toast toast = new Toast();
                    toast.showToast(getApplicationContext(), error, LENGTH_LONG);
                    e.printStackTrace();
                }
            }
        });
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });
    }
}