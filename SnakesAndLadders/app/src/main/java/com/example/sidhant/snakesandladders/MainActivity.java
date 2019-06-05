package com.example.sidhant.snakesandladders;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    Button btStartGame;
    EditText etPlayerCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btStartGame= (Button) findViewById(R.id.btStrtGame);
        etPlayerCount = (EditText) findViewById(R.id.etPlayerCount);

        btStartGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(MainActivity.this,GameMainScreen.class);
                    i.putExtra("PlayerCount", Integer.valueOf(etPlayerCount.getText().toString()));
                startActivity(i);
            }
        });
    }
}
