package com.example.sms.shri_login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutPassword;
    private TextInputLayout textInputLayoutName;
    private TextInputLayout textInputLayoutMobile;
    private TextInputLayout textInputLayoutEno1;
    private TextInputLayout textInputLayoutEno2;
    private TextInputLayout textInputLayoutEno3;
    String blood_type;
    private Spinner blood;
    String[] blood_types={"Select Blood Group","A+ve","A-ve","B+ve","B-ve","O+ve","O-ve","AB+ve","AB-ve"};
    private Button buttonRegister;

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        blood=findViewById(R.id.blood);

        blood.setOnItemSelectedListener(this);
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,blood_types);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        blood.setAdapter(aa);

        textInputLayoutEmail=findViewById(R.id.email);
        textInputLayoutPassword=findViewById(R.id.password);
        textInputLayoutMobile=findViewById(R.id.mobile);
        textInputLayoutName=findViewById(R.id.name);
        textInputLayoutEno1=findViewById(R.id.emer1);
        textInputLayoutEno2=findViewById(R.id.emer2);
        textInputLayoutEno3=findViewById(R.id.emer3);
        buttonRegister = findViewById(R.id.register);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //validateValues();
                validateRegister();
            }
        });
    }
    /*public boolean validEmail(){
        String email= textInputLayoutEmail.getEditText().getText().toString().trim();
        if(email.isEmpty())
        {
            textInputLayoutEmail.setError("Enter Email");
        }
    }*/

   /* public void validateValues(){
        //if(validEmail()| vaildPassword() | vaildName() | validMobile()| validEno1()|validEno2()|validEno3())
         //   return;
        validateRegister();
    }*/
    public void validateRegister()
    {
        final String blood_type_new=blood_type;
        final String email=textInputLayoutEmail.getEditText().getText().toString().trim();
        String password=textInputLayoutPassword.getEditText().getText().toString().trim();
        final String name=textInputLayoutName.getEditText().getText().toString().trim();
        final String mobile=textInputLayoutMobile.getEditText().getText().toString().trim();
        final String eno1=textInputLayoutEno1.getEditText().getText().toString().trim();
        final String eno2=textInputLayoutEno2.getEditText().getText().toString().trim();
        final String eno3=textInputLayoutEno3.getEditText().getText().toString().trim();

        if(email.isEmpty())
        {
            textInputLayoutEmail.setError("Enter valid email");
            return;
        }
        if(password.isEmpty())
        {
            textInputLayoutPassword.setError("Enter valid password");
            return;
        }
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            User user=new User(name,email,mobile,blood_type_new,eno1,eno2,eno3);

                            FirebaseDatabase.getInstance().getReference("users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {
                                        Toast.makeText(RegisterActivity.this, "Registration Success.",
                                                Toast.LENGTH_SHORT).show();
                                        Intent main=new Intent(RegisterActivity.this,MainActivity.class);
                                        startActivity(main);
                                    }
                                    else {
                                        Toast.makeText(RegisterActivity.this, "Registration failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });


                        } else {
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Registration failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        blood_type=blood_types[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
