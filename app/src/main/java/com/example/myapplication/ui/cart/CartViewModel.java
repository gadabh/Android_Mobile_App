package com.example.myapplication.ui.cart;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CartViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private Button b;

    public CartViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is cart fragment");

    }


    public LiveData<String> getText() {
        return mText;
    }

    public Button getB() {
        return b;
    }
}