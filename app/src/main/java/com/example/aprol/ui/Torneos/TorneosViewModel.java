package com.example.aprol.ui.Torneos;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TorneosViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public TorneosViewModel() {
        mText = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }
}
