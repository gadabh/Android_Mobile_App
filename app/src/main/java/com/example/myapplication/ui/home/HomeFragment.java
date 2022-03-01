package com.example.myapplication.ui.home;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.myapplication.R;
import com.example.myapplication.Signup;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private Button btn_map;
    ImageView img ;
    ImageView imageView1 ;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference =firebaseDatabase.getReference() ;
    //*************************************** importation des image de la base de donne *********************************************
    private DatabaseReference first =databaseReference. child("image").child("image");
    @Override
    public void onStart(){
        super.onStart();

        first.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String link = dataSnapshot.getValue(String.class);
                Picasso.get().load(link).into(imageView1);
        }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
//***********************************************************************************************************************************
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
       // TextView text =root.findViewById(R.id.textView3);
      //  text.setMovementMethod(LinkMovementMethod.getInstance());


        final ImageView img=root.findViewById(R.id.imagecarou);//carouselle
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
//******************************************************image carrou ****************************************************************
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable(){
                    int [] carrou= new int[]{R.drawable.carou1,R.drawable.carou2,R.drawable.carou3,R.drawable.carou4};
                    int n=0;
                    public void run(){
                        if(n<4) {
                            img.setImageResource(carrou[n]);
                            n++;
                        } else {
                            n=0;
                        }
                        handler.postDelayed(this, 900);
                    }
                }, 0);

//*******************************************************************************************************************

            }
        });


        return root;

}


}