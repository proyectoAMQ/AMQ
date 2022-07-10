package com.example.amq.ui.vistas;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class UsuarioInfoViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public UsuarioInfoViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}