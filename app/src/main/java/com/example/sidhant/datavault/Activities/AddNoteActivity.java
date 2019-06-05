package com.example.sidhant.datavault.Activities;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.sidhant.datavault.POJOs.Note;
import com.example.sidhant.datavault.R;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class AddNoteActivity extends AppCompatActivity {
    FirebaseUser user;
    TextInputEditText etNoteBody;
    ImageButton ibSubmit;
    Intent i;
    DatabaseReference dbf;
    FirebaseDatabase fbd = FirebaseDatabase.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        dbf = fbd.getReference(user.getEmail());

        i = getIntent();
        etNoteBody = findViewById(R.id.etNoteBody);
        ibSubmit = findViewById(R.id.ibSubmit);
        ibSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(i.getStringExtra("Motive")=="New") {
                    dbf.child(user.getEmail()).child("Notes").push().setValue(new Note(etNoteBody.getText().toString()));
                }
            }
        });

    }
}
