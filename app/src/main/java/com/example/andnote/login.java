package com.example.andnote;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class login extends AppCompatActivity {

    private EditText edtEmail,edtPass;
    private Button btnSignIn;
    private Button btnSingUp;
    private TextInputLayout passwordTextInput;
    private TextInputLayout emailTextInput;


    private FirebaseAuth mAuth;
    private ProgressDialog mProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

         btnSignIn = findViewById(R.id.btnSignInLog);
         btnSingUp = findViewById(R.id.btnSignUpLog);
         edtEmail = findViewById(R.id.edtEmailLog);
         edtPass = findViewById(R.id.edtPassLog);
        passwordTextInput = findViewById(R.id.passwordTextInputLog);
        emailTextInput = findViewById(R.id.emailTextInputLog);

         mAuth = FirebaseAuth.getInstance();



         btnSignIn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 String Email = edtEmail.getText().toString().trim();
                 String Password = edtPass.getText().toString().trim();
                 mProgress = new ProgressDialog(login.this);


                 if (Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
                     emailTextInput.setError(null);
                     if (checkPass(edtPass.getText()) == true) {
                         passwordTextInput.setError(null);
                         mProgress.setMessage("Signing wait..........");
                         mProgress.show();
                         mAuth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(login.this, new OnCompleteListener<AuthResult>() {
                             @Override
                             public void onComplete(@NonNull Task<AuthResult> task) {
                                 if (task.isSuccessful()) {
                                     Intent mainActivity = new Intent(login.this, MainActivity.class);
                                     startActivity(mainActivity);
                                     finish();
                                     mProgress.dismiss();
                                     mProgress.setMessage("Login success!!!");
                                     mProgress.show();
                                 } else {
                                     mProgress.dismiss();
                                     Toast.makeText(login.this, "ERROR: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                     emailTextInput.setError(getString(R.string.andNote_error_loginFailed));
                                 }
                             }
                         });
                     } else {
                         passwordTextInput.setError(getString(R.string.andNote_error_password));
                     }
                 } else {
                     emailTextInput.setError(getString(R.string.andNote_error_email));
                 }


             }
         });

         btnSingUp.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent create = new Intent(login.this, createAcc.class);
                 startActivity(create);
             }
         });

    }
    private boolean checkPass(@Nullable Editable password)
    {
        if (!isPasswordValid(password)) {
            return false;

        } else {
           return true;
        }

    }

    private boolean isPasswordValid(@Nullable Editable text)
    {
        return text !=null && text.length() >=6;
    }
}
