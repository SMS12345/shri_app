package com.example.sms.shri_login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "hello";
    private FirebaseAuth mAuth;
    private String email;
    private String password;
    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutPass;
    private FirebaseUser user = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseApp.initializeApp(LoginActivity.this);
        //notification channel code

        //end

        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        if(mAuth !=null){
        user=mAuth.getCurrentUser();}
        if(user != null)
            mAuth.signOut();

        textInputLayoutEmail=findViewById(R.id.textInputLayoutEmail);
        textInputLayoutPass=findViewById(R.id.textInputLayoutPass);

        Button login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = textInputLayoutEmail.getEditText().getText().toString().trim();
                password = textInputLayoutPass.getEditText().getText().toString().trim();

                if (TextUtils.isEmpty(email)|| TextUtils.isEmpty(password)) return;
                else
                    validate(email,password);
            }
        });

        TextView regLink = findViewById(R.id.register);
        regLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reg=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(reg);
            }
        });

    }
    public void validate(String email,String password)
    {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            Log.d(TAG, "signInWithEmail:success");
                            Intent main=new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(main);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }


                    }
                });
    }


}
