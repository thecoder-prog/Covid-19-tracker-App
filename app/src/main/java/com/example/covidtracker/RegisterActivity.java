package com.example.covidtracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    private EditText mEmail , mPass;
    private Button signUpBtn;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);

        mEmail = findViewById(R.id.mEmail);
        mPass = findViewById(R.id.mPass);
        signUpBtn = findViewById(R.id.signUpBtn);

        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null)  // when the current user object is already present
        {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));  //back to main page
            finish();
        }

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString();
                String pass = mPass.getText().toString();

                if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    if (!pass.isEmpty()){
                        mAuth.createUserWithEmailAndPassword(email, pass)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        Toast.makeText(RegisterActivity.this, "Registered Successfully !!", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(RegisterActivity.this , LoginActivity.class));
                                        finish();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(RegisterActivity.this, "Registration Failed !!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else{
                        mPass.setError("Empty Fields Are not Allowed");
                    }
                }else if(email.isEmpty()){
                    mEmail.setError("Empty Fields Are not Allowed");
                }else{
                    mEmail.setError("Pleas Enter Correct Email");
                }
            }
        });

    }
}