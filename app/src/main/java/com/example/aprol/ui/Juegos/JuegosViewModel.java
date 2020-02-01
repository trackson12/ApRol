package com.example.aprol.ui.Juegos;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class JuegosViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public JuegosViewModel() {
        mText = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }
}