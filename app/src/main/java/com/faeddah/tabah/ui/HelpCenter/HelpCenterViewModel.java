package com.faeddah.tabah.ui.HelpCenter;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HelpCenterViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HelpCenterViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Help Center fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}