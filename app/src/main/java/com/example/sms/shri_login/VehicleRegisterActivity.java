package com.example.sms.shri_login;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class VehicleRegisterActivity extends AppCompatActivity {

    private TextInputLayout TLvin;
    private TextInputLayout TLmodel;
    private TextInputLayout TLcolor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_register);
        TLvin=findViewById(R.id.vin);
        TLmodel=findViewById(R.id.model);
        TLcolor=findViewById(R.id.color);
        Button vehicleRegister = findViewById(R.id.vehicleRegister);
        vehicleRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateVehicle();
            }
        });

    }
    public void validateVehicle(){
        final String vin=TLvin.getEditText().getText().toString().trim();
        String color=TLcolor.getEditText().getText().toString().trim();
        String model=TLmodel.getEditText().getText().toString().trim();
        String owner=FirebaseAuth.getInstance().getCurrentUser().getUid();

        if(vin.isEmpty())
        {
            TLvin.setError("*Required");
            return;
        }
        if(color.isEmpty())
        {
            TLcolor.setError("*Required");
            return;
        }
        if(model.isEmpty())
        {
            TLmodel.setError("*Required");
            return;
        }
        if(owner.isEmpty())
            return;
        Vehicle vehicle=new Vehicle(model,color,owner);
        FirebaseDatabase.getInstance().getReference("vehicles")
                .child(vin)
                .setValue(vehicle).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                   /* Toast.makeText(VehicleRegisterActivity.this, "Registration Success.",
                            Toast.LENGTH_SHORT).show();
                    Intent main=new Intent(VehicleRegisterActivity.this,MainActivity.class);
                    startActivity(main);*/
                   register(vin);

                }
                else {
                    Toast.makeText(VehicleRegisterActivity.this, "Registration failed.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public void register(String vin)
    {
        FirebaseDatabase.getInstance().getReference("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("vin")
                .setValue(vin).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(VehicleRegisterActivity.this, "Registration Success.",
                            Toast.LENGTH_SHORT).show();
                    Intent main=new Intent(VehicleRegisterActivity.this,MainActivity.class);
                    startActivity(main);
                }
                else {
                    Toast.makeText(VehicleRegisterActivity.this, "Registration failed.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
