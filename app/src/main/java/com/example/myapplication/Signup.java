package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signup extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
private ProgressDialog progressDialog ;
    private DatabaseReference databaseReference ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        firebaseAuth = FirebaseAuth.getInstance();// Initialize Firebase Auth
        final EditText user = findViewById(R.id.et_pseudo);
        final EditText mail = findViewById(R.id.et_email);
        final EditText pass = findViewById(R.id.et_password);
        final EditText pass2 = findViewById(R.id.et_password2);
        Button btsend = (Button) findViewById(R.id.btn_send);
        progressDialog=new ProgressDialog(this);
        btsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = user.getText().toString().trim();
                String email = mail.getText().toString().trim();
                String password = pass.getText().toString().trim();
                String password2 = pass2.getText().toString().trim();

                if (password.equalsIgnoreCase("")
                        || password2.equalsIgnoreCase("")
                        || username.equalsIgnoreCase("")
                        || email.equalsIgnoreCase("")) {
                    Toast.makeText(Signup.this, "champ obligatoir", Toast.LENGTH_SHORT).show();
                }else if (!(password.equalsIgnoreCase(password2))) {
                    Toast.makeText(Signup.this, "verifier votre password ", Toast.LENGTH_SHORT).show();
                } else {

                   progressDialog.setMessage("Registering User ... ");
                  progressDialog.show();

                    register(email, password);
                }
            }
        });
    }
    private void userLogin(final String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if (task.isSuccessful()){
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(Signup.this,"welcom ", Toast.LENGTH_SHORT).show();
                    Intent j = new Intent(Signup.this, MainActivity.class);// direction page d'acceuil
                    startActivity(j);
                }else{
                    Toast.makeText(Signup.this,"faild ", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
    private void register(final String email, final String password){


        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(Signup.this,"information saved ... ", Toast.LENGTH_SHORT).show();
                            Intent j = new Intent(Signup.this, authentification.class);// direction page de connexion
                            startActivity(j);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Signup.this, task.getException().toString(),
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }
}
