package com.example.andnote;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class createAcc extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private Button btnSignUp;
    private Button btnBack;
    private EditText edtEmail;
    private EditText edtPass;
    private EditText edtName;
    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_acc);



        edtEmail= findViewById(R.id.edtEmail);
        edtPass= findViewById(R.id.edtPass);
        edtName= findViewById(R.id.edtName);
        btnSignUp= findViewById(R.id.btnSignUp);
        btnBack= findViewById(R.id.btnBack);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        mProgress = new ProgressDialog(createAcc.this);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Email= edtEmail.getText().toString().trim();
                String Password= edtPass.getText().toString().trim();
                final String name= edtName.getText().toString().trim();



                mProgress.setMessage("Please wait..........");
                mAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(createAcc.this,new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {
                       if (task.isSuccessful()) {
                           String user_id= mAuth.getCurrentUser().getUid();
                           DatabaseReference curr = mDatabase.child(user_id);
                           curr.child("name").setValue(name);
                           Intent mainActivity = new Intent(createAcc.this, MainActivity.class);
                           startActivity(mainActivity);
                           finish();
                           mProgress.dismiss();
                           mProgress.setMessage("Account created!!!");
                       }
                   }
               });
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(createAcc.this, login.class);
                startActivity(login);
            }
        });
    }
}
