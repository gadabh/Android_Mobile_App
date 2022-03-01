package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static java.lang.String.valueOf;

public class authentification extends AppCompatActivity {
    private ProgressDialog progressDialog ;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference ;


    private void userLogin(final String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if (task.isSuccessful()){
                    // Sign in success, update UI with the signed-in user's information
// *************************************Write information  to the database
                 FirebaseUser user=firebaseAuth.getCurrentUser() ;
                    UserInformation userInformation=new UserInformation(email, "100") ;
                 databaseReference.child("users").child(user.getUid()).setValue(userInformation);
                  Toast.makeText(authentification.this,"information saved ... ", Toast.LENGTH_SHORT).show();
//***************************************************************************************************
                    Toast.makeText(authentification.this,"welcom ", Toast.LENGTH_SHORT).show();
                   Intent j = new Intent(authentification.this, MainActivity.class);// direction page d'acceuil
                   startActivity(j);
                }else{
                    Toast.makeText(authentification.this,"faild ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

protected void onCreate(Bundle savedInstanceState) {
           super.onCreate(savedInstanceState);
           setContentView(R.layout.activity_authentification);
           Button btlogin = (Button) findViewById(R.id.login);
           Button btsignup = (Button) findViewById(R.id.signup);
           final EditText user = findViewById(R.id.username);
           final EditText pass =  findViewById(R.id.password);
           progressDialog=new ProgressDialog(this);
           firebaseAuth=FirebaseAuth.getInstance();

    databaseReference= FirebaseDatabase.getInstance().getReference();

 btlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth=FirebaseAuth.getInstance();
                String email = user.getText().toString();
                String password = pass.getText().toString();
                if (password.equalsIgnoreCase("") || email.equalsIgnoreCase("")){
                    Toast.makeText(authentification.this, "champ obligatoir", Toast.LENGTH_SHORT).show();
                }
                else {

                    progressDialog.setMessage("Registering User ... ");
                     progressDialog.show();

                    userLogin(email,password);
                }
            }
   });

   btsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(authentification.this, "Sign up ", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(authentification.this, Signup.class);
                startActivity(i);
            }
        });
    }
}
