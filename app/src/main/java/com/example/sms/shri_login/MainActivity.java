package com.example.sms.shri_login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private TextView showVin;
    private TextView showModel;
    private TextView showColor;
    private CardView cardView;
    private Button add_vehicle;
    String uid;
    public String user_vin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cardView=findViewById(R.id.card);
        textView=findViewById(R.id.textView2);
        showVin=findViewById(R.id.showVin);
        showColor=findViewById(R.id.showColor);
        showModel=findViewById(R.id.showModel);
        add_vehicle = findViewById(R.id.addVehicle);

        FirebaseUser f_user = FirebaseAuth.getInstance().getCurrentUser();
        if (f_user != null) {
            uid = f_user.getUid();
        }
        DatabaseReference db_user = FirebaseDatabase.getInstance().getReference("/users/" + uid);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,TrackActivity.class);
                startActivity(intent);
            }
        });

        db_user.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user=dataSnapshot.getValue(User.class);
                if(user != null) {
                    String user_name = user.name;
                    user_vin = user.vin;
                    String welcome = "Welcome ";
                    textView.setText(String.format("%s%s", welcome, user_name));
                    display(user_vin);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
               // Log.w(TAG, "Failed to read value.", error.toException());

            }
        });


        add_vehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,VehicleRegisterActivity.class);
                startActivity(intent);
            }
        });

    }
    public void display(final String vin)
    {
        FirebaseDatabase.getInstance().getReference("/vehicles/"+vin)
        .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Vehicle vehicle=dataSnapshot.getValue(Vehicle.class);
                if(vehicle!=null)
                {
                    cardView.setVisibility(View.VISIBLE);
                    add_vehicle.setVisibility(View.GONE);

                    String color=vehicle.color;
                    String model=vehicle.model;
                    showVin.setText(vin);
                    showModel.setText(String.format("Model : %s", model));
                    showColor.setText(String.format("Color : %s", color));
                }
                else
                {
                    cardView.setVisibility(View.GONE);
                    add_vehicle.setVisibility(View.VISIBLE);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                    int a=7;
            }
        });
    }
}
