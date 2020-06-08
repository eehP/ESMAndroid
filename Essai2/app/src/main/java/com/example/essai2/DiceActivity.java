package com.example.essai2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.security.SecureRandom;

public class DiceActivity extends AppCompatActivity implements View.OnClickListener {
    TextView textResult;
    int max = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice);
        max = getIntent().getIntExtra("max", 6);
        Button buttonRoll = findViewById(R.id.buttonRoll);
        buttonRoll.setOnClickListener(this);
        textResult = findViewById(R.id.textResult);
        textResult.setText("");
        TextView textTitle = findViewById(R.id.textTitle);
        textTitle.setText("Dé "+ max +" faces");
    }
    @Override
    public void onClick(View v) {
        SecureRandom random = new SecureRandom();
        int result = random.nextInt(max)+1;
        textResult.setText(String.valueOf(result));
    }
}
