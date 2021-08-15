package com.rathandevaki.farmersworld.ui.gallery;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GalleryViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public GalleryViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Farmer World app will helps farmers to sell their crops with the reasonable price.\n" +
                "We helps farmers not getting cheated by middle man and commissions.\n" +
                "This is the right platform where a farmer can sell or buy the crops ,as well he can also hire employees to finish his work.\n" +
                "we also provided an option to the farmers to share their thoughts to other farmers. and one can also teach about farming and so on.");
    }

    public LiveData<String> getText() {
        return mText;
    }
}