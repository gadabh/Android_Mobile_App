package com.example.myapplication.ui.cart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.myapplication.R;
import com.example.myapplication.UserInformation;
import com.example.myapplication.authentification;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class  CartFragment<inner> extends Fragment {
    private CartViewModel cartviewmodel;
    private TextView textViewUserEmail ;
    private Button buttonlogout ;
    private FirebaseAuth.AuthStateListener authStateListener ;
    private DatabaseReference myref ;
    private FirebaseAuth firebaseAuth ;// declaration de base de donne
    private FirebaseDatabase firebaseDatabase ;
    private TextView nbpoint ;// nombre de point
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        cartviewmodel = ViewModelProviders.of(this).get(CartViewModel.class);
        View root = inflater.inflate(R.layout.carte, container, false);

        final TextView nbpoint = root.findViewById(R.id.textViewUserNbPoin2);
        final TextView textViewUserEmail = root.findViewById(R.id.textViewUserEmail);
        final TextView txtid = root.findViewById(R.id.id);
        final Button buttonlogout=root.findViewById(R.id.buttonLogout);
        //******** base de donne***************************
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase =FirebaseDatabase.getInstance();//objet de database
        myref=firebaseDatabase.getReference() ;
        //***************************************************
        if(firebaseAuth.getCurrentUser() == null){
            startActivity(new Intent(getActivity(),authentification.class));
        }
        cartviewmodel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                ///***modification de l'email
                final FirebaseUser user = firebaseAuth.getCurrentUser();
                final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid());

                textViewUserEmail.setText((CharSequence)user.getEmail());//**** afficher l'adress
                txtid.setText((CharSequence)user.getUid());
//**************** modifier le nombre de point *******************
                mDatabase.child("nbpoint").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String value = dataSnapshot.getValue(String.class);
                        nbpoint.setText(value);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
//******************************************************************************
            }
        });
        buttonlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    firebaseAuth.signOut();
                 startActivity(new Intent(getActivity(),authentification.class));
            }
        });
        return root;
    }
}