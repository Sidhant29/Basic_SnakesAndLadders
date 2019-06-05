package com.example.sidhant.datavault.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sidhant.datavault.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private FirebaseAuth mAuth;
    public static final String TAG="this";
    FirebaseUser currentUser;
    TextInputEditText etEmail,etPassword;
    Button btnSubmit;
    TextView tvMatter,tvSignUpWithOther;
    String status;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        tvMatter = findViewById(R.id.tvMatter);
        mAuth = FirebaseAuth.getInstance();
        tvSignUpWithOther = findViewById(R.id.tvSignUpWithOther);

        i = getIntent();
        status = i.getStringExtra("Status");
        Log.d(TAG, "onCreate: "+status);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);
        tvSignUpWithOther.setOnClickListener(this);
        if(status.equals("Sign in with email")){
            tvMatter.setText("Sign In");
            etEmail.setText(i.getStringExtra("Email"));

        }
        else if(status.equals("Sign up")){
            tvMatter.setText("Sign Up");
        }
        else if(status.equals("Sign in")){
            tvMatter.setText("Sign In");
        }
    }

    private void signIn(String email,String password){
        Log.d(TAG, "signIn: ");
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                           // Log.d("login", "onComplete: "+ user.getEmail());
                            //String email = user.getEmail();
                            Intent i = new Intent(LoginActivity.this, CenterActivity.class);
                            //i.putExtra("Email", email);
                            startActivity(i);
                        }
                        else{
                            Toast.makeText(LoginActivity.this, "Sign In Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void signUp(String email,String password){
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent i = new Intent(LoginActivity.this, CenterActivity.class);
                            Log.d("login", "onComplete: "+ user.getEmail());
                           // i.putExtra("Email", user.getEmail());
                            startActivity(i);
                        }
                        if(!task.isSuccessful()){
                            FirebaseAuthException e = (FirebaseAuthException )task.getException();
                            Toast.makeText(LoginActivity.this, "Failed Registration: "+e.getMessage(), Toast.LENGTH_SHORT).show();

                            return;
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        if(R.id.btnSubmit == v.getId()) {


            if (status.equals("Sign in with email")) {
                tvMatter.setText("Sign In");
                etEmail.setText(i.getStringExtra("Email"));
                signIn(etEmail.getText().toString(), etPassword.getText().toString());

            } else if (status.equals("Sign up")) {
                tvMatter.setText("Sign Up");
                signUp(etEmail.getText().toString(), etPassword.getText().toString());
            } else if (status.equals("Sign in")) {
                tvMatter.setText("Sign In");
                signIn(etEmail.getText().toString(), etPassword.getText().toString());
            }

        }
        else if (R.id.tvSignUpWithOther==v.getId()){

            Intent i =new Intent(LoginActivity.this,LoginActivity.class);
            i.putExtra("Status","Sign up");
            startActivity(i);
        }
    }
}
